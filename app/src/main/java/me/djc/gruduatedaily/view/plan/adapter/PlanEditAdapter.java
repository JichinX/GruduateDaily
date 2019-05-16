package me.djc.gruduatedaily.view.plan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.room.entity.Plan;
import me.xujichang.xbase.baseutils.strings.StringFormatUtil;

import java.util.List;

public class PlanEditAdapter extends RecyclerView.Adapter<PlanEditAdapter.Holder> {
    private List<Plan> mPlans;
    private OnPlanEditListener mPlanEditListener;

    public void setPlanEditListener(OnPlanEditListener ePlanEditListener) {
        mPlanEditListener = ePlanEditListener;
    }

    public PlanEditAdapter(List<Plan> ePlans) {
        mPlans = ePlans;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_edit, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindData(mPlans.get(position));
    }

    @Override
    public int getItemCount() {
        return mPlans.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private TextView mTvTimeStart;
        private TextView mTvTimeEnd;
        private TextView mTvPlanLabel;
        private ImageView mIvPlanDelete;
        private ImageView mIvPlanAdd;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mTvTimeStart = itemView.findViewById(R.id.tv_time_start);
            mTvTimeEnd = itemView.findViewById(R.id.tv_time_end);
            mTvPlanLabel = itemView.findViewById(R.id.tv_plan_label);
            mIvPlanDelete = itemView.findViewById(R.id.iv_plan_delete);
            mIvPlanAdd = itemView.findViewById(R.id.iv_plan_add);
        }

        public void bindData(Plan ePlan) {
            if (ePlan.getId() <= 0L) {
                mTvPlanLabel.setVisibility(View.GONE);
                mIvPlanAdd.setVisibility(View.VISIBLE);
                mIvPlanDelete.setVisibility(View.GONE);
            } else {
                mTvPlanLabel.setVisibility(View.VISIBLE);
                mIvPlanAdd.setVisibility(View.GONE);
                mIvPlanDelete.setVisibility(View.VISIBLE);

                mTvPlanLabel.setText(ePlan.getLabel());
            }
            mTvTimeStart.setText(StringFormatUtil.formatTime(ePlan.getTimeStart(), "HH:mm:ss"));
            mTvTimeEnd.setText(StringFormatUtil.formatTime(ePlan.getTimeEnd(), "HH:mm:ss"));

            mTvTimeStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mPlanEditListener) {
                        mPlanEditListener.onTimeStart(ePlan);
                    }
                }
            });
            mTvTimeEnd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mPlanEditListener) {
                        mPlanEditListener.onTimeEnd(ePlan);
                    }
                }
            });
            mIvPlanAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mPlanEditListener) {
                        mPlanEditListener.onAddLabel(ePlan);
                    }
                }
            });
            mIvPlanDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mPlanEditListener) {
                        mPlanEditListener.onDelete(ePlan);
                    }
                }
            });
        }
    }

    public interface OnPlanEditListener {

        void onDelete(Plan ePlan);

        void onAddLabel(Plan ePlan);

        void onTimeStart(Plan ePlan);

        void onTimeEnd(Plan ePlan);
    }
}
