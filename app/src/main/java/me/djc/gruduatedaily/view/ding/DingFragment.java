package me.djc.gruduatedaily.view.ding;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.Guideline;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import me.djc.base.fragment.BaseFragment;
import me.djc.common.widget.calender.CustomCalendarView;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.bean.DayWord;
import me.djc.gruduatedaily.room.entity.Ding;
import me.xujichang.xbase.baseutils.strings.StringFormatUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * * Calender 相关APi
 * *
 * *public void scrollToPre();//滚动到上一个月
 * * public void scrollToNext();//滚动到下一个月
 */

public class DingFragment extends BaseFragment {

    private static final String TAG = "DingFragment";
    private LinearLayout mLlDateInfo;
    private CustomCalendarView mCalendarView;
    private LinearLayout mDayInfoView;
    private LinearLayout mLlStatisticsView;
    private Guideline mGuidelineActionbar;
    private TextView mTvDateResult;
    private ImageView mIvPreMonth;
    private ImageView mIvNextMonth;

    private DingViewModel mDingViewModel;

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
        mCalendarView = inflate.findViewById(R.id.custom_calendar_view);
        mDayInfoView = inflate.findViewById(R.id.day_info_view);
        mLlStatisticsView = inflate.findViewById(R.id.ll_statistics_view);
        mGuidelineActionbar = inflate.findViewById(R.id.guideline_actionbar);
        mTvDateResult = inflate.findViewById(R.id.tv_date_result);
        mIvPreMonth = inflate.findViewById(R.id.iv_pre_month);
        mIvNextMonth = inflate.findViewById(R.id.iv_next_month);

        mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {
                Log.i(TAG, "onCalendarOutOfRange: ");
            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                Log.i(TAG, "onCalendarSelect: ");
                long ms = calendar.getTimeInMillis();
                //更新日期显示
                mTvDateResult.setText(StringFormatUtil.formatTime(ms, "yyyy/MM/dd"));
                //更新打卡信息
                mDingViewModel.setSelectedDay(ms);
                //更新每日一句
                mDingViewModel.updateDayWord(ms);
                //更新月份的打卡信息
                mDingViewModel.updateMonthDings(ms);
            }
        });
        mIvNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下一个月
                mCalendarView.scrollToNext();
            }
        });
        mIvPreMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上个月
                mCalendarView.scrollToPre();
            }
        });

        return inflate;
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        //如果单独标记颜色、则会使用这个颜色
        calendar.setSchemeColor(color);
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        return calendar;
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
        mDingViewModel = ViewModelProviders.of(getActivity()).get(DingViewModel.class);
        mDingViewModel.subscribeDayDingInfo().observe(getLifecycleOwner(), new Observer<Ding>() {
            @Override
            public void onChanged(Ding eDing) {
                //TODO  获取到打卡信息
                Log.i(TAG, "onChanged: ");
            }
        });
        mDingViewModel.subscribeDayWord().observe(getLifecycleOwner(), new Observer<DayWord>() {
            @Override
            public void onChanged(DayWord eDayWord) {
                //TODO  更新每日一句
            }
        });
        mDingViewModel.subscribeDings().observe(getLifecycleOwner(), new Observer<List<Ding>>() {
            @Override
            public void onChanged(List<Ding> eLongs) {
                //TODO 更新月份的打卡标记
                Map<String, Calendar> map = new HashMap<>();
                mCalendarView.setSchemeDate(map);
            }
        });

    }

    @Override
    public void refresh() {

    }
}
