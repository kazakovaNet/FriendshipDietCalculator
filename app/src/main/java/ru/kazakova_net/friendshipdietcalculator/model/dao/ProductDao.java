package ru.kazakova_net.friendshipdietcalculator.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.kazakova_net.friendshipdietcalculator.model.Product;

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
