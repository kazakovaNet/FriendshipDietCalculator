package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.databinding.AddFoodIntakeActivityBinding;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeLab;
import ru.kazakova_net.friendshipdietcalculator.model.Product;
import ru.kazakova_net.friendshipdietcalculator.model.ProductLab;
import ru.kazakova_net.friendshipdietcalculator.util.CommonUtil;

public class AddFoodIntakeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    
    private AddFoodIntakeActivityBinding binding;
    private FoodIntake foodIntake;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_intake);
        
        foodIntake = new FoodIntake();
        foodIntake.setTimeMillis(System.currentTimeMillis());
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_food_intake);
        binding.addFoodIntakeTypeSpinner.setOnItemSelectedListener(this);
        binding.addFoodIntakeSaveBtn.setOnClickListener(this);
        binding.addFoodIntakeAddProductRowBtn.setOnClickListener(this);
        
        updateDate();
        updateTime();
    
        initProductRow();
    }
    
    private void addProductRow() {
        View view = LayoutInflater.from(this).inflate(R.layout.product_row, null, false);
        binding.addFoodIntakeProductsRootView
                .addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    
    public static Intent getIntent(Context context) {
        return new Intent(context, AddFoodIntakeActivity.class);
    }
    
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.add_food_intake_type_spinner:
                String foodIntakeType = (String) adapterView.getItemAtPosition(i);
                foodIntake.setType(foodIntakeType);
                break;
        }
    }
    
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    
    }
    
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_food_intake_save_btn:
                FoodIntakeLab.get().addFoodIntake(foodIntake);
                break;
            case R.id.add_food_intake_add_product_row_btn:
                addProductRow();
                
                initProductRow();
                break;
            
        }
    }
    
    private void initProductRow() {
        int childCount = binding.addFoodIntakeProductsRootView.getChildCount();
        ConstraintLayout parent =
                (ConstraintLayout) binding.addFoodIntakeProductsRootView.getChildAt(childCount - 1);
        
        List<Product> allProducts = ProductLab.get().getAllProducts();
        ArrayAdapter<Product> productArrayAdapter = new ArrayAdapter<>(AddFoodIntakeActivity.this, android.R.layout.simple_list_item_1, allProducts);
        
        AutoCompleteTextView productNameTextView = parent.findViewById(R.id.product_row_name);
        productNameTextView.setAdapter(productArrayAdapter);
    }
    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        MotionEvent motionEvent = CommonUtil.dispatchTouchEvent(ev, getCurrentFocus(),
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
        
        return super.dispatchTouchEvent(motionEvent);
    }
    
    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy г", Locale.getDefault());
        
        binding.addFoodIntakeDateBtn.setText(dateFormat.format(foodIntake.getTimeMillis()));
    }
    
    private void updateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        
        binding.addFoodIntakeTimeBtn.setText(dateFormat.format(foodIntake.getTimeMillis()));
    }
}
