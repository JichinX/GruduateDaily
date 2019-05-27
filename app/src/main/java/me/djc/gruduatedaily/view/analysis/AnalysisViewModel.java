package me.djc.gruduatedaily.view.analysis;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import me.djc.base.executors.AppExecutors;
import me.djc.base.viewmodel.BaseViewModel;
import me.djc.common.util.CalenderUtil;
import me.djc.gruduatedaily.App;
import me.djc.gruduatedaily.room.dao.LabelDao;
import me.djc.gruduatedaily.room.dao.PlanDao;
import me.djc.gruduatedaily.room.entity.Label;
import me.djc.gruduatedaily.room.entity.Plan;
import me.xujichang.xbase.baseutils.strings.StringFormatUtil;

import java.util.List;

/**
 * @author xujichang
 * @date 2019/05/15
 **/
public class AnalysisViewModel extends BaseViewModel {
    private LabelDao mLabelDao;
    private PlanDao mPlanDao;
    private AppExecutors mExecutors;

    private LiveData<List<Plan>> mDayPlansLiveData;

    private LiveData<List<Plan>> mWeekPlansLiveData;

    private LiveData<List<Plan>> mMonthPlansLiveData;

    public AnalysisViewModel(@NonNull Application application) {
        super(application);
        mLabelDao = ((App) application).getDatabase().mLabelDao();
        mPlanDao = ((App) application).getDatabase().mPlanDao();
        mExecutors = new AppExecutors();
        mDayPlansLiveData = new MutableLiveData<>();
        mWeekPlansLiveData = new MutableLiveData<>();
        mMonthPlansLiveData = new MutableLiveData<>();
    }


    public LiveData<List<Label>> getLabels() {
        return mLabelDao.queryAllLabels();
    }

    public LiveData<List<Plan>> getDayPlansLiveData() {
        getPlansFromDb();
        return mDayPlansLiveData;
    }

    /**
     * 从数据库中取数据
     */
    private void getPlansFromDb() {
        long current = System.currentTimeMillis();
        String date = null;
        date = StringFormatUtil.formatTime(current, "yyyy-MM-dd");
        mDayPlansLiveData = mPlanDao.queryPlans(date);
    }


    public void addLabel(Label eLabel) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mLabelDao.addLabel(eLabel);
            }
        });
    }

    public void deleteLabel(Label eLabel) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mLabelDao.delete(eLabel);
            }
        });
    }

    public void updateLabel(Label eLabel) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mLabelDao.update(eLabel);
            }
        });
    }

    /**
     * 查询 处于可用状态的标签
     *
     * @return
     */
    public LiveData<List<Label>> getEnabledLabels() {
        return mLabelDao.queryEnabledLabels();
    }

    public LiveData<List<Plan>> getWeekPlans() {
        getWeekPlansFromDb();
        return mWeekPlansLiveData;
    }

    /**
     * 过去一周之内的计划
     */
    private void getWeekPlansFromDb() {
        long end = CalenderUtil.getDayStartMs();
        long start = end - CalenderUtil.DAYS(7);
        mWeekPlansLiveData = mPlanDao.queryPlans(start, end);
    }

    public LiveData<List<Plan>> getMonthPlans() {
        getMonthPlansFromDb();
        return mMonthPlansLiveData;
    }

    /**
     * 过去一个月之内
     */
    private void getMonthPlansFromDb() {
        long end = CalenderUtil.getDayStartMs();
        long start = end - CalenderUtil.DAYS(30);
        mMonthPlansLiveData = mPlanDao.queryPlans(start, end);
    }
}
