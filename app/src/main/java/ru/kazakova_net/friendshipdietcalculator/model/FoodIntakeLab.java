package ru.kazakova_net.friendshipdietcalculator.model;

import java.util.List;

import ru.kazakova_net.friendshipdietcalculator.application.App;

/**
 * Created by nkazakova on 15/02/2019.
 */
public class FoodIntakeLab {
    
    private static FoodIntakeLab foodIntakeLab;
    
    public static FoodIntakeLab get() {
        if (foodIntakeLab == null) {
            foodIntakeLab = new FoodIntakeLab();
        }
        
        return foodIntakeLab;
    }
    
    private FoodIntakeLab() {
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
    
    public List<FoodIntake> getByTimeRangeAndType(long timeMillisStart, long timeMillisEnd, List<String> typeCondition) {
        return App.getAppDatabase().foodIntakeDao().getByDate(timeMillisStart, timeMillisEnd, typeCondition);
    }
}
