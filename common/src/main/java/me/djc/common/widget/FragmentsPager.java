package me.djc.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.google.common.base.Strings;
import me.djc.common.R;
import me.djc.common.adapter.FragmentsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Des:GruduateDaily - me.djc.common.widget
 *
 * @author xujichang
 * @date 2019-04-15 - 20:48
 * <p>
 * modify:
 */
public class FragmentsPager extends LinearLayoutCompat {

    private Map<String, Fragment> mFragmentMap;
    private boolean titleEnable = false;
    private ViewStub mTitle;
    private TabLayout mTabFragments;
    private ViewPager mViewPager;
    private String mTitleStr;
    private FragmentsAdapter mFragmentsAdapter;
    private FragmentActivity mActivity;
    private FragmentManager mFragmentManager;
    private onViewInflateListener mViewInflateListener;
    private List<Fragment> mFragments;
    private List<String> mTabs;
    private View mTitleView;
    private int mTitleRes;

    public FragmentsPager(Context context) {
        this(context, null);
    }

    public FragmentsPager(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FragmentsPager(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    public FragmentsPager(Context context, Map<String, Fragment> fragmentMap, boolean titleEnable) {
        this(context);
        this.mFragmentMap = fragmentMap;
        this.titleEnable = titleEnable;
        patchData();
    }

    public void setTitleRes(int eRes) {
        mTitle.setLayoutResource(eRes);
        initTitle();
    }

    public void attachActivity(FragmentActivity activityer) {
        mFragmentManager = activityer.getSupportFragmentManager();
        patchData();
    }

    public void attachFragment(FragmentManager fragmentManagerer) {
        mFragmentManager = fragmentManagerer;
        patchData();
    }

    public void setFragmentMap(Map<String, Fragment> eFragmentMap) {
        mFragmentMap = eFragmentMap;
        patchData();
    }

    public void setFragments(List<Fragment> eFragments) {
        mFragments = eFragments;
        patchData();
    }

    public void setTabs(List<String> eTabs) {
        mTabs = eTabs;
        patchData();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.custom_view_fragments_pager, this, true);
        mFragments = new ArrayList<>();
        mTabs = new ArrayList<>();
        mTitle = findViewById(R.id.title);

        mTabFragments = findViewById(R.id.tab_fragments);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(3);
        mTabFragments.setupWithViewPager(mViewPager);
        patchData();
    }

    private void patchData() {
        if (null == mFragmentManager) {
            return;
        }
        if (null == mFragmentsAdapter) {
            mFragmentsAdapter = new FragmentsAdapter(mFragmentManager, mFragments, mTabs);
            mViewPager.setAdapter(mFragmentsAdapter);
        }
        if (null != mFragmentMap) {
            initFragments();
        }
        if (titleEnable && !Strings.isNullOrEmpty(mTitleStr)) {
            initTitle();
        }
    }

    private void initTitle() {
        if (0 == mTitle.getLayoutResource()) {
            return;
        }
        mTitleView = mTitle.inflate();
        if (null != mViewInflateListener) {
            mViewInflateListener.onInitTitle(mTitleView);
        }
    }

    private void initFragments() {
        if (null == mFragmentMap || null == mFragmentManager) {
            return;
        }
        splitMap();
        initTabs();
        mFragmentsAdapter.notifyDataSetChanged();
    }

    private void initTabs() {
        mTabFragments.removeAllTabs();
        for (String vTab : mTabs) {
            mTabFragments.addTab(mTabFragments.newTab().setText(vTab));
        }
    }

    private void splitMap() {
        mFragments.clear();
        mTabs.clear();
        for (Map.Entry<String, Fragment> vEntry : mFragmentMap.entrySet()) {
            mFragments.add(vEntry.getValue());
            mTabs.add(vEntry.getKey());
        }
    }

    public void setViewInflateListener(onViewInflateListener eViewInflateListener) {
        mViewInflateListener = eViewInflateListener;
    }

    public interface onViewInflateListener {
        void onInitTitle(View eView);
    }
}
