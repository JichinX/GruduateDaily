package me.djc.gruduatedaily.view.plan;

import android.view.View;
import androidx.fragment.app.Fragment;
import me.djc.base.fragment.SimpleFragment;
import me.djc.common.widget.FragmentsPager;
import me.djc.gruduatedaily.R;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * Calender 相关APi
 * 
 *public void scrollToPre();//滚动到上一个月
 * public void scrollToNext();//滚动到下一个月
 */
public class PlanFragment extends SimpleFragment {
    private FragmentsPager mPager;

    private Map<String, Fragment> mFragmentMap;

    public PlanFragment() {
        mFragmentMap = new LinkedHashMap<>();
        mFragmentMap.put("清单", OrderFragment.newInstance());
        mFragmentMap.put("今天", PlanListFragment.newInstance(PlanListFragment.DAY_TYPE_CUTTENT));
        mFragmentMap.put("明天", PlanListFragment.newInstance(PlanListFragment.DAY_TYPE_NEXT));
    }

    public static PlanFragment newInstance() {
        PlanFragment fragment = new PlanFragment();
        return fragment;
    }

    @Override
    protected View initView(View inflate) {
        mPager = inflate.findViewById(R.id.app_fragment_pager_plan);
        mPager.attachFragment(getChildFragmentManager());
        mPager.setFragmentMap(mFragmentMap);
        return inflate;
    }

    @Override
    protected int onCreateViewRes() {
        return R.layout.fragment_plan;
    }

    @Override
    public void onDataLazyLoad() {

    }
}
