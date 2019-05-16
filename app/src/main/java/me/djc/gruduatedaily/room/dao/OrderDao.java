package me.djc.gruduatedaily.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import me.djc.gruduatedaily.room.entity.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM tbl_order ")
    LiveData<List<Order>> queryAllOrders();

    @Query("SELECT * FROM tbl_order WHERE type == :type")
    LiveData<List<Order>> queryOrders(int type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addOrder(Order eOrder);
}
