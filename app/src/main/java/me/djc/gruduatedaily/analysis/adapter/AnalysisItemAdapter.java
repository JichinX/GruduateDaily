package me.djc.gruduatedaily.analysis.adapter;

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
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.bean.AnalysisData;

import java.util.ArrayList;
import java.util.List;

/**
 * Des:GruduateDaily - me.djc.gruduatedaily.analysis.adapter
 *
 * @author xujichang
 * @date 2019-04-18 - 17:16
 * <p>
 * modify:
 */
public class AnalysisItemAdapter extends RecyclerView.Adapter<AnalysisItemAdapter.ViewHolder> {
    private List<AnalysisData> mData;

    public AnalysisItemAdapter(List<AnalysisData> eData) {
        mData = eData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item_analysis, parent, false);
        return new ViewHolder(vView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
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


        public void bindData(AnalysisData eAnalysisData) {
            onBindData(eAnalysisData, itemView, getAdapterPosition());
        }

        private void initView(View eItemView) {
            mItemTvType = eItemView.findViewById(R.id.app_analysis_item_tv_type);
            mItemChat = eItemView.findViewById(R.id.app_analysis_item_chat);
        }

        private void onBindData(AnalysisData eAnalysisData, View eItemView, int eAdapterPosition) {
            mItemTvType.setText("数学");
            List<IBarDataSet> vSets = new ArrayList<>();
            List<BarEntry> vEntryList = new ArrayList<>();
            vEntryList.add(new BarEntry(10f, 20f));
            vEntryList.add(new BarEntry(10f, 20f));
            vEntryList.add(new BarEntry(10f, 20f));
            vEntryList.add(new BarEntry(10f, 20f));
            vEntryList.add(new BarEntry(10f, 20f));

            vSets.add(new BarDataSet(vEntryList, "label"));
            BarData vBarData = new BarData(vSets);
            mItemChat.setData(vBarData);
        }
    }
}
