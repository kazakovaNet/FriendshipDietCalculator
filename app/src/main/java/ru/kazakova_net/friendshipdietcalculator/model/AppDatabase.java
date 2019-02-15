package ru.kazakova_net.friendshipdietcalculator.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by nkazakova on 14/02/2019.
 */
@Database(entities = {Product.class, FoodIntake.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductDao productDao();
    
    public abstract FoodIntakeDao foodIntakeDao();
}
