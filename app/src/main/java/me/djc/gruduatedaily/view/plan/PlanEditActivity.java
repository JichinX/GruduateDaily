package me.djc.gruduatedaily.view.plan;

import android.content.Intent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import me.djc.base.activity.BaseActivity;
import me.djc.common.util.CalenderUtil;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.base.AppConst;
import me.djc.gruduatedaily.room.entity.Label;
import me.djc.gruduatedaily.room.entity.Plan;
import me.djc.gruduatedaily.view.plan.adapter.PlanEditAdapter;
import org.jaaksi.pickerview.picker.TimePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static me.djc.gruduatedaily.view.plan.PlanListFragment.DAY_TYPE_CUTTENT;
import static org.jaaksi.pickerview.picker.TimePicker.TYPE_TIME;

public class PlanEditActivity extends BaseActivity {
    private PlanEditAdapter mEditAdapter;
    private PlanViewModel mViewModel;
    private List<Plan> mPlans;
    private int dayType = DAY_TYPE_CUTTENT;
    private RecyclerView mRvPlans;
    private List<Label> mLabels;

    private long startMs;
    private long endMs;

    @Override
    protected void onIntentData(Intent intent) {
        dayType = intent.getIntExtra(AppConst.Value.DAY_TYPE, DAY_TYPE_CUTTENT);
    }

    @Override
    protected void onDataInit() {
        mPlans = new ArrayList<>();
        mLabels = new ArrayList<>();
        mEditAdapter = new PlanEditAdapter(mPlans);

        mViewModel = ViewModelProviders.of(this).get(PlanViewModel.class);
        mViewModel.getPlans(dayType).observe(this, new Observer<List<Plan>>() {
            @Override
            public void onChanged(List<Plan> ePlans) {
                mPlans.clear();
                mPlans.addAll(ePlans);
                mPlans.add(new Plan());
                mEditAdapter.notifyDataSetChanged();
            }
        });
        mViewModel.getLabels().observe(this, new Observer<List<Label>>() {
            @Override
            public void onChanged(List<Label> eLabels) {
                //刷新标签列表
                mLabels.clear();
                mLabels.addAll(eLabels);
            }
        });
        if (dayType == DAY_TYPE_CUTTENT) {
            //当前的计划
            startMs = System.currentTimeMillis();
            endMs = CalenderUtil.getDayEndMs();
        } else {
            startMs = CalenderUtil.getNextDayStartMs();
            endMs = CalenderUtil.getNextDayEndMs();
        }
    }

    @Override
    protected void onViewInit() {
        mRvPlans = findViewById(R.id.rv_plans);
        mRvPlans.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvPlans.setAdapter(mEditAdapter);

        mEditAdapter.setPlanEditListener(new PlanEditAdapter.OnPlanEditListener() {
            @Override
            public void onDelete(Plan ePlan) {
                //删除计划
                mViewModel.deletePlan(ePlan);
            }

            @Override
            public void onAddLabel(Plan ePlan) {
                //检查日期
                if (ePlan.getTimeStart() <= 0L || ePlan.getTimeEnd() <= 0L) {
                    showToast("请先选择开始和结束时间");
                    return;
                }
                //添加标签
                showLabelSelect(ePlan);
                //设置标签为最后一步
            }

            @Override
            public void onTimeStart(Plan ePlan) {
                //设置开始时间
                showTimeSelect(ePlan, true);
            }

            @Override
            public void onTimeEnd(Plan ePlan) {
                //设置结束时间
                showTimeSelect(ePlan, false);
            }
        });
    }

    private void showTimeSelect(Plan ePlan, boolean isStart) {
        TimePicker mTimePicker =
                new TimePicker
                        .Builder(getContext(), TYPE_TIME, new TimePicker.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(TimePicker picker, Date date) {
                        if (isStart) {
                            ePlan.setTimeStart(date.getTime());
                        } else {
                            ePlan.setTimeEnd(date.getTime());
                        }
                        mEditAdapter.notifyDataSetChanged();
                    }
                })
                        .setRangDate(startMs, endMs)
                        .create();
        mTimePicker.show();
    }

    private void showLabelSelect(Plan ePlan) {
        MaterialDialog.Builder vBuilder = new MaterialDialog.Builder(getContext());
        vBuilder.title("选择标签")
                .items(mLabels)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        if (which == -1) {
                            return false;
                        }
                        ePlan.setLabelId(mLabels.get(which).getId());
                        ePlan.setLabel(mLabels.get(which).getContent());
                        mViewModel.addPlans(ePlan, dayType);
                        return false;
                    }
                })
                .positiveText("确定")
                .neutralText("自定义")
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //自定义文本
                        showCustomLabel(ePlan);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 显示自定义文本
     *
     * @param ePlan
     */
    private void showCustomLabel(Plan ePlan) {
        MaterialDialog.Builder vBuilder = new MaterialDialog.Builder(getContext());
        vBuilder.title("输入自定义文本标签")
                .input("自定义文本标签，不计入统计～", null, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        ePlan.setLabel(input.toString());
                        mViewModel.addPlans(ePlan, dayType);
                        dialog.dismiss();
                    }
                })
                .positiveText("确定")
                .show();
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_plan_edit;
    }
}
