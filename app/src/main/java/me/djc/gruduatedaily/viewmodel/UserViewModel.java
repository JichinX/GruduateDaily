package me.djc.gruduatedaily.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import me.djc.base.executors.AppExecutors;
import me.djc.base.viewmodel.BaseViewModel;
import me.djc.gruduatedaily.App;
import me.djc.gruduatedaily.room.dao.UserDao;
import me.djc.gruduatedaily.room.entity.User;

/**
 * @author xujichang
 * @date 2019/05/16
 **/
public class UserViewModel extends BaseViewModel {

    private UserDao mUserDao;
    private AppExecutors mExecutors;
    private MutableLiveData<Long> mRegisterData;
    private MutableLiveData<Boolean> mLoginLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mRegisterData = new MutableLiveData<>();
        mLoginLiveData = new MutableLiveData<>();
        mUserDao = ((App) application).getDatabase().mUserDao();
        mExecutors = new AppExecutors();
    }

    /**
     * 注册
     *
     * @param eUser
     */
    public void registerUser(User eUser) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mRegisterData.postValue(mUserDao.addUser(eUser));
            }
        });
    }

    /**
     * 登录
     *
     * @param eAccount
     * @param ePwd
     */
    public void login(String eAccount, String ePwd) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mLoginLiveData.postValue(null != mUserDao.getUser(eAccount, ePwd));
            }
        });
    }

    public MutableLiveData<Long> subscribeRegister() {
        return mRegisterData;
    }

    public MutableLiveData<Boolean> subscribeLogin() {
        return mLoginLiveData;
    }
}
