package me.djc.gruduatedaily.view;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import me.djc.base.activity.BaseActivity;
import me.djc.common.util.PrefUtil;
import me.djc.gruduatedaily.R;

/**
 * @author xujichang
 * @date 2019/05/27
 **/
public class PersonalActivity extends BaseActivity {
    private TextView mTvLoginOut;

    @Override
    protected void onIntentData(Intent intent) {

    }

    @Override
    protected void onDataInit() {

    }

    @Override
    protected void onViewInit() {

        mTvLoginOut = findViewById(R.id.tv_login_out);
        mTvLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtil.clearLoginInfo(getContext());
                Intent vIntent = new Intent(getContext(), LoginActivity.class);
                vIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(vIntent);
            }
        });
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_personal;
    }
}
