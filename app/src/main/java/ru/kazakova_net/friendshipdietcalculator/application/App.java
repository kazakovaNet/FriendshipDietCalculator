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
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `food_intake` (`id` INTEGER PRIMARY KEY not null, "
                    + "`type` TEXT, `time_millis` INTEGER not null)");
        }
    };
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `food_intake_product` (`id` INTEGER PRIMARY KEY not null, "
                    + "`food_intake_id` INTEGER not null, `product_id` INTEGER not null, "
                    + "`weight` REAL not null)");
        }
    };
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE `products` ADD `glycemic_idx` INTEGER NOT NULL DEFAULT 0");
        }
    };
    private static AppDatabase appDatabase;
    
    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "fdc-db")
                .allowMainThreadQueries()
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                .build();
    }
}
