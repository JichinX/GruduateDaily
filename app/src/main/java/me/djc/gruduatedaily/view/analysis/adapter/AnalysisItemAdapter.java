package me.djc.gruduatedaily.view.analysis.adapter;

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
import me.djc.gruduatedaily.base.AppConst;
import me.djc.gruduatedaily.bean.AnalysisData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Des:GruduateDaily - me.djc.gruduatedaily.view.analysis.adapter
 *
 * @author xujichang
 * @date 2019-04-18 - 17:16
 * <p>
 * modify:
 */
public class AnalysisItemAdapter extends RecyclerView.Adapter<AnalysisItemAdapter.ViewHolder> {
    private List<AnalysisData> mData;
    private Random mRandom;

    public AnalysisItemAdapter(List<AnalysisData> eData) {
        mData = eData;
        mRandom = new Random();
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
            mItemTvType.setText(eAnalysisData.getTitle());

            List<IBarDataSet> vSets = new ArrayList<>();
            List<BarEntry> vEntryList = new ArrayList<>();
            switch (eAnalysisData.getType()) {
                case AppConst.Analysistype.TAG_DAY:
                    for (int i = 1; i <= 12; i++) {
                        vEntryList.add(new BarEntry(i * 2, mRandom.nextInt(120)));
                    }
                    break;
                case AppConst.Analysistype.TAG_WEEK:
                    for (int i = 1; i <= 7; i++) {
                        vEntryList.add(new BarEntry(i, mRandom.nextInt(24)));
                    }
                    break;
                case AppConst.Analysistype.TAG_MOUNTH:
                    for (int i = 1; i <= 15; i++) {
                        vEntryList.add(new BarEntry(i * 2, mRandom.nextInt(48)));
                    }
                    break;
                case AppConst.Analysistype.TAG_YEAY:
                    for (int i = 1; i <= 12; i++) {
                        vEntryList.add(new BarEntry(i, mRandom.nextInt(30)));
                    }
                    break;
                default:
                    break;
            }


            vSets.add(new BarDataSet(vEntryList, "label"));
            BarData vBarData = new BarData(vSets);
//            vBarData.setValueTextSize(10f);
//            vBarData.setBarWidth(0.9f);

            mItemChat.setDrawBarShadow(false);
            mItemChat.setFitBars(false);
            mItemChat.setDrawGridBackground(false);
            mItemChat.setDrawValueAboveBar(true);
            mItemChat.setPinchZoom(false);
            mItemChat.setData(vBarData);
        }
    }
}
