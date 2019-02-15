package ru.kazakova_net.friendshipdietcalculator.application;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;

import ru.kazakova_net.friendshipdietcalculator.model.AppDatabase;

/**
 * Created by nkazakova on 14/02/2019.
 */
public class App extends Application {
    private static AppDatabase appDatabase;
    
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `food_intake` (`id` INTEGER PRIMARY KEY not null, "
                    + "`type` TEXT, `time_millis` INTEGER not null)");
        }
    };
    
    @Override
    public void onCreate() {
        super.onCreate();
    
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "fdc-db")
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2)
                .build();
    }
    
    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
