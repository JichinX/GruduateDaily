package me.djc.gruduatedaily;

import me.djc.base.BaseApp;
import me.djc.gruduatedaily.repository.DataRepository;
import me.djc.gruduatedaily.room.AppDatabase;

/**
 * Des:GruduateDaily - me.djc.gruduatedaily
 *
 * @author xujichang
 * @date 2019-04-19 - 00:59
 * <p>
 * modify:
 */
public class App extends BaseApp {

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase());
    }
}
