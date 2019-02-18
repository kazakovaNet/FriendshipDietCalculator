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
    private long id;
    @ColumnInfo(name = "food_intake_id")
    private long foodIntakeId;
    @ColumnInfo(name = "product_id")
    private long productId;
    private double weight;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public long getFoodIntakeId() {
        return foodIntakeId;
    }
    
    public void setFoodIntakeId(long foodIntakeId) {
        this.foodIntakeId = foodIntakeId;
    }
    
    public long getProductId() {
        return productId;
    }
    
    public void setProductId(long productId) {
        this.productId = productId;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
}
