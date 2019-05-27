package me.djc.gruduatedaily.view.plan;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.base.activity.BaseActivity;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.room.entity.Plan;
import me.djc.gruduatedaily.view.plan.adapter.PlanAdapter;

import java.util.ArrayList;
import java.util.List;

import static me.djc.gruduatedaily.base.AppConst.Value.DAY_TIME_MS;

/**
 * @author xujichang
 * @date 2019/05/27
 **/
public class DayPlanActivity extends BaseActivity {
    private List<Plan> mPlans;
    private PlanAdapter mPlanAdapter;
    private RecyclerView mRvPlansContent;
    private String date;
    private PlanViewModel mViewModel;
    private ImageView mIvBack;

    @Override
    protected void onIntentData(Intent intent) {
        date = intent.getStringExtra(DAY_TIME_MS);
    }

    @Override
    protected void onDataInit() {
        mPlans = new ArrayList<>();
        mPlanAdapter = new PlanAdapter(mPlans);

        mViewModel = ViewModelProviders.of(this).get(PlanViewModel.class);
        mViewModel.getPlansWithDate(date)
                .observe(this, new Observer<List<Plan>>() {
                    @Override
                    public void onChanged(List<Plan> ePlans) {
                        //获取到列表
                        mPlans.clear();
                        mPlans.addAll(ePlans);
                        mPlanAdapter.notifyDataSetChanged();
                        if (ePlans.size() == 0) {
                            showToast("当天无计划安排");
                        }
                    }
                });
    }

    @Override
    protected void onViewInit() {
        mIvBack = findViewById(R.id.iv_back);
        mRvPlansContent = findViewById(R.id.rv_plans_content);

        mRvPlansContent.setLayoutManager(new LinearLayoutManager(this));
        mRvPlansContent.setAdapter(mPlanAdapter);

        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_day_plans;
    }
}
