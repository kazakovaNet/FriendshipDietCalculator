package ru.kazakova_net.friendshipdietcalculator.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by nkazakova on 14/02/2019.
 */
@Database(entities = {Product.class, FoodIntake.class, FoodIntakeProduct.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    
    public abstract FoodIntakeDao foodIntakeDao();
    
    public abstract FoodIntakeProductDao foodIntakeProductDao();
}
