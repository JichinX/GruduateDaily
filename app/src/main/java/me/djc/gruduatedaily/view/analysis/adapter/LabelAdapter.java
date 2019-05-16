package me.djc.gruduatedaily.view.analysis.adapter;

import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.room.entity.Label;

import java.util.List;

/**
 * @author xujichang
 * @date 2019/05/15
 **/
public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.Holder> {
    private List<Label> mLabels;
    private boolean showDelete = false;
    private OnLabelEditListener mEditListener;

    public void setEditListener(OnLabelEditListener eEditListener) {
        mEditListener = eEditListener;
    }

    public LabelAdapter(List<Label> eLabels) {
        mLabels = eLabels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bindData(mLabels.get(position));
    }

    @Override
    public int getItemCount() {
        return mLabels.size();
    }

    public void enableDelete(boolean enable) {
        showDelete = enable;
        notifyDataSetChanged();
    }

    class Holder extends RecyclerView.ViewHolder {

        private ImageView mIvLabelLogo;
        private TextView mIvLabelContent;
        private SwitchCompat mSwEnable;
        private ImageView mIvLabelDelete;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mIvLabelLogo = itemView.findViewById(R.id.iv_label_logo);
            mIvLabelContent = itemView.findViewById(R.id.iv_label_content);
            mSwEnable = itemView.findViewById(R.id.sw_enable);
            mIvLabelDelete = itemView.findViewById(R.id.iv_label_delete);
        }

        public void bindData(Label eLabel) {
            mIvLabelContent.setText(eLabel.getContent());
            ColorDrawable vDrawable = new ColorDrawable(eLabel.getClolor());
            mIvLabelLogo.setImageDrawable(vDrawable);
            if (showDelete) {
                mIvLabelDelete.setVisibility(View.VISIBLE);
                mSwEnable.setVisibility(View.GONE);

                mIvLabelDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mEditListener) {
                            mEditListener.onDelete(eLabel);
                        }
                    }
                });
            } else {
                mIvLabelDelete.setVisibility(View.GONE);
                mSwEnable.setVisibility(View.VISIBLE);
                mSwEnable.setOnCheckedChangeListener(null);
                mSwEnable.setChecked(eLabel.isEnable());

                mSwEnable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (null != mEditListener) {
                            mEditListener.onEnable(eLabel, isChecked);
                        }
                    }
                });
            }
        }
    }

    public interface OnLabelEditListener {
        void onDelete(Label eLabel);

        void onEnable(Label eLabel, boolean enable);
    }
}
