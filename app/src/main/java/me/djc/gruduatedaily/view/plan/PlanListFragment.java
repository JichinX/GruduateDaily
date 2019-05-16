package me.djc.gruduatedaily.view.plan;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.base.fragment.SimpleFragment;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.base.AppConst;
import me.djc.gruduatedaily.room.entity.Plan;
import me.djc.gruduatedaily.view.plan.adapter.PlanAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 计划列表
 */
public class PlanListFragment extends SimpleFragment {
    public static final int DAY_TYPE_CUTTENT = 1;
    public static final int DAY_TYPE_NEXT = 2;
    private RecyclerView mRvPlanList;
    private List<Plan> mPlans;
    private PlanAdapter mPlanAdapter;
    private PlanViewModel mViewModel;

    public static final String argument_type = "argument_type";
    private int dayType = DAY_TYPE_CUTTENT;
    private ImageView mIvPlansEdit;
    private RecyclerView mAppPlanRvPlanList;
    private LinearLayout mLlMark;
    private TextView mTvDing;
    private ImageView mEtCamera;
    private EditText mEtMark;

    public PlanListFragment() {
        mPlans = new ArrayList<>();
        mPlanAdapter = new PlanAdapter(mPlans);
    }

    public static PlanListFragment newInstance(int type) {
        PlanListFragment fragment = new PlanListFragment();
        Bundle args = new Bundle();
        args.putInt(argument_type, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            dayType = getArguments().getInt(argument_type);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDataLazyLoad() {
        mViewModel = ViewModelProviders.of(getActivity()).get(PlanViewModel.class);
        mViewModel.getPlans(dayType).observe(getLifecycleOwner(), new Observer<List<Plan>>() {
            @Override
            public void onChanged(List<Plan> ePlans) {
                mPlans.clear();
                mPlans.addAll(ePlans);
                mPlanAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected View initView(View inflate) {
        mRvPlanList = inflate.findViewById(R.id.app_plan_rv_plan_list);
        mIvPlansEdit = inflate.findViewById(R.id.iv_plans_edit);
        mLlMark = inflate.findViewById(R.id.ll_mark);
        mTvDing = inflate.findViewById(R.id.tv_ding);

        mEtCamera = inflate.findViewById(R.id.et_camera);
        mEtMark = inflate.findViewById(R.id.et_mark);

        mRvPlanList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvPlanList.setAdapter(mPlanAdapter);

        mIvPlansEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑
                Intent vIntent = new Intent(getContext(), PlanEditActivity.class);
                vIntent.putExtra(AppConst.Value.DAY_TYPE, dayType);
                startActivity(vIntent);
            }
        });
        if (dayType == DAY_TYPE_CUTTENT) {
            mLlMark.setVisibility(View.VISIBLE);
            mTvDing.setVisibility(View.VISIBLE);
        }
        return inflate;
    }

    @Override
    protected int onCreateViewRes() {
        return R.layout.app_fragment_plan_list;
    }

}
