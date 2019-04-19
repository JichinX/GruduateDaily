package me.djc.gruduatedaily.view.plan.adapter;

import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.common.util.ColorUtils;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.bean.Plan;

import java.util.List;

/**
 * Des:GruduateDaily - me.djc.gruduatedaily.view.plan.adapter
 *
 * @author xujichang
 * @date 2019-04-19 - 03:54
 * <p>
 * modify:
 */
public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.Holder> {
    private List<Plan> mPlans;


    public PlanAdapter(List<Plan> ePlans) {
        mPlans = ePlans;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_plan_item_plan, parent, false);
        return new Holder(vView);
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

        private TextView mTvContent;
        private TextView mTvTime;
        private Guideline mGuideCenter;
        private ImageView mIvOperate;
        private View mCenterLine;
        private ConstraintLayout mClContainer;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mTvContent = itemView.findViewById(R.id.app_plane_item_tv_content);
            mTvTime = itemView.findViewById(R.id.app_plane_item_tv_time);
            mGuideCenter = itemView.findViewById(R.id.guide_center);
            mIvOperate = itemView.findViewById(R.id.app_plane_item_iv_operate);
            mCenterLine = itemView.findViewById(R.id.app_plan_item_center_line);
            mClContainer = itemView.findViewById(R.id.cl_container);
        }

        public void bindData(Plan ePlan) {
            int position = getAdapterPosition();
            if (position % 2 == 0) {
                //偶数
                reverseLayout();
            } else {
                //奇数
                forwardLayout();
            }
            mTvContent.setText(String.format("%s \n %s", "#标题", "笔记内容"));
            mTvTime.setText(String.format("%s \n %s", "13:30", "2019/04/12"));
            int color = ColorUtils.createRandomColor();
            mIvOperate.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
//            mIvOperate.setColorFilter(ColorUtils.paletteIvColor(color));
        }

        private void forwardLayout() {
        }

        private void reverseLayout() {

        }
    }

}
