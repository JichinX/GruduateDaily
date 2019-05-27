package me.djc.gruduatedaily.view.analysis;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;
import me.djc.base.fragment.BaseFragment;
import me.djc.common.util.ColorUtils;
import me.djc.common.util.PieChatUtil;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.room.entity.Label;
import me.djc.gruduatedaily.room.entity.Plan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分析当天
 */
public class DayAnalysisFragment extends BaseFragment {

    private static final String TAG = "DayAnalysisFragment";
    private AnalysisViewModel mViewModel;
    private PieChart mChatLabels;
    private List<Label> mLabels;
    private List<Plan> mPlans;

    public DayAnalysisFragment() {
        // Required empty public constructor
        mPlans = new ArrayList<>();
        mLabels = new ArrayList<>();
    }

    /**
     *
     */
    public static DayAnalysisFragment newInstance() {
        DayAnalysisFragment fragment = new DayAnalysisFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onDataLazyLoad() {
        mViewModel = ViewModelProviders.of(getActivity()).get(AnalysisViewModel.class);

        mViewModel.getDayPlansLiveData().observe(getLifecycleOwner(), new Observer<List<Plan>>() {
            @Override
            public void onChanged(List<Plan> ePlans) {
                //获取到当天内的计划列表
                if (null != ePlans) {
                    mPlans.clear();
                    mPlans.addAll(ePlans);
                }
                refresh();
            }
        });
        mViewModel.getEnabledLabels().observe(getLifecycleOwner(), new Observer<List<Label>>() {
            @Override
            public void onChanged(List<Label> eLabels) {
                //获取到当前处于可用的标!
                if (null != eLabels) {
                    mLabels.clear();
                    mLabels.addAll(eLabels);
                }
                refresh();
            }
        });
    }

    /**
     * 将计划数据 转换为 饼图的数据，以label为基准
     *
     * @param ePlans
     */
    private void patchChatData(List<Plan> ePlans) {

    }

    @Override
    public void refresh() {
        refreshChatData();
    }

    /**
     * 根据标签列表和计划列表来处理饼图数据
     * 饼图数据在标签的基础上会有其他、和空闲
     */
    private void refreshChatData() {
        long DayTimems = 24 * 60 * 60 * 1000;
        float temp = 1;
        float other = 0;
        //处理标签数据
        LongSparseArray<LabelTime> vTimeMap = new LongSparseArray<>();
        for (Label vLabel : mLabels) {
            vTimeMap.put(vLabel.getId(), new LabelTime(vLabel));
        }
        for (Plan vPlan : mPlans) {
            long labelId = vPlan.getLabelId();
            long time = vPlan.getTimeEnd() - vPlan.getTimeStart();
            float percent = (float) time / DayTimems;
            if (vTimeMap.indexOfKey(labelId) > 0) {
                vTimeMap.get(labelId).timePercent += percent;
            } else {
                other += percent;
            }
            temp = temp - percent;
        }
        temp = temp - other;
        Log.i(TAG, "refreshChatData: 剩余百分占比 = " + temp + "   other = " + other);
        //数据
        List<PieEntry> vEntries = new ArrayList<>();
        //颜色
        List<Integer> colors = new ArrayList<>();

        for (int i = 0; i < vTimeMap.size(); i++) {
            LabelTime vTime = vTimeMap.valueAt(i);
            vEntries.add(new PieEntry(vTime.timePercent, vTime.name));
            colors.add(vTime.mLabel.getClolor());
        }
        //添加空闲
        vEntries.add(new PieEntry(temp, "空闲"));
        colors.add(ColorUtils.createRandomColor());
        //添加其他
        vEntries.add(new PieEntry(other, "其他"));
        colors.add(ColorUtils.createRandomColor());
        PieChatUtil vUtil = new PieChatUtil(mChatLabels);
        vUtil.showSolidPieChart(vEntries, colors);

    }


    @Override
    protected View initView(View inflate) {
        mChatLabels = inflate.findViewById(R.id.chat_labels);


        return inflate;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected void onGetArguments(Bundle arguments) {

    }

    @Override
    protected int onCreateViewRes() {
        return R.layout.fragment_day_analysis;
    }

    private class LabelTime {
        private Label mLabel;
        private String name;
        private float timePercent;

        public LabelTime(Label eLabel) {
            mLabel = eLabel;
            name = eLabel.getContent();
        }
    }
}
