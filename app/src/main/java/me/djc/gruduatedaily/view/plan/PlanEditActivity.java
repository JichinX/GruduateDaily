package me.djc.gruduatedaily.view.plan;

import android.content.Intent;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.base.activity.BaseActivity;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.base.AppConst;
import me.djc.gruduatedaily.room.entity.Plan;
import me.djc.gruduatedaily.view.plan.adapter.PlanEditAdapter;
import org.jaaksi.pickerview.picker.TimePicker;

import java.util.ArrayList;
import java.util.List;

import static org.jaaksi.pickerview.picker.TimePicker.TYPE_TIME;

public class PlanEditActivity extends BaseActivity {
    private PlanEditAdapter mEditAdapter;
    private PlanViewModel mViewModel;
    private List<Plan> mPlans;
    private int dayType = PlanListFragment.DAY_TYPE_CUTTENT;
    private RecyclerView mRvPlans;

    @Override
    protected void onIntentData(Intent intent) {
        dayType = intent.getIntExtra(AppConst.Value.DAY_TYPE, PlanListFragment.DAY_TYPE_CUTTENT);
    }

    @Override
    protected void onDataInit() {
        mPlans = new ArrayList<>();
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

                    return;
                }
                //添加标签
                showLabelSelect();
                //设置标签为最后一步

            }

            @Override
            public void onTimeStart(Plan ePlan) {
                //设置开始时间
                showTimeSelect();
            }

            @Override
            public void onTimeEnd(Plan ePlan) {
                //设置结束时间
                showTimeSelect();
            }
        });
    }

    private void showTimeSelect() {
        TimePicker mTimePicker = new TimePicker
                .Builder(getContext(), TYPE_TIME, null)
                .create();
        mTimePicker.show();
    }

    private void showLabelSelect() {

    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_plan_edit;
    }
}
