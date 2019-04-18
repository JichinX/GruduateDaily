package me.djc.common.adapter;

import android.util.Log;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

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
    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;

    public FragmentsAdapter(@NonNull FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragmentManager = fm;
        mFragments = fragments;
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
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Log.i(TAG, "destroyItem: " + object);
        if (null == mTransaction) {
            mTransaction = mFragmentManager.beginTransaction();
        }
        mTransaction.remove((Fragment) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void setData(List<Fragment> fragments) {
        mFragments = fragments;
        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);
        if (mTransaction != null) {
            mTransaction.commitNowAllowingStateLoss();
            mTransaction = null;
        }
    }
}

