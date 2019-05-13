package ru.kazakova_net.friendshipdietcalculator.model.lab;

import java.util.List;

import ru.kazakova_net.friendshipdietcalculator.application.App;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;

/**
 * Created by nkazakova on 15/02/2019.
 */
public class FoodIntakeLab {
    
    private static FoodIntakeLab foodIntakeLab;
    
    private FoodIntakeLab() {
    }
    
    public static FoodIntakeLab get() {
        if (foodIntakeLab == null) {
            foodIntakeLab = new FoodIntakeLab();
        }
        
        return foodIntakeLab;
    }
    
    public long add(FoodIntake f) {
        long[] ids = App.getAppDatabase().foodIntakeDao().insertAll(f);
        return ids[0];
    }
    
    public boolean isSaved(long foodIntakeId) {
        return App.getAppDatabase().foodIntakeDao().getById(foodIntakeId) != null;
    }
    
    public void update(FoodIntake f) {
        App.getAppDatabase().foodIntakeDao().updateAll(f);
    }
    
    public List<FoodIntake> getAll() {
        return App.getAppDatabase().foodIntakeDao().getAll();
    }
    
    public List<FoodIntake> getByTimeRange(long timeMillisStart, long timeMillisEnd) {
        return App.getAppDatabase().foodIntakeDao().getByDateRange(timeMillisStart, timeMillisEnd);
    }
    
    public List<FoodIntake> getByTimeRangeAndType(long timeMillisStart, long timeMillisEnd, List<String> typeCondition) {
        return App.getAppDatabase().foodIntakeDao().getByDateRangeAndType(timeMillisStart, timeMillisEnd, typeCondition);
    }
    
    public void truncate() {
        App.getAppDatabase().foodIntakeDao().truncate();
    }
    
    public FoodIntake getById(long foodIntakeId) {
        return App.getAppDatabase().foodIntakeDao().getById(foodIntakeId);
    }
    
    public Long getNextRowId() {
        return App.getAppDatabase().foodIntakeDao().getLastInsertRowId() + 1;
    }
}
