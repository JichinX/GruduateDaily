package me.djc.gruduatedaily.view.ding;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import me.djc.base.executors.AppExecutors;
import me.djc.base.viewmodel.BaseViewModel;
import me.djc.common.util.CalenderUtil;
import me.djc.gruduatedaily.App;
import me.djc.gruduatedaily.bean.DayWord;
import me.djc.gruduatedaily.room.dao.DingDao;
import me.djc.gruduatedaily.room.entity.Ding;

import java.util.Date;
import java.util.List;

/**
 * @author xujichang
 * @date 2019/05/16
 **/
public class DingViewModel extends BaseViewModel {

    private MutableLiveData<Ding> mDingMutableLiveData;
    private MutableLiveData<DayWord> mWordMutableLiveData;
    private MutableLiveData<List<Ding>> mDingsLiveData;
    private DingDao mDingDao;
    private AppExecutors mExecutors;

    public DingViewModel(@NonNull Application application) {
        super(application);
        mDingMutableLiveData = new MutableLiveData<>();
        mWordMutableLiveData = new MutableLiveData<>();
        mDingsLiveData = new MutableLiveData<>();
        mDingDao = ((App) application).getDatabase().mDingDao();
        mExecutors = new AppExecutors();
    }


    public MutableLiveData<Ding> subscribeDayDingInfo() {
        return mDingMutableLiveData;
    }

    public MutableLiveData<DayWord> subscribeDayWord() {
        return mWordMutableLiveData;
    }

    public MutableLiveData<List<Ding>> subscribeDings() {
        return mDingsLiveData;
    }

    /**
     * 选择具体某一天的打卡记录
     *
     * @param eMs
     */
    public void setSelectedDay(long eMs) {
        //转换为天数的MS
        long moreMs = eMs % (24 * 60 * 60 * 1000);
        long startMs = eMs - moreMs;
        long endMs = startMs + 24 * 60 * 60 * 1000;
        //数据库查询对应天数的打卡信息
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDingMutableLiveData.postValue(mDingDao.selectDingInfo(startMs, endMs));
            }
        });
    }

    public void updateDayWord(long eMs) {

    }

    /**
     * 获取月份内的打卡信息
     *
     * @param eMs
     */
    public void updateMonthDings(long eMs) {
        long startMs = CalenderUtil.getTimeOfMonthStart(new Date(eMs));
        long endMs = CalenderUtil.getTimeOfMonthEnd(new Date(eMs));
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDingsLiveData.postValue(mDingDao.queryMontsDings(startMs, endMs));
            }
        });
    }
}
