package me.djc.gruduatedaily.view.analysis;

import android.os.Bundle;
import android.util.LongSparseArray;
import android.view.View;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.base.fragment.SimpleFragment;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.room.entity.Label;
import me.djc.gruduatedaily.room.entity.Plan;
import me.djc.gruduatedaily.view.analysis.adapter.AnalysisItemAdapter;
import me.djc.gruduatedaily.base.AppConst;

import java.util.ArrayList;
import java.util.List;


public class DataAnalysisFragment extends SimpleFragment {
    private int mType;
    private RecyclerView mRvDatas;
    private AnalysisItemAdapter mItemAdapter;
    private LongSparseArray<LabelProxy> mDataList;
    private AnalysisViewModel mViewModel;

    private List<Label> mLabels;
    private List<Plan> mPlans;

    private DataAnalysisFragment() {
        mLabels = new ArrayList<>();
        mPlans = new ArrayList<>();
        mDataList = new LongSparseArray<>();
        mItemAdapter = new AnalysisItemAdapter(mDataList);
    }


    public static DataAnalysisFragment newInstance(int eType) {
        DataAnalysisFragment fragment = new DataAnalysisFragment();
        Bundle args = new Bundle();
        args.putInt(AppConst.Value.ANALYSIS_TYPE, eType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onGetArguments(Bundle arguments) {
        mType = arguments.getInt(AppConst.Value.ANALYSIS_TYPE);
    }

    @Override
    public void onDataLazyLoad() {
        mViewModel = ViewModelProviders.of(getActivity()).get(AnalysisViewModel.class);
        mViewModel.getEnabledLabels().observe(getLifecycleOwner(), new Observer<List<Label>>() {
            @Override
            public void onChanged(List<Label> eLabels) {
                //获取在可用状态下的标签
                mLabels.clear();
                mLabels.addAll(eLabels);
                refresh();
            }
        });
        LiveData<List<Plan>> vLiveData;
        if (mType == AppConst.Analysistype.TAG_MONTH) {
            vLiveData = mViewModel.getMonthPlans();
        } else {
            vLiveData = mViewModel.getWeekPlans();
        }
        vLiveData.observe(getLifecycleOwner(), new Observer<List<Plan>>() {
            @Override
            public void onChanged(List<Plan> ePlans) {
                //获取到计划列表
                mPlans.clear();
                mPlans.addAll(ePlans);
                refresh();
            }
        });

        mItemAdapter.notifyDataSetChanged();
    }

    @Override
    protected View initView(View inflate) {
        mRvDatas = inflate.findViewById(R.id.app_analysis_rv_datas);
        mRvDatas.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvDatas.setAdapter(mItemAdapter);
        return inflate;
    }


    @Override
    protected int onCreateViewRes() {
        return R.layout.fragment_data_analysis;
    }

    @Override
    public void refresh() {
        //刷新数据
        mDataList.clear();
        //首先按标签整理

        for (Label vLabel : mLabels) {
            mDataList.put(vLabel.getId(), new LabelProxy(vLabel));
        }
        //将计划按标签分类
        for (Plan vPlan : mPlans) {
            long labelID = vPlan.getLabelId();
            if (labelID > 0) {
                if (mDataList.indexOfKey(labelID) >= 0) {
                    mDataList.get(labelID).addPlan(vPlan);
                }
            }
        }

        mItemAdapter.notifyDataSetChanged();
    }

    public class LabelProxy {
        private Label mLabel;
        private List<Plan> mPlans;

        public Label getLabel() {
            return mLabel;
        }

        public void setLabel(Label eLabel) {
            mLabel = eLabel;
        }

        public List<Plan> getPlans() {
            if (null == mPlans) {
                mPlans = new ArrayList<>();
            }
            return mPlans;
        }

        public void setPlans(List<Plan> ePlans) {
            mPlans = ePlans;
        }

        public LabelProxy(Label eLabel) {
            mLabel = eLabel;
        }

        public void addPlan(Plan ePlan) {
            if (null == mPlans) {
                mPlans = new ArrayList<>();
            }
            mPlans.add(ePlan);
        }
    }
}
