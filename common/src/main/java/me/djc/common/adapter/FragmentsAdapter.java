package me.djc.common.adapter;

import android.util.Log;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;
import java.util.Random;

import static android.view.View.NO_ID;

/**
 * Des:EGSApp - com.codvision.egsapp.module.main.adapters
 *
 * @author xujichang
 * @date 2019/2/18 11:18
 * <p>
 * modify:
 */
public class FragmentsAdapter extends FragmentPagerAdapter {
    private static final String TAG = FragmentsAdapter.class.getSimpleName();
    private List<Fragment> mFragments;
    private List<String> mTabs;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private long mTag;

    public FragmentsAdapter(@NonNull FragmentManager fm, List<Fragment> fragments, List<String> eTabs) {
        super(fm);
        mTag = new Random().nextLong();
        mFragmentManager = fm;
        mFragments = fragments;
        mTabs = eTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object o = super.instantiateItem(container, position);
        Log.i(TAG, "instantiateItem: " + o);
        return o;
    }

    @Override
    public long getItemId(int position) {
        return mTag + position;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position);
    }
}

