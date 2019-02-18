package ru.kazakova_net.friendshipdietcalculator.model;

import ru.kazakova_net.friendshipdietcalculator.application.App;

/**
 * Created by nkazakova on 18/02/2019.
 */
public class FoodIntakeProductLab {
    
    private static FoodIntakeProductLab foodIntakeProductLab;
    
    public static FoodIntakeProductLab get() {
        if (foodIntakeProductLab == null) {
            foodIntakeProductLab = new FoodIntakeProductLab();
        }
        
        return foodIntakeProductLab;
    }
    
    private FoodIntakeProductLab() {
    }
    
    public void addFoodIntakeProduct(FoodIntakeProduct f) {
        App.getAppDatabase().foodIntakeProductDao().insertAll(f);
    }
}
