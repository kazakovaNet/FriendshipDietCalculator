package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.databinding.AddFoodIntakeActivityBinding;
import ru.kazakova_net.friendshipdietcalculator.fragment.DatePickerFragment;
import ru.kazakova_net.friendshipdietcalculator.fragment.TimePickerFragment;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeLab;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeProduct;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeProductLab;
import ru.kazakova_net.friendshipdietcalculator.model.Product;
import ru.kazakova_net.friendshipdietcalculator.model.ProductLab;
import ru.kazakova_net.friendshipdietcalculator.util.CommonUtil;

public class AddFoodIntakeActivity extends AppCompatActivity implements TimePickerFragment.TimeSelectListener, DatePickerFragment.DateSelectListener {
    
    private static final String DIALOG_TIME = "DialogTime";
    private static final String DIALOG_DATE = "DialogDate";
    private AddFoodIntakeActivityBinding binding;
    private FoodIntake foodIntake;
    private Product newProduct;
    
    private double sumProteins;
    private double sumFats;
    private double sumCarbohydrates;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_intake);
        
        initFoodIntake();
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_food_intake);
        binding.addFoodIntakeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String foodIntakeType = (String) adapterView.getItemAtPosition(i);
                foodIntake.setType(foodIntakeType);
                
                saveFoodIntake(foodIntake);
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            
            }
        });
        binding.addFoodIntakeDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(new Date(foodIntake.getTimeMillis()));
                dialog.show(manager, DIALOG_DATE);
            }
        });
        binding.addFoodIntakeTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(new Date(foodIntake.getTimeMillis()));
                dialog.show(manager, DIALOG_TIME);
            }
        });
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
    
    private void initFoodIntake() {
        foodIntake = new FoodIntake();
        long foodIntakeId = saveFoodIntake(foodIntake);
        foodIntake.setId(foodIntakeId);
        foodIntake.setTimeMillis(System.currentTimeMillis());
    }
    
    // FIXME: 25/02/2019
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
                newProduct = (Product) adapterView.getItemAtPosition(i);
                
                productCountTextView.requestFocus();
            }
        });
        productNameTextView.requestFocus();
        
        productCountTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                CommonUtil.hideKeyboard(productNameTextView, (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
                
                return true;
            }
        });
        
        final Button productCalcButton = productRootView.findViewById(R.id.product_row_calc_btn);
        productCalcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoCompleteTextView productNameTextView = productRootView.findViewById(R.id.product_row_name);
                TextView productCountTextView = productRootView.findViewById(R.id.product_row_count);
                
                if (productNameTextView.getText().toString().equals("") || productCountTextView.getText().toString().equals("")) {
                    return;
                }
                
                Spinner productCountUnitSpinner = productRootView.findViewById(R.id.product_row_count_unit);
                String productCountUnit = (String) productCountUnitSpinner.getSelectedItem();
                
                double weightProduct = Double.parseDouble(productCountTextView.getText().toString());
                
                displayCalculation(productRootView, getWeightProduct(weightProduct, productCountUnit));
            }
        });
        
        final Button productAddButton = productRootView.findViewById(R.id.product_row_add_btn);
        productAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoCompleteTextView productNameTextView = productRootView.findViewById(R.id.product_row_name);
                TextView productCountTextView = productRootView.findViewById(R.id.product_row_count);
                
                if (productNameTextView.getText().toString().equals("") || productCountTextView.getText().toString().equals("")) {
                    return;
                }
                
                Spinner productCountUnitSpinner = productRootView.findViewById(R.id.product_row_count_unit);
                String productCountUnit = (String) productCountUnitSpinner.getSelectedItem();
                
                double weightProduct = Double.parseDouble(productCountTextView.getText().toString());
                
                saveFoodIntakeProduct(getWeightProduct(weightProduct, productCountUnit));
                
                productAddButton.setVisibility(View.GONE);
                productCalcButton.setVisibility(View.GONE);
                productNameTextView.setEnabled(false);
                productCountTextView.setEnabled(false);
                productCountUnitSpinner.setEnabled(false);
                
                addProductRow();
            }
        });
    }
    
    private void saveFoodIntakeProduct(double weightProduct) {
        FoodIntakeProduct foodIntakeProduct = new FoodIntakeProduct();
        foodIntakeProduct.setFoodIntakeId(foodIntake.getId());
        foodIntakeProduct.setProductId(newProduct.getId());
        foodIntakeProduct.setWeight(weightProduct);
        
        FoodIntakeProductLab.get().saveNew(foodIntakeProduct);
    }
    
    private void displayCalculation(View productRootView, double weightNewProduct) {
        TextView proteinsTextView = productRootView.findViewById(R.id.product_row_proteins);
        TextView fatsTextView = productRootView.findViewById(R.id.product_row_fats);
        TextView carbohydratesTextView = productRootView.findViewById(R.id.product_row_carbohydrates);
        TextView glycemicIndexTextView = productRootView.findViewById(R.id.product_row_glycemic_idx);
        Spinner productCountUnitSpinner = productRootView.findViewById(R.id.product_row_count_unit);
        LinearLayout linearLayout2 = productRootView.findViewById(R.id.linearLayout2);
        LinearLayout linearLayout3 = productRootView.findViewById(R.id.linearLayout3);
        
        String productCountUnit = (String) productCountUnitSpinner.getSelectedItem();
        
        if (newProduct.getGlycemicIndex() > 0) {
            glycemicIndexTextView.setText(String.valueOf(newProduct.getGlycemicIndex()));
            linearLayout3.setVisibility(View.VISIBLE);
        }
        
        proteinsTextView.setText(formatDouble(calcElement(newProduct.getProteins(), weightNewProduct, productCountUnit)));
        fatsTextView.setText(formatDouble(calcElement(newProduct.getFats(), weightNewProduct, productCountUnit)));
        carbohydratesTextView.setText(formatDouble(calcElement(newProduct.getCarbohydrates(), weightNewProduct, productCountUnit)));
        linearLayout2.setVisibility(View.VISIBLE);
        
        displaySumCalculation(weightNewProduct);
    }
    
    private void displaySumCalculation(double weightNewProduct) {
        calcSumElements(weightNewProduct);
        
        binding.addFoodIntakeProteins.setText(formatDouble(sumProteins));
        binding.addFoodIntakeFats.setText(formatDouble(sumFats));
        binding.addFoodIntakeCarbohydrates.setText(formatDouble(sumCarbohydrates));
        
        binding.addFoodIntakeResumeWrapper.setVisibility(View.VISIBLE);
    }
    
    private void calcSumElements(double weightNewProduct) {
        Map<Product, Double> productsForFoodIntake = FoodIntakeProductLab.get().getProductsForFoodIntake(foodIntake.getId());
        
        sumProteins = 0;
        sumFats = 0;
        sumCarbohydrates = 0;
        
        for (Map.Entry<Product, Double> entry : productsForFoodIntake.entrySet()) {
            sumProteins += entry.getKey().getProteins() * entry.getValue() / 100;
            sumFats += entry.getKey().getFats() * entry.getValue() / 100;
            sumCarbohydrates += entry.getKey().getCarbohydrates() * entry.getValue() / 100;
        }
        
        sumProteins += newProduct.getProteins() * weightNewProduct / 100;
        sumFats += newProduct.getFats() * weightNewProduct / 100;
        sumCarbohydrates += newProduct.getCarbohydrates() * weightNewProduct / 100;
    }
    
    private double calcElement(double absAmountElement, double weightProduct, String productCountUnit) {
        return (getWeightProduct(weightProduct, productCountUnit) * absAmountElement) / 100;
    }
    
    private double getWeightProduct(double weightProduct, String productCountUnit) {
        if (productCountUnit.equals(getString(R.string.product_count_milligram))) {
            weightProduct /= 1000;
        } else if (productCountUnit.equals(getString(R.string.product_count_pcs))) {
            weightProduct = 1;
        } else if (productCountUnit.equals(getString(R.string.product_count_teaspoon))) {
            weightProduct *= 4;
        } else if (productCountUnit.equals(getString(R.string.product_count_table_spoon))) {
            weightProduct *= 8;
        }
        return weightProduct;
    }
    
    private String formatDouble(double result) {
        return String.format(Locale.getDefault(), "%1$,.2f", result);
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
    
    @Override
    public void onTimeSelect(Date time) {
        foodIntake.setTimeMillis(time.getTime());
        saveFoodIntake(foodIntake);
        
        updateTime();
    }
    
    @Override
    public void onDateSelect(Date date) {
        foodIntake.setTimeMillis(date.getTime());
        saveFoodIntake(foodIntake);
        
        updateDate();
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
                finish();
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
