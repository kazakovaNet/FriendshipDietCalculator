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
    
    public void addDummyProducts() {
        Product p1 = new Product();
        p1.setTitle("Цыпленое, белое мясо без кожи");
        p1.setProteins(23.2);
        p1.setFats(1.7);
        p1.setCarbohydrates(0);
        p1.setCalories(114);
        
        Product p2 = new Product();
        p2.setTitle("Капуста белокочанная");
        p2.setProteins(1.8);
        p2.setFats(0.1);
        p2.setCarbohydrates(4.7);
        p2.setCalories(28);
        p2.setGlycemicIndex(15);
        
        Product p3 = new Product();
        p3.setTitle("Укроп");
        p3.setProteins(2.5);
        p3.setFats(0.5);
        p3.setCarbohydrates(6.3);
        p3.setCalories(40);
        
        Product p4 = new Product();
        p4.setTitle("Голубика");
        p4.setProteins(1);
        p4.setFats(0.5);
        p4.setCarbohydrates(6.6);
        p4.setCalories(39);
        
        Product p5 = new Product();
        p5.setTitle("Кальмары (мясо)");
        p5.setProteins(18);
        p5.setFats(2.2);
        p5.setCarbohydrates(2);
        p5.setCalories(100);
        
        App.getAppDatabase().productDao().truncate();
        App.getAppDatabase().productDao().insertAll(p1, p2, p3, p4, p5);
    }
    
    public List<Product> getAllProducts() {
        return App.getAppDatabase().productDao().getAll();
    }
}
