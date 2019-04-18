package me.djc.gruduatedaily.fragment;

import android.os.Bundle;
import android.view.View;
import me.djc.base.fragment.BaseFragment;
import me.djc.gruduatedaily.R;


public class DingFragment extends BaseFragment {

    public DingFragment() {
        // Required empty public constructor
    }

    public static PlanFragment newInstance(String param1, String param2) {
        PlanFragment fragment = new PlanFragment();

        return fragment;
    }

    @Override
    protected View initView(View inflate) {
        return inflate;
    }

    @Override
    protected void onGetArguments(Bundle arguments) {

    }

    @Override
    protected int onCreateViewRes() {
        return R.layout.fragment_ding;
    }

    @Override
    public void onDataLazyLoad() {

    }

    @Override
    public void refresh() {

    }
}
