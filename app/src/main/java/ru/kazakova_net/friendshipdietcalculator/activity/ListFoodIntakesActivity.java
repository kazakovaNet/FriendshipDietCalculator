package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.adapter.FoodIntakesAdapter;
import ru.kazakova_net.friendshipdietcalculator.databinding.ListFoodIntakesActivityBinding;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeLab;

public class ListFoodIntakesActivity extends AppCompatActivity {
    
    private ListFoodIntakesActivityBinding binding;
    private FoodIntakesAdapter adapter;
    
    public static Intent getIntent(Context context) {
        return new Intent(context, ListFoodIntakesActivity.class);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food_intakes);
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_list_food_intakes);
        
        adapter = new FoodIntakesAdapter(FoodIntakeLab.get().getAll());
        binding.listFoodIntakesRecyclerView.setAdapter(adapter);
        binding.listFoodIntakesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
