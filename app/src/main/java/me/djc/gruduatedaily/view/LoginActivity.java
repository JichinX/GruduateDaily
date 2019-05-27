package me.djc.gruduatedaily.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import me.djc.base.activity.BaseActivity;
import me.djc.common.util.PrefUtil;
import me.djc.gruduatedaily.R;
import me.djc.gruduatedaily.room.entity.User;
import me.djc.gruduatedaily.viewmodel.UserViewModel;
import org.w3c.dom.Text;

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
    private UserViewModel mViewModel;
    private EditText mEtRegisterName;
    private EditText mEtRegisterPwd;
    private EditText mEtRegisterPhone;

    private String tempAccount;
    private String tempPwd;

    @Override
    protected void onIntentData(Intent intent) {

    }

    @Override
    protected void onDataInit() {
        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.subscribeRegister().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long eLong) {
                if (eLong > 0L) {
                    //注册成功
                    showToast("注册成功");
                    mTvLabelLogin.performLongClick();
                } else {
                    showToast("注册失败");
                }
            }
        });
        mViewModel.subscribeLogin().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean eBoolean) {
                //登录
                if (eBoolean) {
                    PrefUtil.saveLoginInfo(getContext(), tempAccount, tempPwd);
                    //登录成功
                    startActivity(new Intent(getContext(), MainActivity.class));
                    finish();
                } else {
                    showToast("登录失败");
                }
            }
        });
        tempAccount = PrefUtil.getSavedAccount(this);
        tempPwd = PrefUtil.getSavedPwd(this);
        if (!TextUtils.isEmpty(tempAccount) && !TextUtils.isEmpty(tempPwd)) {
            tryLogin(tempAccount, tempPwd);
        }
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
        mEtRegisterName = findViewById(R.id.et_register_name);
        mEtRegisterPwd = findViewById(R.id.et_register_pwd);
        mEtRegisterPhone = findViewById(R.id.et_register_phone);

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
                String account = mEtName.getText().toString();
                String pwd = mEtPwd.getText().toString();
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
                    return;
                }
                tryLogin(account, pwd);

            }
        });
        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册
                User vUser = collectUserInfo();
                if (null != vUser) {
                    tryRegister(vUser);
                }
            }
        });
    }

    /**
     * 注册
     *
     * @param eUser
     */
    private void tryRegister(User eUser) {
        mViewModel.registerUser(eUser);
    }

    private void tryLogin(String account, String pwd) {
        tempAccount = account;
        tempPwd = pwd;
        mViewModel.login(account, pwd);

    }

    /**
     * s搜集用户信息
     *
     * @return
     */
    private User collectUserInfo() {
        String name = mEtRegisterName.getText().toString();
        String pwd = mEtRegisterPwd.getText().toString();
        String phone = mEtRegisterPhone.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)) {
            showToast("关键信息不能为空");
            return null;
        }
        User vUser = new User();
        vUser.setName(name);
        vUser.setPhone(phone);
        vUser.setPwd(pwd);
        return vUser;
    }


    @Override
    protected int getContentLayoutRes() {
        return R.layout.activity_login;
    }
}
