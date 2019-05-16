package me.djc.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import me.djc.base.factory.IView;

/**
 * Des:GruduateDaily - me.djc.base.activity
 *
 * @author xujichang
 * @date 2019-04-15 - 20:47
 * <p>
 * modify:
 */
public abstract class BaseActivity extends AppCompatActivity implements IView {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onIntentData(getIntent());
        setContentView(getContentLayoutRes());
        onDataInit();
        onViewInit();
    }

    protected abstract void onIntentData(Intent intent);

    /**
     * 初始化数据
     */
    protected abstract void onDataInit();

    /**
     * 初始化View
     */
    protected abstract void onViewInit();

    /**
     * @return
     */
    @LayoutRes
    protected abstract int getContentLayoutRes();


    protected Context getContext() {
        return this;
    }
}
