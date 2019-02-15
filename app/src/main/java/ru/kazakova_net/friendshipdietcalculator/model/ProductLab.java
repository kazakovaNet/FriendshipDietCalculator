package ru.kazakova_net.friendshipdietcalculator.model;

import java.util.List;

import ru.kazakova_net.friendshipdietcalculator.application.App;

/**
 * Created by nkazakova on 14/02/2019.
 */
public class ProductLab {
    
    private static ProductLab productLab;
    
    public static ProductLab get() {
        if (productLab == null) {
            productLab = new ProductLab();
        }
        
        return productLab;
    }
    
    private ProductLab() {
    }
    
    public void addProduct(Product p) {
        App.getAppDatabase().productDao().insertAll(p);
    }
    
    public List<Product> getAllProducts(){
        return App.getAppDatabase().productDao().getAll();
    }
}
