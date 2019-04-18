package me.djc.gruduatedaily.analysis.adapter;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Des:GruduateDaily - me.djc.gruduatedaily.analysis.adapter
 *
 * @author xujichang
 * @date 2019-04-18 - 18:00
 * <p>
 * modify:
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements IViewHolder<T> {
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
