package me.djc.gruduatedaily.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import me.djc.gruduatedaily.room.entity.Label;

import java.util.List;

@Dao
public interface LabelDao {
    @Query("SELECT * FROM tbl_label")
    LiveData<List<Label>> queryAllLabels();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addLabel(Label eLabel);

    @Delete
    void delete(Label eLabel);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Label eLabel);

    /**
     * 查询可用状态下的 标签
     *
     * @return
     */
    @Query("SELECT * FROM tbl_label WHERE enable = 1")
    LiveData<List<Label>> queryEnabledLabels();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Label> eLabels);
}
