package me.djc.gruduatedaily.view.plan;


import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.base.fragment.SimpleFragment;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.bean.Order;
import me.djc.gruduatedaily.view.plan.adapter.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 清单列表
 */
public class OrderListFragment extends SimpleFragment {
    private RecyclerView mRvOrderList;
    private OrderAdapter mOrderAdapter;
    private List<Order> mOrders;

    public OrderListFragment() {
        mOrders = new ArrayList<>();
        mOrderAdapter = new OrderAdapter(mOrders);
    }


    public static OrderListFragment newInstance() {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onDataLazyLoad() {
        mOrders.add(new Order());
        mOrders.add(new Order());
        mOrders.add(new Order());
        mOrders.add(new Order());
        mOrders.add(new Order());
        mOrderAdapter.notifyDataSetChanged();
    }

    @Override
    protected View initView(View inflate) {
        mRvOrderList = inflate.findViewById(R.id.app_plan_rv_order_list);
        mRvOrderList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRvOrderList.setAdapter(mOrderAdapter);

        return inflate;
    }

    @Override
    protected int onCreateViewRes() {
        return R.layout.app_fragment_order_list;
    }
}
