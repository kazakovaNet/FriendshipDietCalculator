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
    
    public boolean isSaved(long foodIntakeId, long productId){
        return App.getAppDatabase().foodIntakeProductDao().getByProductId(foodIntakeId, productId).size() > 0;
    }
    
    public void update(FoodIntakeProduct foodIntakeProduct) {
        App.getAppDatabase().foodIntakeProductDao().updateAll(foodIntakeProduct);
    }
}
