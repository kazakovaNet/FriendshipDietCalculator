package ru.kazakova_net.friendshipdietcalculator.model.lab;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.kazakova_net.friendshipdietcalculator.application.App;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeProduct;
import ru.kazakova_net.friendshipdietcalculator.model.Product;

/**
 * Created by nkazakova on 18/02/2019.
 */
public class FoodIntakeProductLab {
    
    private static FoodIntakeProductLab foodIntakeProductLab;
    
    private FoodIntakeProductLab() {
    }
    
    public static FoodIntakeProductLab get() {
        if (foodIntakeProductLab == null) {
            foodIntakeProductLab = new FoodIntakeProductLab();
        }
        
        return foodIntakeProductLab;
    }
    
    public void addFoodIntakeProduct(FoodIntakeProduct f) {
        App.getAppDatabase().foodIntakeProductDao().insertAll(f);
    }
    
    public boolean isSaved(long foodIntakeId, long productId) {
        return App.getAppDatabase().foodIntakeProductDao().getByFoodIntakeIdAndProductId(foodIntakeId, productId).size() > 0;
    }
    
    public void update(FoodIntakeProduct foodIntakeProduct) {
        App.getAppDatabase().foodIntakeProductDao().updateAll(foodIntakeProduct);
    }
    
    public Map<Product, Double> getProductsForFoodIntake(long foodIntakeId) {
        List<FoodIntakeProduct> listByIdFoodIntake = App.getAppDatabase().foodIntakeProductDao().getByFoodIntakeId(foodIntakeId);
        Map<Product, Double> productsList = new HashMap<>();
        
        for (FoodIntakeProduct foodIntakeProduct : listByIdFoodIntake) {
            Product product = App.getAppDatabase().productDao().getById(foodIntakeProduct.getProductId());
            productsList.put(product, foodIntakeProduct.getWeight());
        }
        
        return productsList;
    }
    
    public void saveNew(FoodIntakeProduct foodIntakeProduct) {
        FoodIntakeProductLab.get().addFoodIntakeProduct(foodIntakeProduct);
    }
    
    public void truncate() {
        App.getAppDatabase().foodIntakeProductDao().truncate();
    }
}
