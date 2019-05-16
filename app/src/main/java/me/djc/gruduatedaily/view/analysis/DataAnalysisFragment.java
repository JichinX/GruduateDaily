package me.djc.gruduatedaily.view.analysis;

import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.base.fragment.SimpleFragment;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.bean.AnalysisData;
import me.djc.gruduatedaily.view.analysis.adapter.AnalysisItemAdapter;
import me.djc.gruduatedaily.base.AppConst;

import java.util.ArrayList;
import java.util.List;


public class DataAnalysisFragment extends SimpleFragment {
    private int mType;
    private RecyclerView mRvDatas;
    private AnalysisItemAdapter mItemAdapter;
    private List<AnalysisData> mDataList;

    private DataAnalysisFragment() {
        mDataList = new ArrayList<>();
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
        mDataList.add(new AnalysisData("数学", mType));
        mDataList.add(new AnalysisData("物理", mType));
        mDataList.add(new AnalysisData("线性代数", mType));
        mDataList.add(new AnalysisData("英语", mType));
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
}
