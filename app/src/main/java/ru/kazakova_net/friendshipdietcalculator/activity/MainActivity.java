package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        
        binding.mainMenuAddProductBtn.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View view) {
        Intent intent = AddProductActivity.getIntent(MainActivity.this);
        startActivity(intent);
    }
}
