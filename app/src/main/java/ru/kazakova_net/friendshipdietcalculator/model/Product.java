package ru.kazakova_net.friendshipdietcalculator.model;

/**
 * Created by nkazakova on 14/02/2019.
 */
public class Product {
    private String title;
    private double proteins;
    private double fats;
    private double carbohydrates;
    private double calories;
    
    public Product(String title, double proteins, double fats, double carbohydrates, double calories) {
        this.title = title;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
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
}
