package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.widget.Toast;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.adapter.ProductsAdapter;
import ru.kazakova_net.friendshipdietcalculator.databinding.ListProductsActivityBinding;
import ru.kazakova_net.friendshipdietcalculator.model.lab.ProductLab;

public class ListProductsActivity extends AppCompatActivity {
    
    private ListProductsActivityBinding binding;
    private ProductsAdapter adapter;
    
    public static Intent getIntent(Context context) {
        return new Intent(context, ListProductsActivity.class);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_products);
        
        adapter = new ProductsAdapter(ProductLab.get().getAllProducts());
        binding.listProductsRecyclerView.setAdapter(adapter);
        binding.listProductsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
