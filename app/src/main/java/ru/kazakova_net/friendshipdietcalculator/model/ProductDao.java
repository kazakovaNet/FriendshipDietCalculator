package ru.kazakova_net.friendshipdietcalculator.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by nkazakova on 14/02/2019.
 */
@Dao
public interface ProductDao {
    @Query("SELECT * FROM products ORDER BY title")
    List<Product> getAll();
    
    @Query("SELECT * FROM products WHERE id = :productId")
    Product getById(long productId);
    
    @Insert
    void insertAll(Product... products);
    
    @Update
    void updateAll(Product... products);
    
    @Delete
    void delete(Product product);
    
    @Query("DELETE FROM products")
    void truncate();
}
