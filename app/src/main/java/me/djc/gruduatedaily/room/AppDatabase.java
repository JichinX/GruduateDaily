package me.djc.gruduatedaily.room;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import me.djc.base.executors.AppExecutors;
import me.djc.common.util.CalenderUtil;
import me.djc.common.util.ColorUtils;
import me.djc.gruduatedaily.room.dao.*;
import me.djc.gruduatedaily.room.entity.*;
import me.xujichang.xbase.baseutils.strings.StringFormatUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Des:GruduateDaily - me.djc.base.room
 * APP 数据库
 *
 * @author xujichang
 * @date 2019-04-19 - 00:51
 * <p>
 * modify:
 */
@Database(entities = {Ding.class, Label.class, Order.class, Plan.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    public static final String DATABASE_NAME = "tbl-basic-db";

    public abstract UserDao mUserDao();

    public abstract DingDao mDingDao();

    public abstract LabelDao mLabelDao();

    public abstract OrderDao mOrderDao();

    public abstract PlanDao mPlanDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            // Add a delay to simulate a long-running operation
                            addDelay();
                            // Generate the data for pre-population
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            List<Plan> products = DataGenerator.generatePlans();
                            List<Label> vLabels = DataGenerator.generateLabels();
                            List<Ding> comments =
                                    DataGenerator.generateDings(products);

                            insertData(database, products, comments, vLabels);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                })
//                .addMigrations(MIGRATION_1_2)
                .build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertData(final AppDatabase database, final List<Plan> products,
                                   final List<Ding> comments, List<Label> eLabels) {
        database.runInTransaction(() -> {
            database.mPlanDao().insertAll(products);
            database.mDingDao().insertAll(comments);
            database.mLabelDao().insertAll(eLabels);
        });
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    //TODO  测试数据
    private static class DataGenerator {
        private static Random mRandom = new Random();

        public static List<Plan> generatePlans() {
            List<Plan> vPlans = new ArrayList<>();
            long current = System.currentTimeMillis();
            //默认添加60天内的数据
            for (int i = 0; i < 60; i++) {
                long time = current - CalenderUtil.DAY_MS * (i + 1);
                String date = StringFormatUtil.formatTime(time, "yyyy-MM-dd");
                vPlans.add(new Plan(time - CalenderUtil.HOUR_MS, time + CalenderUtil.HOUR_MS, mRandom.nextInt(6) + 1, "默认", date));
                vPlans.add(new Plan(time - CalenderUtil.HOUR_MS, time + CalenderUtil.HOUR_MS, mRandom.nextInt(6) + 1, "默认", date));
                vPlans.add(new Plan(time - CalenderUtil.HOUR_MS, time + CalenderUtil.HOUR_MS, mRandom.nextInt(6) + 1, "默认", date));
                vPlans.add(new Plan(time - CalenderUtil.HOUR_MS, time + CalenderUtil.HOUR_MS, mRandom.nextInt(6) + 1, "默认", date));
                vPlans.add(new Plan(time - CalenderUtil.HOUR_MS, time + CalenderUtil.HOUR_MS, mRandom.nextInt(6) + 1, "默认", date));
                vPlans.add(new Plan(time - CalenderUtil.HOUR_MS, time + CalenderUtil.HOUR_MS, mRandom.nextInt(6) + 1, "默认", date));
                vPlans.add(new Plan(time - CalenderUtil.HOUR_MS, time + CalenderUtil.HOUR_MS, mRandom.nextInt(6) + 1, "默认", date));
            }


            return vPlans;
        }

        public static List<Ding> generateDings(List<Plan> eProducts) {
            List<Ding> vDings = new ArrayList<>();


            return vDings;
        }

        public static List<Label> generateLabels() {
            List<Label> vLabels = new ArrayList<>();
            vLabels.add(new Label(1, ColorUtils.createRandomColor(), "数学", "", true));
            vLabels.add(new Label(2, ColorUtils.createRandomColor(), "化学", "", true));
            vLabels.add(new Label(3, ColorUtils.createRandomColor(), "物理", "", true));
            vLabels.add(new Label(4, ColorUtils.createRandomColor(), "线性代数", "", true));
            vLabels.add(new Label(5, ColorUtils.createRandomColor(), "英语", "", true));
            vLabels.add(new Label(6, ColorUtils.createRandomColor(), "图形学", "", true));
            return vLabels;
        }
    }
}
