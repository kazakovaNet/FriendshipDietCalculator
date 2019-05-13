package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.adapter.AddFoodIntakeViewPagerAdapter;
import ru.kazakova_net.friendshipdietcalculator.databinding.AddFoodIntakeActivityBinding;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;
import ru.kazakova_net.friendshipdietcalculator.model.lab.FoodIntakeLab;
import ru.kazakova_net.friendshipdietcalculator.util.CommonUtil;

public class AddFoodIntakeActivity extends AppCompatActivity {
    
    public static Intent getIntent(Context context) {
        return new Intent(context, AddFoodIntakeActivity.class);
    }
    
    private static FoodIntake foodIntake;
    
    private static void setFoodIntake() {
        foodIntake = new FoodIntake();
        foodIntake.setTimeMillis(System.currentTimeMillis());
        foodIntake.setId(FoodIntakeLab.get().getNextRowId());
    }
    
    public static FoodIntake getFoodIntake(){
        return foodIntake;
    }
    
    private static void flashFoodIntake() {
        if (FoodIntakeLab.get().isSaved(foodIntake.getId())) {
            FoodIntakeLab.get().update(foodIntake);
    
        } else {
            FoodIntakeLab.get().add(foodIntake);
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
        switch (item.getItemId()) {
            case R.id.complete:
                flashFoodIntake();
                
                finish();
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
