package ru.kazakova_net.friendshipdietcalculator.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeProduct;
import ru.kazakova_net.friendshipdietcalculator.model.Product;
import ru.kazakova_net.friendshipdietcalculator.model.dao.FoodIntakeDao;
import ru.kazakova_net.friendshipdietcalculator.model.dao.FoodIntakeProductDao;
import ru.kazakova_net.friendshipdietcalculator.model.dao.ProductDao;

/**
 * Created by nkazakova on 14/02/2019.
 */
@Database(entities = {Product.class, FoodIntake.class, FoodIntakeProduct.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    
    public abstract FoodIntakeDao foodIntakeDao();
    
    public abstract FoodIntakeProductDao foodIntakeProductDao();
}
