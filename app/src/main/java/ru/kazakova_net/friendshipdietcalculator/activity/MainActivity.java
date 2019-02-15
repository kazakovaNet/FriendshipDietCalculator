package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.databinding.MainActivityBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    private MainActivityBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        
        binding.mainMenuAddFoodIntakeBtn.setOnClickListener(this);
        binding.mainMenuAddProductBtn.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.main_menu_add_product_btn:
                intent = AddProductActivity.getIntent(MainActivity.this);
                startActivity(intent);
                break;
            case R.id.main_menu_add_food_intake_btn:
                intent = AddFoodIntakeActivity.getIntent(MainActivity.this);
                startActivity(intent);
                break;
        }
    }
}
