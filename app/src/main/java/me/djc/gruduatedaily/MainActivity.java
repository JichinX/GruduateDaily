package me.djc.gruduatedaily;

import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import me.djc.base.activity.BaseActivity;
import me.djc.base.fragment.IFragmentInteractionListener;

/**
 * 首页Activity
 */
public class MainActivity extends BaseActivity implements IFragmentInteractionListener {
    private BottomNavigationView mNavView;
    private ConstraintLayout mContainer;
    private Fragment mFlContent;

    @Override
    protected void onIntentData(Intent intent) {

    }

    @Override
    protected void onDataInit() {

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

    /**
     * 切换Fragment
     *
     * @param itemId
     */
    private void changeContentFragment(int itemId) {
        switch (itemId) {
            case R.id.navigation_ding:
                Navigation.findNavController(this, R.id.fragment).navigate(R.id.dingFragment);
                break;
            case R.id.navigation_plane:
                Navigation.findNavController(this, R.id.fragment).navigate(R.id.planeFragment);
                break;
            case R.id.navigation_static:
                Navigation.findNavController(this, R.id.fragment).navigate(R.id.staticFragment);
                break;
            default:
                break;
        }
    }

    @Override
    protected int getContentlayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void onFragmentInteraction(String tag, Uri uri) {

    }
}
