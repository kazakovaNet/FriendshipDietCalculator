package ru.kazakova_net.friendshipdietcalculator.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.kazakova_net.friendshipdietcalculator.databinding.FoodIntakesListItemBinding;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;

/**
 * Created by nkazakova on 20/02/2019.
 */
public class FoodIntakesAdapter extends RecyclerView.Adapter<FoodIntakesAdapter.ViewHolder> {
    
    private List<FoodIntake> foodIntakes;
    
    public FoodIntakesAdapter(List<FoodIntake> foodIntakes) {
        this.foodIntakes = foodIntakes;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        FoodIntakesListItemBinding binding =
                FoodIntakesListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        
        return new ViewHolder(binding);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(foodIntakes.get(position));
    }
    
    @Override
    public int getItemCount() {
        return foodIntakes.size();
    }
    
    public static String formatDate(long timeMillis){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
    
        return dateFormat.format(timeMillis);
    }
    
    public void addItems(List<FoodIntake> newItems){
        foodIntakes = newItems;
        notifyDataSetChanged();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        
        private FoodIntakesListItemBinding binding;
        
        ViewHolder(FoodIntakesListItemBinding binding) {
            super(binding.getRoot());
            
            this.binding = binding;
        }
        
        public void bind(FoodIntake foodIntake) {
            binding.setFoodIntake(foodIntake);
        }
    }
}
