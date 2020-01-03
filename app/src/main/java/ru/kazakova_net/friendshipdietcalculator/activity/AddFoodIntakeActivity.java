package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.adapter.AddFoodIntakeViewPagerAdapter;
import ru.kazakova_net.friendshipdietcalculator.databinding.AddFoodIntakeActivityBinding;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeProduct;
import ru.kazakova_net.friendshipdietcalculator.model.lab.FoodIntakeLab;
import ru.kazakova_net.friendshipdietcalculator.model.lab.FoodIntakeProductLab;
import ru.kazakova_net.friendshipdietcalculator.util.CommonUtil;

public class AddFoodIntakeActivity extends AppCompatActivity {
    
    private static FoodIntake foodIntake;
    private static ArrayList<FoodIntakeProduct> foodIntakeProducts = new ArrayList<>();
    
    public static Intent getIntent(Context context) {
        return new Intent(context, AddFoodIntakeActivity.class);
    }
    
    private static void setFoodIntake() {
        foodIntake = new FoodIntake();
        foodIntake.setTimeMillis(System.currentTimeMillis());
    }
    
    public static void addFoodIntakeProduct(double weightProduct, long productId) {
        boolean productExists = false;
    
        // TODO: 2019-05-14 replace for binary search
        for (FoodIntakeProduct foodIntakeProduct : foodIntakeProducts) {
            if (foodIntakeProduct.getProductId() == productId) {
                foodIntakeProduct.setWeight(weightProduct);
                productExists = true;
                break;
            }
        }
        
        if (!productExists) {
            FoodIntakeProduct foodIntakeProduct = new FoodIntakeProduct();
            foodIntakeProduct.setProductId(productId);
            foodIntakeProduct.setWeight(weightProduct);
            foodIntakeProducts.add(foodIntakeProduct);
        }
    }
    
    public static FoodIntake getFoodIntake() {
        return foodIntake;
    }
    
    public static ArrayList<FoodIntakeProduct> getFoodIntakeProducts() {
        return foodIntakeProducts;
    }
    
    private static void flashFoodIntake() {
        long foodIntakeId = 0;
        FoodIntake savedIntake = FoodIntakeLab.get().getSaved(foodIntake);
        
        if (savedIntake != null) {
            foodIntakeId = savedIntake.getId();
            foodIntake.setId(foodIntakeId);
            
            FoodIntakeLab.get().update(foodIntake);
    
            // TODO: 2019-05-14 update intake product
            for (FoodIntakeProduct intakeProduct : foodIntakeProducts) {
                FoodIntakeProductLab.get().update(intakeProduct);
            }
        } else {
            foodIntakeId = FoodIntakeLab.get().add(foodIntake);
            
            for (FoodIntakeProduct intakeProduct : foodIntakeProducts) {
                intakeProduct.setFoodIntakeId(foodIntakeId);
                FoodIntakeProductLab.get().saveNew(intakeProduct);
            }
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_intake);
        
        setFoodIntake();
        
        AddFoodIntakeActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_add_food_intake);
        AddFoodIntakeViewPagerAdapter adapter = new AddFoodIntakeViewPagerAdapter(this, getSupportFragmentManager());
        
        binding.addFoodIntakeViewPager.setAdapter(adapter);
        binding.addFoodIntakeTabLayout.setupWithViewPager(binding.addFoodIntakeViewPager);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        MotionEvent motionEvent = CommonUtil.dispatchTouchEvent(ev, getCurrentFocus(),
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
        
        return super.dispatchTouchEvent(motionEvent);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_food_intake_menu, menu);
        
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.complete) {
            flashFoodIntake();
            
            finish();
        }
        
        return super.onOptionsItemSelected(item);
    }
}
