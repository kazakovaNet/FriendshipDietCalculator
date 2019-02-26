package ru.kazakova_net.friendshipdietcalculator.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeProduct;

/**
 * Created by nkazakova on 18/02/2019.
 */
@Dao
public interface FoodIntakeProductDao {
    @Query("SELECT * FROM food_intake_product")
    List<FoodIntakeProduct> getAll();
    
    @Query("SELECT * FROM food_intake_product WHERE food_intake_id = :foodIntakeId AND product_id = :productId")
    List<FoodIntakeProduct> getByFoodIntakeIdAndProductId(long foodIntakeId, long productId);
    
    @Query("SELECT * FROM food_intake_product WHERE food_intake_id = :foodIntakeId")
    List<FoodIntakeProduct> getByFoodIntakeId(long foodIntakeId);
    
    @Insert
    void insertAll(FoodIntakeProduct... foodIntakeProducts);
    
    @Update
    void updateAll(FoodIntakeProduct... foodIntakeProducts);
    
    @Delete
    void delete(FoodIntakeProduct foodIntakeProduct);
    
    @Query("DELETE FROM food_intake_product")
    void truncate();
}
