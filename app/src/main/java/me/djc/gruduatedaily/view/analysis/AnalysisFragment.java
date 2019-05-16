package me.djc.gruduatedaily.view.analysis;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import me.djc.base.fragment.SimpleFragment;
import me.djc.common.widget.FragmentsPager;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.base.AppConst;

import java.util.LinkedHashMap;
import java.util.Map;


public class AnalysisFragment extends SimpleFragment {

    private Map<String, Fragment> mFragmentMap;
    private FragmentsPager mPager;
    private TextView mTvTitle;
    private ImageView mIvSeeting;

    private AnalysisFragment() {
        mFragmentMap = new LinkedHashMap<>();
        mFragmentMap.put("日", DataAnalysisFragment.newInstance(AppConst.Analysistype.TAG_DAY));
        mFragmentMap.put("周", DataAnalysisFragment.newInstance(AppConst.Analysistype.TAG_WEEK));
        mFragmentMap.put("本月", DataAnalysisFragment.newInstance(AppConst.Analysistype.TAG_MOUNTH));
    }

    public static AnalysisFragment newInstance() {
        AnalysisFragment fragment = new AnalysisFragment();

        return fragment;
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
                mIvSeeting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //标签设置
                        startActivity(new Intent(getContext(), LabelSettingActivity.class));
                    }
                });
            }
        });
        mPager.setFragmentMap(mFragmentMap);
        mPager.attachFragment(getChildFragmentManager());
        mPager.setTitleRes(R.layout.app_analusis_title);
        return inflate;
    }

    @Override
    protected int onCreateViewRes() {
        return R.layout.fragment_analysis;
    }

    @Override
    public void onDataLazyLoad() {

    }
}
