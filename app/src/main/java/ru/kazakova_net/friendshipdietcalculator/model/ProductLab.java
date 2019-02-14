package ru.kazakova_net.friendshipdietcalculator.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nkazakova on 14/02/2019.
 */
public class ProductLab {
    
    private static ProductLab productLab;
    
    private List<Product> productList;
    
    public static ProductLab get(Context context) {
        if (productLab == null) {
            productLab = new ProductLab();
        }
        
        return productLab;
    }
    
    private ProductLab() {
        productList = new ArrayList<>();
    }
    
    public void addProduct(Product p){
        productList.add(p);
    }
}
