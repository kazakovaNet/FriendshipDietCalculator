package ru.kazakova_net.friendshipdietcalculator.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by nkazakova on 18/02/2019.
 */
@Dao
public interface FoodIntakeProductDao {
    @Query("SELECT * FROM food_intake_product")
    List<FoodIntakeProduct> getAll();
    
    @Query("SELECT * FROM food_intake_product WHERE food_intake_id = :foodIntakeId AND product_id = :productId")
    List<FoodIntakeProduct> getByProductId(long foodIntakeId, long productId);
    
    @Insert
    void insertAll(FoodIntakeProduct... foodIntakeProducts);
    
    @Update
    void updateAll(FoodIntakeProduct... foodIntakeProducts);
    
    @Delete
    void delete(FoodIntakeProduct foodIntakeProduct);
}
