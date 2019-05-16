package me.djc.gruduatedaily.view.ding;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.Guideline;
import me.djc.base.fragment.BaseFragment;
import me.djc.common.widget.calender.CustomCalendarView;
import me.djc.gruduatedaily.R;


public class DingFragment extends BaseFragment {

    private LinearLayout mLlDateInfo;
    private CustomCalendarView mCalendarView;
    private LinearLayout mDayInfoView;
    private LinearLayout mLlStatisticsView;
    private Guideline mGuidelineActionbar;

    public DingFragment() {
        // Required empty public constructor
    }

    public static DingFragment newInstance() {
        DingFragment fragment = new DingFragment();

        return fragment;
    }

    @Override
    protected View initView(View inflate) {
        mLlDateInfo = inflate.findViewById(R.id.ll_date_info);
        mCalendarView = inflate.findViewById(R.id.calendar_view);
        mDayInfoView = inflate.findViewById(R.id.day_info_view);
        mLlStatisticsView = inflate.findViewById(R.id.ll_statistics_view);
        mGuidelineActionbar = inflate.findViewById(R.id.guideline_actionbar);
        return inflate;
    }

    @Override
    protected void onGetArguments(Bundle arguments) {

    }

    @Override
    protected int onCreateViewRes() {
        return R.layout.fragment_ding;
    }

    @Override
    public void onDataLazyLoad() {

    }

    @Override
    public void refresh() {

    }
}
