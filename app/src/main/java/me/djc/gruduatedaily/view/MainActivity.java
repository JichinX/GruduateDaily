package me.djc.gruduatedaily.view;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import me.djc.base.activity.BaseActivity;
import me.djc.base.fragment.IFragmentInteractionListener;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.view.analysis.AnalysisFragment;
import me.djc.gruduatedaily.view.ding.DingFragment;
import me.djc.gruduatedaily.view.plan.PlanFragment;

/**
 * 首页Activity
 */
public class MainActivity extends BaseActivity implements IFragmentInteractionListener {
    private static final String TAG = "MainActivity";
    private BottomNavigationView mNavView;
    private ConstraintLayout mContainer;
    private Fragment mDingFragment, mPlanFragment, mAnalysisFragment;
    private Fragment currentFragment;
    private FragmentManager mManager;

    @Override
    protected void onIntentData(Intent intent) {

    }

    @Override
    protected void onDataInit() {
        mManager = getSupportFragmentManager();
        initFragments();
    }

    private void initFragments() {
        currentFragment = new Fragment();
        mDingFragment = DingFragment.newInstance();
        mPlanFragment = PlanFragment.newInstance();
        mAnalysisFragment = AnalysisFragment.newInstance();
    }

    @Override
    protected void onViewInit() {
        mNavView = findViewById(R.id.nav_view);
        mContainer = findViewById(R.id.container);

        mNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                changeContentFragment(menuItem.getItemId());
                return true;
            }
        });
        //默认初始化选中1
        mNavView.setSelectedItemId(R.id.navigation_ding);
    }

    private void showFragment(Fragment eFragment) {
        Log.i(TAG, "will showFragment: " + eFragment);
        if (currentFragment != eFragment) {
            FragmentTransaction transaction = mManager.beginTransaction();
            transaction.hide(currentFragment);
            Log.i(TAG, "hideFragment: " + currentFragment);
            currentFragment = eFragment;
            if (!eFragment.isAdded()) {
                transaction.add(R.id.fragment, eFragment).show(eFragment).commit();
                Log.i(TAG, "showFragment: " + eFragment);
            } else {
                transaction.show(eFragment).commit();
            }
        }
    }

    /**
     * 切换Fragment
     *
     * @param itemId
     */
    private void changeContentFragment(int itemId) {
        switch (itemId) {
            case R.id.navigation_ding:
                showFragment(mDingFragment);
                break;
            case R.id.navigation_plane:

                showFragment(mPlanFragment);
                break;
            case R.id.navigation_static:
                showFragment(mAnalysisFragment);
                break;
            default:
                break;
        }
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void onFragmentInteraction(String tag, Uri uri) {

    }
}
