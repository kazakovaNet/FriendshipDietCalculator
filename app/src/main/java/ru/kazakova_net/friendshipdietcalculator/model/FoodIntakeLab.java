package ru.kazakova_net.friendshipdietcalculator.model;

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
    
    public void addFoodIntake(FoodIntake f) {
        App.getAppDatabase().foodIntakeDao().insertAll(f);
    }
}
