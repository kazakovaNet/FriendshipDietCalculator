package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.databinding.AddProductActivityBinding;
import ru.kazakova_net.friendshipdietcalculator.model.Product;
import ru.kazakova_net.friendshipdietcalculator.model.ProductLab;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {
    
    private AddProductActivityBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_product);
        
        binding.addProductSaveBtn.setOnClickListener(this);
    }
    
    public static Intent getIntent(Context context) {
        return new Intent(context, AddProductActivity.class);
    }
    
    @Override
    public void onClick(View view) {
        Product product = new Product();
        product.setTitle(binding.addProductTitleInput.getText().toString());
        product.setProteins(Double.parseDouble(binding.addProductProteinsInput.getText().toString()));
        product.setFats(Double.parseDouble(binding.addProductFatsInput.getText().toString()));
        product.setCarbohydrates(Double.parseDouble(binding.addProductCarbohydratesInput.getText().toString()));
        product.setCalories(Double.parseDouble(binding.addProductCaloriesInput.getText().toString()));
        product.setFromBS((byte) (binding.addProductFromBsChk.isChecked() ? 1 : 0));
        
        ProductLab.get(getApplicationContext()).addProduct(product);
    }
}
