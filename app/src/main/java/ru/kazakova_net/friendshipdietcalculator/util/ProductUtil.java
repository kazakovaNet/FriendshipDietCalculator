package ru.kazakova_net.friendshipdietcalculator.util;

import java.util.ArrayList;
import java.util.Map;

import ru.kazakova_net.friendshipdietcalculator.model.Product;

import static ru.kazakova_net.friendshipdietcalculator.util.CommonUtil.formatShortDouble;

/**
 * Created by nkazakova on 2019-05-13.
 */
public class ProductUtil {
    public static ArrayList<String> getProductsList(Map<Product, Double> productsForFoodIntake) {
        ArrayList<String> productsList = new ArrayList<>();
        
        for (Map.Entry<Product, Double> entry : productsForFoodIntake.entrySet()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    // Льняное масло - 16 гр
                    .append(entry.getKey().getTitle())
                    .append(" - ")
                    .append(formatShortDouble(entry.getValue()))
                    .append(" гр.");
            productsList.add(stringBuilder.toString());
        }
        
        return productsList;
    }
}
