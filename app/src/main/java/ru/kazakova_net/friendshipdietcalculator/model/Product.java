package ru.kazakova_net.friendshipdietcalculator.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by nkazakova on 14/02/2019.
 */
@Entity(tableName = "products")
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private double proteins;
    private double fats;
    private double carbohydrates;
    private double calories;
    @ColumnInfo(name = "from_bs")
    private byte fromBS;
    
    @Ignore
    public Product(String title, double proteins, double fats, double carbohydrates, double calories, byte fromBS) {
        this.title = title;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
        this.fromBS = fromBS;
    }
    
    public Product() {
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public double getProteins() {
        return proteins;
    }
    
    public void setProteins(double proteins) {
        this.proteins = proteins;
    }
    
    public double getFats() {
        return fats;
    }
    
    public void setFats(double fats) {
        this.fats = fats;
    }
    
    public double getCarbohydrates() {
        return carbohydrates;
    }
    
    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
    
    public double getCalories() {
        return calories;
    }
    
    public void setCalories(double calories) {
        this.calories = calories;
    }
    
    public byte getFromBS() {
        return fromBS;
    }
    
    public void setFromBS(byte fromBS) {
        this.fromBS = fromBS;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
}
