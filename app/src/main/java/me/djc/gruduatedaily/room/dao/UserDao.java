package me.djc.gruduatedaily.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import me.djc.gruduatedaily.room.entity.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM tbl_user WHERE  phone==:eAccount AND pwd=:ePwd")
    User getUser(String eAccount, String ePwd);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long addUser(User eUser);
}
