package me.djc.gruduatedaily.view.ding;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Guideline;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.afollestad.materialdialogs.MaterialDialog;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import me.djc.base.fragment.BaseFragment;
import me.djc.common.util.CalenderUtil;
import me.djc.common.widget.calender.CustomCalendarView;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.bean.DayWord;
import me.djc.gruduatedaily.room.entity.Ding;
import me.xujichang.xbase.baseutils.strings.StringFormatUtil;

import java.util.*;

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
    private int currentYear, currentMonth, currentDay;
    private Calendar currentCalender;
    private TextView mTvDingMark;
    private TextView mTvCheckPlan;
    private TextView mTvAddDing;
    private TextView mTvSumDing;
    private TextView mTvSumDingAdd;
    private TextView mTvDingLoss;
    private boolean mDingOperate = false;
    private Calendar selectedCalender;

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
        mTvDingMark = inflate.findViewById(R.id.tv_ding_mark);
        mTvCheckPlan = inflate.findViewById(R.id.tv_check_plan);
        mTvAddDing = inflate.findViewById(R.id.tv_add_ding);
        mTvSumDing = inflate.findViewById(R.id.tv_sum_ding);
        mTvSumDingAdd = inflate.findViewById(R.id.tv_sum_ding_add);
        mTvDingLoss = inflate.findViewById(R.id.tv_ding_loss);
        mTvDingMark = inflate.findViewById(R.id.tv_ding_mark);

        mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {
                Log.i(TAG, "onCalendarOutOfRange: ");
            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                Log.i(TAG, "onCalendarSelect: ");
                updateDayInfo(calendar);
            }
        });
        mIvNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下一个月
                mCalendarView.scrollToNext();
                updateMonthData();
            }
        });
        mIvPreMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上个月
                mCalendarView.scrollToPre();
                updateMonthData();
            }
        });
        mTvDateResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回当前时间
                mCalendarView.scrollToCurrent(true);
                updateMonthData();
            }
        });
        mTvCheckPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看当天计划

            }
        });
        mTvAddDing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //补卡
                showPatchDing();
            }
        });
        return inflate;
    }

    /**
     * 显示补卡
     */
    private void showPatchDing() {
        MaterialDialog.Builder vBuilder = new MaterialDialog.Builder(getContext());
        vBuilder.title("补卡")
                .input("输入一天的总结哦～", null, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        //添加打卡信息
                        Ding vDing = new Ding();
                        vDing.setMark(input.toString());
                        mDingViewModel.addDingInfo(vDing, selectedCalender);
                        updateMonthData();
                        updateDayInfo(selectedCalender);
                    }
                })
                .show();

    }

    /**
     * 更新月份的数据
     */
    private void updateMonthData() {
        Calendar vCalendar = mCalendarView.getSelectedCalendar();
        long ms = vCalendar.getTimeInMillis();
        //更新月份的打卡信息
        mDingViewModel.updateMonthDings(ms);
    }

    private void updateDayInfo(Calendar eCalendar) {
        long ms = eCalendar.getTimeInMillis();
        selectedCalender = eCalendar;
        int year = eCalendar.getYear();
        int month = eCalendar.getMonth();
        int day = eCalendar.getDay();
        //更新日期显示
        mTvDateResult.setText(StringFormatUtil.formatTime(ms, "yyyy/MM/dd"));
        //更新每日一句
        mDingViewModel.updateDayWord(ms);
        if (eCalendar.getTimeInMillis() > currentCalender.getTimeInMillis()) {
            //如果选择的日期大于当前时间直接返回
            showDingOperate(false);
            return;
        }
        showDingOperate(true);
        //更新打卡信息
        mDingViewModel.setSelectedDay(ms);

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
                Log.i(TAG, "onChanged: " + eDing);
                updateDayDingInfo(eDing);
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
                Calendar selected = mCalendarView.getSelectedCalendar();
                int maxDay = CalenderUtil.getMonthDays(selected.getTimeInMillis());
                int dingDays = 0;
                if (null == eLongs) {
                    return;
                } else {
                    dingDays = eLongs.size();
                    mCalendarView.setSchemeDate(createShameData(eLongs));
                }
                updateDingCounter(dingDays, maxDay);
            }
        });
        currentYear = mCalendarView.getCurYear();
        currentMonth = mCalendarView.getCurMonth();
        currentDay = mCalendarView.getCurDay();
        currentCalender = new Calendar();
        currentCalender.setDay(currentDay);
        currentCalender.setYear(currentYear);
        currentCalender.setMonth(currentMonth);
        updateDayInfo(currentCalender);
        updateMonthData();
    }

    /**
     * 更新计数
     *
     * @param eDingDays
     * @param eMaxDay
     */

    private void updateDingCounter(int eDingDays, int eMaxDay) {
        mTvSumDing.setText(String.format("打卡：%s次", eDingDays));
        mTvDingLoss.setText(String.format("缺卡：%s次", eMaxDay - eDingDays));
    }

    private void showDingOperate(boolean show) {
        mDingOperate = show;
        mTvAddDing.setVisibility(show ? View.VISIBLE : View.GONE);
        mTvCheckPlan.setVisibility(show ? View.VISIBLE : View.GONE);
        mTvDingMark.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void updateDayDingInfo(Ding eDing) {
        if (!mDingOperate) {
            return;
        }
        if (null == eDing) {
            //未获得打卡信息
            mTvDingMark.setText("未打卡");
            mTvAddDing.setVisibility(View.VISIBLE);
        } else {
            mTvDingMark.setText(eDing.getMark());
            mTvAddDing.setVisibility(View.GONE);
        }
    }

    /**
     * 根据月份的打卡记录，添加标记
     *
     * @param eLongs
     * @return
     */
    private Map<String, Calendar> createShameData(List<Ding> eLongs) {
        //TODO  使用测试数据

        long current = System.currentTimeMillis();
        long dayMs = 24 * 60 * 60 * 1000;
        if (null == eLongs) {
            eLongs = new ArrayList<>();
        }
        eLongs.add(new Ding(current - 2 * dayMs, ""));
        eLongs.add(new Ding(current - 3 * dayMs, ""));
        eLongs.add(new Ding(current - 5 * dayMs, ""));
        eLongs.add(new Ding(current - 7 * dayMs, ""));
        eLongs.add(new Ding(current - 11 * dayMs, ""));


        Map<String, Calendar> schemes = new HashMap<>();
        java.util.Calendar vCalendar = java.util.Calendar.getInstance();
        for (Ding vDing : eLongs) {
            vCalendar.setTime(new Date(vDing.getDayMs()));
            Calendar tempCalendar = getSchemeCalendar(vCalendar);
            schemes.put(tempCalendar.toString(), tempCalendar);
        }
        return schemes;
    }

    private Calendar getSchemeCalendar(java.util.Calendar eCalendar) {
        Calendar calendar = new Calendar();
        calendar.setYear(eCalendar.get(java.util.Calendar.YEAR));
        calendar.setMonth(eCalendar.get(java.util.Calendar.MONTH) + 1);
        calendar.setDay(eCalendar.get(java.util.Calendar.DAY_OF_MONTH));
        //如果单独标记颜色、则会使用这个颜色
        calendar.setSchemeColor(Color.GREEN);
        calendar.setScheme("卡");
        calendar.addScheme(new Calendar.Scheme());
        return calendar;
    }

    @Override
    public void refresh() {

    }
}
