package me.djc.base;

import android.app.Application;
import me.djc.base.executors.AppExecutors;

/**
 * Des:GruduateDaily - me.djc.base
 *
 * @author xujichang
 * @date 2019-04-15 - 20:47
 * <p>
 * modify:
 */
public class BaseApp extends Application {
    protected AppExecutors mExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mExecutors = new AppExecutors();
    }
}
