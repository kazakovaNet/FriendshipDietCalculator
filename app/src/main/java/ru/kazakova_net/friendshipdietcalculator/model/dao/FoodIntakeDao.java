package ru.kazakova_net.friendshipdietcalculator.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;

/**
 * Created by nkazakova on 15/02/2019.
 */
@Dao
public interface FoodIntakeDao {
    @Query("SELECT * FROM food_intake")
    List<FoodIntake> getAll();
    
    @Insert
    long[] insertAll(FoodIntake... foodIntakes);
    
    @Update
    void updateAll(FoodIntake... foodIntakes);
    
    @Delete
    void delete(FoodIntake foodIntake);
    
    @Query("DELETE FROM food_intake")
    void truncate();
    
    @Query("SELECT * FROM food_intake WHERE id = :foodIntakeId")
    FoodIntake getById(long foodIntakeId);
    
    @Query("SELECT * FROM food_intake WHERE (time_millis BETWEEN :timeMillisStart AND :timeMillisEnd) AND type IN (:typeCondition)")
    List<FoodIntake> getByDateRangeAndType(long timeMillisStart, long timeMillisEnd, List<String> typeCondition);
    
    @Query("SELECT * FROM food_intake WHERE time_millis BETWEEN :timeMillisStart AND :timeMillisEnd")
    List<FoodIntake> getByDateRange(long timeMillisStart, long timeMillisEnd);
}
