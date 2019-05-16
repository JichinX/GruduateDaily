package me.djc.gruduatedaily.view.analysis;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import me.djc.base.executors.AppExecutors;
import me.djc.base.viewmodel.BaseViewModel;
import me.djc.gruduatedaily.App;
import me.djc.gruduatedaily.room.dao.LabelDao;
import me.djc.gruduatedaily.room.entity.Label;

import java.util.List;

/**
 * @author xujichang
 * @date 2019/05/15
 **/
public class AnalysisViewModel extends BaseViewModel {
    private LabelDao mLabelDao;
    private AppExecutors mExecutors;

    public AnalysisViewModel(@NonNull Application application) {
        super(application);
        mLabelDao = ((App) application).getDatabase().mLabelDao();
        mExecutors = new AppExecutors();
    }


    public LiveData<List<Label>> getLabels() {
        return mLabelDao.queryAllLabels();
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
}
