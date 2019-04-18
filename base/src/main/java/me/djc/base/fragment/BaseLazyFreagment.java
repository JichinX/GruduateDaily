package me.djc.base.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import me.xujichang.xbase.baseutils.shake.XOnClickListener;
import me.xujichang.xbase.baseutils.view.ViewHelper;

import java.util.ArrayList;

/**
 * Des:EGSApp - com.codvision.egsapp.ext
 *
 * @author xujichang
 * @date 2019/2/18 14:22
 * <p>
 * modify:
 */
public abstract class BaseLazyFreagment extends Fragment {
    public static final String TITLE = "title";
    private static final String TAG = "BaseLazyFreagment";
    private ViewHelper mViewHelper;
    private boolean isPrepared = false;
    private boolean isLazyLoaded = false;
    private ArrayList<String> titles;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewHelper = ViewHelper.getInstance(this);
        if (null != getArguments()) {
            titles = getArguments().getStringArrayList(TITLE);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
            onDataLazyLoad();
            isLazyLoaded = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    public abstract void onDataLazyLoad();


    //防抖点击
    protected <V extends View> void proxyClick(V view, final XOnClickListener<V> listener,
                                               boolean enable) {
        if (null == listener) {
            return;
        }
        if (enable) {
            mViewHelper.proxyClick(view, listener);
        } else {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick((V) v);
                }
            });
        }
    }

    protected <V extends View> void proxyClick(V view, final XOnClickListener<V> listener) {
        proxyClick(view, listener, mViewHelper != null);
    }

    protected LifecycleOwner getLifecycleOwner() {
        return this;
    }

    protected void disableEditTextFocus(EditText etDeviceSearch) {
        ((ViewGroup) etDeviceSearch.getParent()).setFocusable(true);
        ((ViewGroup) etDeviceSearch.getParent()).setFocusableInTouchMode(true);
    }

    public abstract void refresh();

    protected Fragment getFrament() {
        return this;
    }
}
