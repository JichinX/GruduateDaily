package me.djc.gruduatedaily.view.plan;

import android.content.Intent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import me.djc.base.activity.BaseActivity;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.base.AppConst;
import me.djc.gruduatedaily.room.entity.Order;
import me.djc.gruduatedaily.view.plan.adapter.OrderListAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends BaseActivity {
    private OrderListAdapter mOrderListAdapter;
    private List<Order> mOrders;
    private int type;
    private RecyclerView mRvOrders;
    private FloatingActionButton mFabOrderAdd;
    private PlanViewModel mViewModel;

    @Override
    protected void onIntentData(Intent intent) {
        mOrders = new ArrayList<>();
        mOrderListAdapter = new OrderListAdapter(mOrders);
        type = intent.getIntExtra(AppConst.Value.ORDER_TYPE, AppConst.OrderType.IMPORTANT_URGENT);
    }

    @Override
    protected void onDataInit() {
        mViewModel = ViewModelProviders.of(this).get(PlanViewModel.class);
        mViewModel.getOrders(type).observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> eOrders) {
                mOrders.clear();
                mOrders.addAll(eOrders);
                mOrderListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onViewInit() {
        mRvOrders = findViewById(R.id.rv_orders);
        mFabOrderAdd = findViewById(R.id.fab_order_add);

        mRvOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvOrders.setAdapter(mOrderListAdapter);
        mRvOrders.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        mFabOrderAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加清单
                showOrderAddDialog();
            }
        });
        mOrderListAdapter.setOrderEditListener(new OrderListAdapter.OnOrderEditListener() {
            @Override
            public void onSlideUp(Order eOrder, int ePosition) {
                //TODO 上移

            }

            @Override
            public void onSlideDown(Order eOrder, int ePosition) {
                //TODO 下滑

            }

            @Override
            public void onEdit(Order eOrder) {
                //TODO 编辑

            }
        });
    }

    private void showOrderAddDialog() {
        MaterialDialog.Builder vBuilder = new MaterialDialog.Builder(this);
        vBuilder.title("添加清单")
                .input("输入清单内容", null, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        addOrders(input.toString());
                        dialog.dismiss();
                    }
                })
                .positiveText("添加")
                .show();
    }

    private void addOrders(String str) {
        long current = System.currentTimeMillis();
        Order vOrder = new Order();
        vOrder.setContent(str);
        vOrder.setType(type);
        vOrder.setDateTimeStart(current);
        vOrder.setDateTimeEnd(current + 7 * 24 * 60 * 60 * 1000);
        mViewModel.addOrder(vOrder);
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_order_detail;
    }
}
