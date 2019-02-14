package ru.kazakova_net.friendshipdietcalculator.application;

import android.app.Application;
import android.arch.persistence.room.Room;

import ru.kazakova_net.friendshipdietcalculator.model.AppDatabase;

/**
 * Created by nkazakova on 14/02/2019.
 */
public class App extends Application {
    private static AppDatabase appDatabase;
    
    @Override
    public void onCreate() {
        super.onCreate();
    
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "fdc-db")
                .allowMainThreadQueries()
                .build();
    }
    
    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
