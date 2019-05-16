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
    @Query("SELECT * FROM tbl_ding WHERE dateTime >= :eStartMs AND dateTime <= :eRealMs")
    Ding selectDingInfo(long eStartMs, long eRealMs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addDing(Ding eDing);

    @Query("SELECT * FROM tbl_ding WHERE dateTime >= :eStartMs AND dateTime <= :eEndMs")
    LiveData<Ding> queryDingInfo(long eStartMs, long eEndMs);
    @Query("SELECT * FROM tbl_ding WHERE dateTime >= :eStartMs AND dateTime <= :eEndMs ")
    List<Ding> queryMontsDings(long eStartMs, long eEndMs);
}
