package me.djc.gruduatedaily.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import me.djc.gruduatedaily.room.entity.Plan;

import java.util.List;

@Dao
public interface PlanDao {

    @Query("SELECT * FROM tbl_plan WHERE date ==:date")
    LiveData<List<Plan>> queryPlans(String date);

    @Delete
    void deletePlan(Plan ePlan);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPlan(Plan ePlan);
}
