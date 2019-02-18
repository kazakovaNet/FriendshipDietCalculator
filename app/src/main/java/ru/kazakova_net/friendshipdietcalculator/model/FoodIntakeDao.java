package ru.kazakova_net.friendshipdietcalculator.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.Map;

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
    
    @Query("SELECT * FROM food_intake WHERE id = :foodIntakeId")
    FoodIntake getById(long foodIntakeId);
}
