package ru.kazakova_net.friendshipdietcalculator.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.activity.AddFoodIntakeActivity;
import ru.kazakova_net.friendshipdietcalculator.databinding.ProductsFoodIntakeFragmentBinding;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeProduct;
import ru.kazakova_net.friendshipdietcalculator.model.Product;
import ru.kazakova_net.friendshipdietcalculator.model.lab.FoodIntakeProductLab;
import ru.kazakova_net.friendshipdietcalculator.model.lab.ProductLab;
import ru.kazakova_net.friendshipdietcalculator.util.CommonUtil;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static ru.kazakova_net.friendshipdietcalculator.util.CommonUtil.formatDouble;

/**
 * Created by nkazakova on 25/02/2019.
 */
public class ProductsFoodIntakeFragment extends Fragment {
    
    private ProductsFoodIntakeFragmentBinding binding;
    private Product newProduct;
    private long foodIntakeId;
    
    private double sumProteins;
    private double sumFats;
    private double sumCarbohydrates;
    
    public static ProductsFoodIntakeFragment newInstance() {
        return new ProductsFoodIntakeFragment();
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products_food_intake, container, false);
        
        foodIntakeId = AddFoodIntakeActivity.getFoodIntake().getId();
        
        addProductRow();
        
        return binding.getRoot();
    }
    
    private void addProductRow() {
        View productsRootView = LayoutInflater.from(getActivity()).inflate(R.layout.product_row, binding.addFoodIntakeProductsRootView, false);
        
        initProductRow(productsRootView);
        
        binding.addFoodIntakeProductsRootView
                .addView(productsRootView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
    
    private void initProductRow(final View productRootView) {
        List<Product> allProducts = ProductLab.get().getAllProducts();
        ArrayAdapter<Product> productArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, allProducts);
        
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
                CommonUtil.hideKeyboard(productNameTextView, (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE));
                
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
    
    private void saveFoodIntakeProduct(double weightProduct) {
        FoodIntakeProduct foodIntakeProduct = new FoodIntakeProduct();
        foodIntakeProduct.setFoodIntakeId(foodIntakeId);
        foodIntakeProduct.setProductId(newProduct.getId());
        foodIntakeProduct.setWeight(weightProduct);
        
        FoodIntakeProductLab.get().saveNew(foodIntakeProduct);
    }
    
    
    private double calcElement(double absAmountElement, double weightProduct, String productCountUnit) {
        return (getWeightProduct(weightProduct, productCountUnit) * absAmountElement) / 100;
    }
    
    private void calcSumElements(double weightNewProduct) {
        Map<Product, Double> productsForFoodIntake = FoodIntakeProductLab.get().getProductsForFoodIntake(foodIntakeId);
        
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
    
    private void displaySumCalculation(double weightNewProduct) {
        calcSumElements(weightNewProduct);
        
        binding.addFoodIntakeProteins.setText(formatDouble(sumProteins));
        binding.addFoodIntakeFats.setText(formatDouble(sumFats));
        binding.addFoodIntakeCarbohydrates.setText(formatDouble(sumCarbohydrates));
        
        binding.addFoodIntakeResumeWrapper.setVisibility(View.VISIBLE);
    }
}
