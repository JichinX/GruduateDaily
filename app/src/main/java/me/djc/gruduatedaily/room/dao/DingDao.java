package me.djc.gruduatedaily.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import me.djc.gruduatedaily.room.entity.Ding;

import java.util.List;

@Dao
public interface DingDao {
    /**
     * 查询数据库中 打卡时间 在某一天的时间范围内
     *
     * @param eStartMs
     * @param eRealMs
     * @return
     */
    @Query("SELECT * FROM tbl_ding WHERE dayMs >= :eStartMs AND dayMs <= :eRealMs")
    Ding selectDingInfo(long eStartMs, long eRealMs);

    /**
     * 添加打卡信息
     *
     * @param eDing
     * @return
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addDing(Ding eDing);

    /**
     * 查询打卡信息
     *
     * @param eStartMs
     * @param eEndMs
     * @return
     */
    @Query("SELECT * FROM tbl_ding WHERE dayMs >= :eStartMs AND dayMs <= :eEndMs")
    LiveData<Ding> queryDingInfo(long eStartMs, long eEndMs);

    /**
     * 查询月份内的打卡信息
     *
     * @param eStartMs
     * @param eEndMs
     * @return
     */
    @Query("SELECT * FROM tbl_ding WHERE dayMs >= :eStartMs AND dayMs <= :eEndMs ")
    List<Ding> queryMonthDings(long eStartMs, long eEndMs);
}
