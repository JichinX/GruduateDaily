package me.djc.gruduatedaily.analysis.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Des:GruduateDaily - me.djc.gruduatedaily.analysis.adapter
 *
 * @author xujichang
 * @date 2019-04-18 - 17:56
 * <p>
 * modify:
 */
public interface IViewHolder<T> {
    void onBindData(T eT);

    void onInitView(View eView);
}
