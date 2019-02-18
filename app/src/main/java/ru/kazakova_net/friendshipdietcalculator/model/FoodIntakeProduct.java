package ru.kazakova_net.friendshipdietcalculator.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nkazakova on 18/02/2019.
 */
@Entity(tableName = "food_intake_product")
public class FoodIntakeProduct {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "food_intake_id")
    private int foodIntakeId;
    @ColumnInfo(name = "product_id")
    private int productId;
    private double weight;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getFoodIntakeId() {
        return foodIntakeId;
    }
    
    public void setFoodIntakeId(int foodIntakeId) {
        this.foodIntakeId = foodIntakeId;
    }
    
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
}
