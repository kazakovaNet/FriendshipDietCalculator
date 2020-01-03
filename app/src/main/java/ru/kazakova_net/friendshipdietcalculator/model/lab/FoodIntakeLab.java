package ru.kazakova_net.friendshipdietcalculator.model.lab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ru.kazakova_net.friendshipdietcalculator.application.App;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;
import ru.kazakova_net.friendshipdietcalculator.util.TimeUtil;

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
    
    public FoodIntake getSaved(FoodIntake foodIntake) {
        ArrayList<String> typeConditions = new ArrayList<>();
        typeConditions.add(foodIntake.getType());
        
        Map<String, Long> rangeDay = TimeUtil.getRangeDay(new Date(foodIntake.getTimeMillis()));
        
        List<FoodIntake> list = FoodIntakeLab.get().getByTimeRangeAndType(rangeDay.get(TimeUtil.TIME_START_KEY), rangeDay.get(TimeUtil.TIME_END_KEY), typeConditions);
        
        return list.size() > 0 ? list.get(0) : null;
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
}
