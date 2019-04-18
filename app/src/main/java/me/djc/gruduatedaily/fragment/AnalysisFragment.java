package me.djc.gruduatedaily.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import me.djc.base.fragment.SimpleFragment;
import me.djc.common.widget.FragmentsPager;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.analysis.DataAnalysisFragment;

import java.util.HashMap;
import java.util.Map;


public class AnalysisFragment extends SimpleFragment {

    private FragmentsPager mPager;
    private TextView mTvTitle;
    private ImageView mIvSeeting;

    public AnalysisFragment() {
        // Required empty public constructor
    }

    @Override
    protected View initView(View inflate) {
        mPager = inflate.findViewById(R.id.app_fragment_pager_analysis);
        mPager.setViewInflateListener(new FragmentsPager.onViewInflateListener() {
            @Override
            public void onInitTitle(View eView) {
                mTvTitle = eView.findViewById(R.id.app_analysis_tv_title);
                mIvSeeting = eView.findViewById(R.id.app_analysis_iv_seeting);
                mTvTitle.setText("统计");
            }
        });
        mPager.setFragmentMap(generatePagerMap());
        mPager.attachFragment(getChildFragmentManager());
        mPager.setTitleRes(R.layout.app_analusis_title);
        return inflate;
    }

    private Map<String, Fragment> generatePagerMap() {
        Map<String, Fragment> vFragmentMap = new HashMap<>();
        vFragmentMap.put("日", DataAnalysisFragment.newInstance(1));
        vFragmentMap.put("周", DataAnalysisFragment.newInstance(2));
        vFragmentMap.put("本月", DataAnalysisFragment.newInstance(3));
        return vFragmentMap;
    }

    @Override
    protected int onCreateViewRes() {
        return R.layout.fragment_analysis;
    }

    @Override
    public void onDataLazyLoad() {

    }
}
