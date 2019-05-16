package me.djc.gruduatedaily.view.plan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.room.entity.Order;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.Holder> {
    private List<Order> mOrders;
    private OnOrderEditListener mOrderEditListener;

    public void setOrderEditListener(OnOrderEditListener eOrderEditListener) {
        mOrderEditListener = eOrderEditListener;
    }

    public OrderListAdapter(List<Order> eOrders) {
        mOrders = eOrders;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_plan_order_detail, parent, false));
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

        private TextView mTvOrder;
        private TextView mTvContent;
        private ImageView mIvOrderUp;
        private ImageView mIvOrderDown;
        private ImageView mIvOrderEdit;

        public Holder(@NonNull View itemView) {
            super(itemView);

            mTvOrder = itemView.findViewById(R.id.tv_order);
            mTvContent = itemView.findViewById(R.id.tv_content);
            mIvOrderUp = itemView.findViewById(R.id.iv_order_up);
            mIvOrderDown = itemView.findViewById(R.id.iv_order_down);
            mIvOrderEdit = itemView.findViewById(R.id.iv_order_edit);
        }

        public void bindData(Order eOrder) {
            int position = getAdapterPosition();
            mTvContent.setText(eOrder.getContent());
            mTvOrder.setText(String.valueOf(position + 1));
            if (position == 0) {
                mIvOrderUp.setVisibility(View.INVISIBLE);
                mIvOrderUp.setEnabled(false);
            } else {
                mIvOrderUp.setVisibility(View.VISIBLE);
                mIvOrderUp.setEnabled(true);
            }
            if (position == getItemCount() - 1) {
                mIvOrderDown.setVisibility(View.INVISIBLE);
                mIvOrderDown.setEnabled(false);
            } else {
                mIvOrderDown.setVisibility(View.VISIBLE);
                mIvOrderDown.setEnabled(true);
            }


            mIvOrderDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //下滑位置
                    if (null != mOrderEditListener) {
                        mOrderEditListener.onSlideDown(eOrder, position);
                    }
                }
            });
            mIvOrderUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //上滑位置
                    if (null != mOrderEditListener) {
                        mOrderEditListener.onSlideUp(eOrder, position);
                    }
                }
            });
            mIvOrderEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //编辑
                    if (null != mOrderEditListener) {
                        mOrderEditListener.onEdit(eOrder);
                    }
                }
            });

        }
    }

    public interface OnOrderEditListener {

        void onSlideUp(Order eOrder, int ePosition);

        void onSlideDown(Order eOrder, int ePosition);

        void onEdit(Order eOrder);
    }
}
