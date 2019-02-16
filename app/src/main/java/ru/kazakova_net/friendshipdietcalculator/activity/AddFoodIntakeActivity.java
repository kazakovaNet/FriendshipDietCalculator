package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private Product product;
    
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
        
        addProductRow();
        
        updateDate();
        updateTime();
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
                break;
            
        }
    }
    
    private void addProductRow() {
        View productsRootView = LayoutInflater.from(this).inflate(R.layout.product_row, binding.addFoodIntakeProductsRootView, false);
        
        initProductRow(productsRootView);
        
        binding.addFoodIntakeProductsRootView
                .addView(productsRootView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    
    private void initProductRow(final View productRootView) {
        List<Product> allProducts = ProductLab.get().getAllProducts();
        ArrayAdapter<Product> productArrayAdapter = new ArrayAdapter<>(AddFoodIntakeActivity.this, android.R.layout.simple_list_item_1, allProducts);
        
        AutoCompleteTextView productNameTextView = productRootView.findViewById(R.id.product_row_name);
        productNameTextView.setAdapter(productArrayAdapter);
        productNameTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                product = (Product) adapterView.getItemAtPosition(i);
    
                displayCalculation(productRootView);
            }
        });
        
        TextView productCountTextView = productRootView.findViewById(R.id.product_row_count);
        productCountTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (textView.getText().toString().equals("")){
                    return true;
                }
                
                
                
                return true;
            }
        });
    }
    
    private void displayCalculation(View productsRootView) {
        TextView proteinsTextView = productsRootView.findViewById(R.id.product_row_proteins);
        TextView fatsTextView = productsRootView.findViewById(R.id.product_row_fats);
        TextView carbohydratesTextView = productsRootView.findViewById(R.id.product_row_carbohydrates);
        TextView caloriesTextView = productsRootView.findViewById(R.id.product_row_calories);
        TextView productCount = productsRootView.findViewById(R.id.product_row_count);
        
        String weightProduct = productCount.getText().toString();
        
        if (weightProduct.equals("")){
            return;
        }
        
        proteinsTextView.setText(calcElements(product.getProteins(), Double.parseDouble(weightProduct)));
        fatsTextView.setText(calcElements(product.getFats(), Double.parseDouble(weightProduct)));
        carbohydratesTextView.setText(calcElements(product.getCarbohydrates(), Double.parseDouble(weightProduct)));
        caloriesTextView.setText(calcElements(product.getCalories(), Double.parseDouble(weightProduct)));
    }
    
    private String calcElements(double absAmountElement, double weightProduct) {
        double result = weightProduct * absAmountElement / 100;
        
        return String.valueOf(result);
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
