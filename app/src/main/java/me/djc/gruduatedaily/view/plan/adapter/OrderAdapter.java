package me.djc.gruduatedaily.view.plan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.room.entity.Order;

import java.util.List;

/**
 * Des:GruduateDaily - me.djc.gruduatedaily.room.entity
 *
 * @author xujichang
 * @date 2019-04-19 - 03:25
 * <p>
 * modify:
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.Holder> {
    private List<Order> mOrders;

    public OrderAdapter(List<Order> eOrders) {
        mOrders = eOrders;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_plan_item_order, parent, false);
        return new Holder(vView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindData(mOrders.get(position));
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView mContent;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.tv_order_content);
        }

        public void bindData(Order eOrder) {
            mContent.setText(eOrder.getContent());
        }
    }
}
