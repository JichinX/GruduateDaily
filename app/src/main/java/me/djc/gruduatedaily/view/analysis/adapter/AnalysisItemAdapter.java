package me.djc.gruduatedaily.view.analysis.adapter;

import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import me.djc.common.util.CalenderUtil;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.base.AppConst;
import me.djc.gruduatedaily.bean.AnalysisData;
import me.djc.gruduatedaily.room.entity.Label;
import me.djc.gruduatedaily.room.entity.Plan;
import me.djc.gruduatedaily.view.analysis.DataAnalysisFragment;

import java.util.*;

/**
 * Des:GruduateDaily - me.djc.gruduatedaily.view.analysis.adapter
 *
 * @author xujichang
 * @date 2019-04-18 - 17:16
 * <p>
 * modify:
 */
public class AnalysisItemAdapter extends RecyclerView.Adapter<AnalysisItemAdapter.ViewHolder> {
    private LongSparseArray<DataAnalysisFragment.LabelProxy> mData;

    public AnalysisItemAdapter(LongSparseArray<DataAnalysisFragment.LabelProxy> eData) {
        mData = eData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item_analysis, parent, false);
        vView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(vView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mData.valueAt(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemTvType;
        private BarChart mItemChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }


        public void bindData(DataAnalysisFragment.LabelProxy eAnalysisData) {
            onBindData(eAnalysisData, itemView, getAdapterPosition());
        }

        private void initView(View eItemView) {
            mItemTvType = eItemView.findViewById(R.id.app_analysis_item_tv_type);
            mItemChat = eItemView.findViewById(R.id.app_analysis_item_chat);
        }

        private void onBindData(DataAnalysisFragment.LabelProxy eProxy, View eItemView, int eAdapterPosition) {
            Label vLabel = eProxy.getLabel();
            mItemTvType.setText(vLabel.getContent());
            List<Plan> vPlans = eProxy.getPlans();
            //处理当前标签下的计划数据
            //按时间整理
            Map<String, TimeCount> vCountMap = new HashMap<>();
            for (Plan vPlan : vPlans) {
                String date = vPlan.getDate();
                long time = vPlan.getTimeEnd() - vPlan.getTimeStart();
                float hourTime = (float) time / CalenderUtil.HOUR_MS;
                if (!vCountMap.containsKey(date)) {
                    vCountMap.put(vPlan.getDate(), new TimeCount(hourTime));
                } else {
                    vCountMap.get(date).hours += hourTime;
                }
            }

            List<BarEntry> vEntries = new ArrayList<>();
            //转为
            int index = 0;
            for (Map.Entry<String, TimeCount> vEntry : vCountMap.entrySet()) {
                vEntries.add(new BarEntry(index, vEntry.getValue().getHours()));
                index++;
            }
            BarDataSet vDataSet = new BarDataSet(vEntries, "时间");
            List<IBarDataSet> vDataSets = new ArrayList<>();
            vDataSets.add(vDataSet);
            BarData vBarData = new BarData(vDataSets);
            vBarData.setBarWidth(0.4f);
            mItemChat.setData(vBarData);
        }
    }

    public class TimeCount {
        private float hours;

        public TimeCount(float eHourTime) {
            hours = eHourTime;
        }

        public float getHours() {
            return hours;
        }

        public void setHours(float eHours) {
            hours = eHours;
        }
    }
}
