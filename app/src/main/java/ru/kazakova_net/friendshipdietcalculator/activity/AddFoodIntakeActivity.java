package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.databinding.AddFoodIntakeActivityBinding;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeLab;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeProduct;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeProductLab;
import ru.kazakova_net.friendshipdietcalculator.model.Product;
import ru.kazakova_net.friendshipdietcalculator.model.ProductLab;
import ru.kazakova_net.friendshipdietcalculator.util.CommonUtil;

public class AddFoodIntakeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    
    private AddFoodIntakeActivityBinding binding;
    private FoodIntake foodIntake;
    private Product product;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_intake);
        
        initFoodIntake();
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_food_intake);
        binding.addFoodIntakeTypeSpinner.setOnItemSelectedListener(this);
        binding.addFoodIntakeAddProductRowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductRow();
            }
        });
        
        addProductRow();
        
        updateDate();
        updateTime();
    }
    
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.add_food_intake_type_spinner:
                String foodIntakeType = (String) adapterView.getItemAtPosition(i);
                foodIntake.setType(foodIntakeType);
                
                saveFoodIntake(foodIntake);
                break;
        }
    }
    
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    
    }
    
    private void initFoodIntake() {
        foodIntake = new FoodIntake();
        long foodIntakeId = saveFoodIntake(foodIntake);
        foodIntake.setId(foodIntakeId);
        foodIntake.setTimeMillis(System.currentTimeMillis());
    }
    
    private long saveFoodIntake(FoodIntake foodIntake) {
        if (FoodIntakeLab.get().isSaved(foodIntake.getId())) {
            FoodIntakeLab.get().update(foodIntake);
            
            return foodIntake.getId();
        } else {
            return FoodIntakeLab.get().add(foodIntake);
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
        
        final AutoCompleteTextView productNameTextView = productRootView.findViewById(R.id.product_row_name);
        final EditText productCountTextView = productRootView.findViewById(R.id.product_row_count);
        productNameTextView.setAdapter(productArrayAdapter);
        productNameTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                product = (Product) adapterView.getItemAtPosition(i);
                
                CommonUtil.hideKeyboard(productNameTextView, (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
                
                productCountTextView.requestFocus();
            }
        });
        
        Button productCalcButton = productRootView.findViewById(R.id.product_row_calc_btn);
        productCalcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView productCountTextView = productRootView.findViewById(R.id.product_row_count);
                if (productCountTextView.getText().toString().equals("")) {
                    return;
                }
                double weightProduct = Double.parseDouble(productCountTextView.getText().toString());
                
                displayCalculation(productRootView, weightProduct);
                saveFoodIntakeProduct(weightProduct);
            }
        });
    }
    
    private void saveFoodIntakeProduct(double weightProduct) {
        FoodIntakeProduct foodIntakeProduct = new FoodIntakeProduct();
        foodIntakeProduct.setFoodIntakeId(foodIntake.getId());
        foodIntakeProduct.setProductId(product.getId());
        foodIntakeProduct.setWeight(weightProduct);
        
        if (FoodIntakeProductLab.get().isSaved(foodIntake.getId(), product.getId())) {
            FoodIntakeProductLab.get().update(foodIntakeProduct);
        } else {
            FoodIntakeProductLab.get().addFoodIntakeProduct(foodIntakeProduct);
        }
    }
    
    private void displayCalculation(View productRootView, double weightProduct) {
        TextView proteinsTextView = productRootView.findViewById(R.id.product_row_proteins);
        TextView fatsTextView = productRootView.findViewById(R.id.product_row_fats);
        TextView carbohydratesTextView = productRootView.findViewById(R.id.product_row_carbohydrates);
        TextView caloriesTextView = productRootView.findViewById(R.id.product_row_calories);
        Spinner productCountUnitSpinner = productRootView.findViewById(R.id.product_row_count_unit);
        
        String productCountUnit = (String) productCountUnitSpinner.getSelectedItem();
        
        proteinsTextView.setText(calcElements(product.getProteins(), weightProduct, productCountUnit));
        fatsTextView.setText(calcElements(product.getFats(), weightProduct, productCountUnit));
        carbohydratesTextView.setText(calcElements(product.getCarbohydrates(), weightProduct, productCountUnit));
        caloriesTextView.setText(calcElements(product.getCalories(), weightProduct, productCountUnit));
    }
    
    private String calcElements(double absAmountElement, double weightProduct, String productCountUnit) {
        if (productCountUnit.equals(getString(R.string.product_count_milligram))) {
            weightProduct /= 1000;
        } else if (productCountUnit.equals(getString(R.string.product_count_pcs))) {
            weightProduct = 1;
        } else if (productCountUnit.equals(getString(R.string.product_count_teaspoon))) {
            weightProduct = 4;
        } else if (productCountUnit.equals(getString(R.string.product_count_table_spoon))) {
            weightProduct = 8;
        }
        
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
    
    public static Intent getIntent(Context context) {
        return new Intent(context, AddFoodIntakeActivity.class);
    }
}
