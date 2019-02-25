package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.databinding.AddProductActivityBinding;
import ru.kazakova_net.friendshipdietcalculator.model.Product;
import ru.kazakova_net.friendshipdietcalculator.model.ProductLab;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {
    
    private AddProductActivityBinding binding;
    
    public static Intent getIntent(Context context) {
        return new Intent(context, AddProductActivity.class);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product);
        
        binding.addProductSaveBtn.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        if (validateInput()) {
            saveProduct();
            
            completeSaving(view);
        }
    }
    
    private boolean validateInput() {
        if (TextUtils.isEmpty(binding.addProductTitleInput.getText().toString())) {
            binding.addProductTitleInput.setError(getString(R.string.add_product_invalid_input_msg));
            
            return false;
        }
        
        if (TextUtils.isEmpty(binding.addProductProteinsInput.getText().toString())) {
            binding.addProductProteinsInput.setError(getString(R.string.add_product_invalid_input_msg));
            
            return false;
        }
        
        if (TextUtils.isEmpty(binding.addProductFatsInput.getText().toString())) {
            binding.addProductFatsInput.setError(getString(R.string.add_product_invalid_input_msg));
            
            return false;
        }
        
        if (TextUtils.isEmpty(binding.addProductCarbohydratesInput.getText().toString())) {
            binding.addProductCarbohydratesInput.setError(getString(R.string.add_product_invalid_input_msg));
            
            return false;
        }
        
        if (TextUtils.isEmpty(binding.addProductCaloriesInput.getText().toString())) {
            binding.addProductCaloriesInput.setError(getString(R.string.add_product_invalid_input_msg));
            
            return false;
        }
        
        if (TextUtils.isEmpty(binding.addProductGlycemicIdxInput.getText().toString())) {
            binding.addProductGlycemicIdxInput.setError(getString(R.string.add_product_invalid_input_msg));
            
            return false;
        }
        
        return true;
    }
    
    private void completeSaving(View view) {
        Snackbar.make(view, R.string.add_product_success_msg, Snackbar.LENGTH_SHORT).show();
        
        resetInputs();
    }
    
    private void resetInputs() {
        binding.addProductTitleInput.setText("");
        binding.addProductProteinsInput.setText("");
        binding.addProductFatsInput.setText("");
        binding.addProductCarbohydratesInput.setText("");
        binding.addProductCaloriesInput.setText("");
        binding.addProductGlycemicIdxInput.setText("");
        binding.addProductFromBsChk.setChecked(true);
    }
    
    private void saveProduct() {
        Product product = new Product();
        product.setTitle(binding.addProductTitleInput.getText().toString());
        product.setProteins(Double.parseDouble(binding.addProductProteinsInput.getText().toString()));
        product.setFats(Double.parseDouble(binding.addProductFatsInput.getText().toString()));
        product.setCarbohydrates(Double.parseDouble(binding.addProductCarbohydratesInput.getText().toString()));
        product.setCalories(Double.parseDouble(binding.addProductCaloriesInput.getText().toString()));
        product.setGlycemicIndex(Integer.parseInt(binding.addProductGlycemicIdxInput.getText().toString()));
        product.setFromBS((byte) (binding.addProductFromBsChk.isChecked() ? 1 : 0));
        
        ProductLab.get().addProduct(product);
    }
}
