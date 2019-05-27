package me.djc.gruduatedaily.view.plan;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import me.djc.base.executors.AppExecutors;
import me.djc.common.util.CalenderUtil;
import me.djc.gruduatedaily.App;
import me.djc.gruduatedaily.base.AppConst;
import me.djc.gruduatedaily.room.dao.DingDao;
import me.djc.gruduatedaily.room.dao.LabelDao;
import me.djc.gruduatedaily.room.dao.OrderDao;
import me.djc.gruduatedaily.room.dao.PlanDao;
import me.djc.gruduatedaily.room.entity.Ding;
import me.djc.gruduatedaily.room.entity.Label;
import me.djc.gruduatedaily.room.entity.Order;
import me.djc.gruduatedaily.room.entity.Plan;
import me.xujichang.xbase.baseutils.strings.StringFormatUtil;

import java.util.List;

/**
 * 计划
 * 包括清单
 *
 * @author xujichang
 * @date 2019/05/15
 **/
public class PlanViewModel extends AndroidViewModel {

    private LiveData<List<Plan>> mPlansLiveData;
    private PlanDao mPlanDao;
    private OrderDao mOrderDao;
    private AppExecutors mExecutors;
    private LabelDao mLabelDao;
    private DingDao mDingDao;

    public PlanViewModel(@NonNull Application application) {
        super(application);
        mExecutors = new AppExecutors();
        mPlansLiveData = new MutableLiveData<>();
        mPlanDao = ((App) application).getDatabase().mPlanDao();
        mOrderDao = ((App) application).getDatabase().mOrderDao();
        mLabelDao = ((App) application).getDatabase().mLabelDao();
        mDingDao = ((App) application).getDatabase().mDingDao();
    }

    public LiveData<List<Plan>> getPlans(int eDayType) {
        getPlansFromDb(eDayType);
        return mPlansLiveData;
    }

    /**
     * 从数据库中取数据
     *
     * @param eDayType
     */
    private void getPlansFromDb(int eDayType) {
        long current = System.currentTimeMillis();
        String date = null;
        long nextDay = 0;
        if (eDayType == PlanListFragment.DAY_TYPE_NEXT) {
            nextDay = current + 24 * 60 * 60 * 1000;
        }
        date = StringFormatUtil.formatTime(current + nextDay, "yyyy-MM-dd");
        mPlansLiveData = mPlanDao.queryPlans(date);
    }


    public LiveData<List<Order>> getNoneOrders() {
        return mOrderDao.queryOrders(AppConst.OrderType.NONE);
    }

    public LiveData<List<Order>> getUrgentOrders() {
        return mOrderDao.queryOrders(AppConst.OrderType.URGENT);
    }

    public LiveData<List<Order>> getImportantOrders() {
        return mOrderDao.queryOrders(AppConst.OrderType.IMPORTANT);
    }

    public LiveData<List<Order>> getBothOrders() {
        return mOrderDao.queryOrders(AppConst.OrderType.IMPORTANT_URGENT);
    }

    public LiveData<List<Order>> getOrders(int eType) {
        switch (eType) {
            case AppConst.OrderType.IMPORTANT:
                return getImportantOrders();
            case AppConst.OrderType.IMPORTANT_URGENT:
                return getBothOrders();
            case AppConst.OrderType.URGENT:
                return getUrgentOrders();
            case AppConst.OrderType.NONE:
                return getNoneOrders();
            default:
                return null;
        }
    }

    public void addOrder(Order eOrder) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mOrderDao.addOrder(eOrder);
            }
        });
    }

    public void deletePlan(Plan ePlan) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mPlanDao.deletePlan(ePlan);
            }
        });
    }

    public LiveData<List<Label>> getLabels() {
        return mLabelDao.queryAllLabels();
    }

    /**
     * 添加计划
     *
     * @param ePlan
     * @param eDayType
     */
    public void addPlans(Plan ePlan, int eDayType) {
        long current = System.currentTimeMillis();
        long nextDay = 0;
        if (eDayType == PlanListFragment.DAY_TYPE_NEXT) {
            nextDay = current + 24 * 60 * 60 * 1000;
        }
        String date = StringFormatUtil.formatTime(current + nextDay, "yyyy-MM-dd");
        ePlan.setDate(date);

        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mPlanDao.addPlan(ePlan);
            }
        });
    }

    public void addDing(Ding eDing) {
        long current = System.currentTimeMillis();
        eDing.setDateTime(current);
        eDing.setDayMs(CalenderUtil.getMidDayMs(current));
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDingDao.addDing(eDing);
            }
        });
    }

    public LiveData<Ding> getDingInfo() {
        long current = System.currentTimeMillis();
        //转换为天数的MS
        long moreMs = current % (24 * 60 * 60 * 1000);
        long startMs = current - moreMs;
        long endMs = startMs + 24 * 60 * 60 * 1000;
        return mDingDao.queryDingInfo(startMs, endMs);
    }

    public LiveData<List<Plan>> getPlansWithDate(String eDate) {
        return mPlanDao.queryPlans(eDate);
    }
}
