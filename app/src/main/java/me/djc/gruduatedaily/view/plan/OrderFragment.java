package me.djc.gruduatedaily.view.plan;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.base.fragment.SimpleFragment;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.base.AppConst;
import me.djc.gruduatedaily.room.entity.Order;
import me.djc.gruduatedaily.view.plan.adapter.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 清单列表
 */
public class OrderFragment extends SimpleFragment {
    private OrderAdapter mBothOrderAdapter;
    private List<Order> mBothOrders;
    private OrderAdapter mImportantOrderAdapter;
    private List<Order> mImportantOrders;
    private OrderAdapter mUrgentOrderAdapter;
    private List<Order> mUrgentOrders;
    private OrderAdapter mNoneOrderAdapter;
    private List<Order> mNoneOrders;

    private PlanViewModel mViewModel;
    private RecyclerView mRvOrdersImportantUrgent;
    private RecyclerView mRvOrdersImportant;
    private RecyclerView mRvOrdersUrgent;
    private RecyclerView mRvOrdersNone;
    private ImageView mIvOrdersImportantUrgent;
    private ImageView mIvOrdersImportant;
    private ImageView mIvOrdersUrgent;
    private ImageView mIvOrdersNone;

    public OrderFragment() {
        mBothOrders = new ArrayList<>();
        mBothOrderAdapter = new OrderAdapter(mBothOrders);
        mImportantOrders = new ArrayList<>();
        mImportantOrderAdapter = new OrderAdapter(mImportantOrders);
        mUrgentOrders = new ArrayList<>();
        mUrgentOrderAdapter = new OrderAdapter(mUrgentOrders);
        mNoneOrders = new ArrayList<>();
        mNoneOrderAdapter = new OrderAdapter(mNoneOrders);
    }


    public static OrderFragment newInstance() {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDataLazyLoad() {
        mViewModel = ViewModelProviders.of(getActivity()).get(PlanViewModel.class);
        mViewModel.getBothOrders().observe(getLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> eOrders) {
                notifyOrdersData(eOrders, mBothOrders, mBothOrderAdapter);
            }
        });
        mViewModel.getImportantOrders().observe(getLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> eOrders) {
                notifyOrdersData(eOrders, mImportantOrders, mImportantOrderAdapter);
            }
        });
        mViewModel.getUrgentOrders().observe(getLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> eOrders) {
                notifyOrdersData(eOrders, mUrgentOrders, mUrgentOrderAdapter);
            }
        });
        mViewModel.getNoneOrders().observe(getLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> eOrders) {
                notifyOrdersData(eOrders, mNoneOrders, mNoneOrderAdapter);
            }
        });
    }

    private void notifyOrdersData(List<Order> eDataOrders, List<Order> eSrcOrders, OrderAdapter eAdapter) {
        eSrcOrders.clear();
        eSrcOrders.addAll(eDataOrders);
        eAdapter.notifyDataSetChanged();
    }

    @Override
    protected View initView(View inflate) {

        mRvOrdersImportantUrgent = inflate.findViewById(R.id.rv_orders_important_urgent);
        mRvOrdersImportant = inflate.findViewById(R.id.rv_orders_important);
        mRvOrdersUrgent = inflate.findViewById(R.id.rv_orders_urgent);
        mRvOrdersNone = inflate.findViewById(R.id.rv_orders_none);
        mIvOrdersImportantUrgent = inflate.findViewById(R.id.iv_orders_important_urgent);
        mIvOrdersImportant = inflate.findViewById(R.id.iv_orders_important);
        mIvOrdersUrgent = inflate.findViewById(R.id.iv_orders_urgent);
        mIvOrdersNone = inflate.findViewById(R.id.iv_orders_none);

        mRvOrdersImportantUrgent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvOrdersImportantUrgent.setAdapter(mBothOrderAdapter);
        mRvOrdersImportant.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvOrdersImportant.setAdapter(mImportantOrderAdapter);
        mRvOrdersUrgent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvOrdersUrgent.setAdapter(mUrgentOrderAdapter);
        mRvOrdersNone.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvOrdersNone.setAdapter(mNoneOrderAdapter);
        mIvOrdersImportant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrders(AppConst.OrderType.IMPORTANT);
            }
        });
        mIvOrdersImportantUrgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrders(AppConst.OrderType.IMPORTANT_URGENT);
            }
        });
        mIvOrdersNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrders(AppConst.OrderType.NONE);
            }
        });
        mIvOrdersUrgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrders(AppConst.OrderType.URGENT);
            }
        });
        return inflate;
    }

    private void editOrders(int type) {
        Intent vIntent = new Intent(getContext(), OrderDetailActivity.class);
        vIntent.putExtra(AppConst.Value.ORDER_TYPE, type);
        startActivity(vIntent);
    }

    @Override
    protected int onCreateViewRes() {
        return R.layout.app_fragment_order_list;
    }
}
