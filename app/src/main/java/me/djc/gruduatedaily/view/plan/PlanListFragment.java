package me.djc.gruduatedaily.view.plan;


import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.base.fragment.SimpleFragment;
import me.djc.common.widget.FootAndHeaderRv;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.bean.Plan;
import me.djc.gruduatedaily.view.plan.adapter.PlanAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 计划列表
 */
public class PlanListFragment extends SimpleFragment {


    private FootAndHeaderRv mRvPlanList;
    private List<Plan> mPlans;
    private PlanAdapter mPlanAdapter;

    public PlanListFragment() {
        mPlans = new ArrayList<>();
        mPlanAdapter = new PlanAdapter(mPlans);
    }

    public static PlanListFragment newInstance() {
        PlanListFragment fragment = new PlanListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDataLazyLoad() {
        mPlans.add(new Plan());
        mPlans.add(new Plan());
        mPlans.add(new Plan());
        mPlans.add(new Plan());
        mPlans.add(new Plan());
        mPlans.add(new Plan());
        mPlans.add(new Plan());
        mPlans.add(new Plan());
        mPlans.add(new Plan());
        mPlans.add(new Plan());
        mPlanAdapter.notifyDataSetChanged();
    }

    @Override
    protected View initView(View inflate) {
        mRvPlanList = inflate.findViewById(R.id.app_plan_rv_plan_list);
        mRvPlanList.initData(0, 0);
        RecyclerView mRvList = mRvPlanList.getRv();
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvList.setAdapter(mPlanAdapter);
        return inflate;
    }

    @Override
    protected int onCreateViewRes() {
        return R.layout.app_fragment_plan_list;
    }

}
