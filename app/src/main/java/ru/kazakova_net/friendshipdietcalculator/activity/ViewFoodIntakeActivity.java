package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Map;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.databinding.ActivityViewFoodIntakeBinding;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;
import ru.kazakova_net.friendshipdietcalculator.model.Product;
import ru.kazakova_net.friendshipdietcalculator.model.lab.FoodIntakeLab;
import ru.kazakova_net.friendshipdietcalculator.model.lab.FoodIntakeProductLab;
import ru.kazakova_net.friendshipdietcalculator.util.ProductUtil;

public class ViewFoodIntakeActivity extends AppCompatActivity {
    
    public static final String FOOD_INTAKE_ID_EXT = "FOOD_INTAKE_ID_EXT";
    
    private FoodIntake foodIntake;
    private ActivityViewFoodIntakeBinding binding;
    
    public static Intent getStartIntent(Context context, Long foodIntakeId) {
        Intent intent = new Intent(context, ViewFoodIntakeActivity.class);
        intent.putExtra(FOOD_INTAKE_ID_EXT, foodIntakeId);
        return intent;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        foodIntake = FoodIntakeLab.get().getById(getIntent().getLongExtra(FOOD_INTAKE_ID_EXT, -1));
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_food_intake);
        binding.setFoodIntake(foodIntake);
        
        Map<Product, Double> productsForFoodIntake = FoodIntakeProductLab.get().getProductsForFoodIntake(foodIntake.getId());
        ArrayList<String> productsList = ProductUtil.getProductsList(productsForFoodIntake);
        
        binding.viewFoodIntakeProductsList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productsList));
        binding.viewFoodIntakeProductsList.setDividerHeight(2);
    }
}
