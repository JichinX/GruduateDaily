package me.djc.gruduatedaily.view;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import me.djc.base.activity.BaseActivity;
import me.djc.gruduatedaily.R;

public class LoginActivity extends BaseActivity {

    private Guideline mGuide;
    private TextView mTvLabelName;
    private EditText mEtName;
    private LinearLayout mLlName;
    private TextView mTvLabelPwd;
    private EditText mEtPwd;
    private LinearLayout mLlPwd;
    private TextView mTvLogin;
    private LinearLayout mLlRegisterName;
    private LinearLayout mLlRegisterPwd;
    private LinearLayout mLlRegisterPhone;
    private LinearLayout mLlRegisterVCode;
    private TextView mTvRegister;
    private TextView mTvLabelLogin;
    private TextView mTvLabelRegister;
    private ConstraintLayout mClLogin;
    private ConstraintLayout mClRegister;

    @Override
    protected void onIntentData(Intent intent) {

    }

    @Override
    protected void onDataInit() {

    }

    @Override
    protected void onViewInit() {
        mGuide = findViewById(R.id.guide);
        mTvLabelName = findViewById(R.id.tv_label_name);
        mEtName = findViewById(R.id.et_name);
        mLlName = findViewById(R.id.ll_name);
        mTvLabelPwd = findViewById(R.id.tv_label_pwd);
        mEtPwd = findViewById(R.id.et_pwd);
        mLlPwd = findViewById(R.id.ll_pwd);
        mTvLogin = findViewById(R.id.tv_login);
        mLlRegisterName = findViewById(R.id.ll_register_name);
        mLlRegisterPwd = findViewById(R.id.ll_register_pwd);
        mLlRegisterPhone = findViewById(R.id.ll_register_phone);
        mLlRegisterVCode = findViewById(R.id.ll_register_v_code);
        mTvRegister = findViewById(R.id.tv_register);
        mTvLabelLogin = findViewById(R.id.tv_label_login);
        mTvLabelRegister = findViewById(R.id.tv_label_register);
        mClLogin = findViewById(R.id.cl_login);
        mClRegister = findViewById(R.id.cl_register);

        mTvLabelLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录
                mClLogin.setVisibility(View.VISIBLE);
                mClRegister.setVisibility(View.GONE);
            }
        });
        mTvLabelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册
                mClLogin.setVisibility(View.GONE);
                mClRegister.setVisibility(View.VISIBLE);
            }
        });

        mTvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录
                startActivity(new Intent(getContext(), MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_login;
    }
}
