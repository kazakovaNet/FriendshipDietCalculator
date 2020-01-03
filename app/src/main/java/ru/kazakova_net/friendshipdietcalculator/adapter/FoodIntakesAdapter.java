package ru.kazakova_net.friendshipdietcalculator.adapter;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ru.kazakova_net.friendshipdietcalculator.activity.ViewFoodIntakeActivity;
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
    
    public static String formatDate(long timeMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        
        return dateFormat.format(timeMillis);
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
    
    public void addItems(List<FoodIntake> newItems) {
        foodIntakes = newItems;
        notifyDataSetChanged();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        
        private FoodIntakesListItemBinding binding;
        private FoodIntake foodIntake;
        
        ViewHolder(FoodIntakesListItemBinding binding) {
            super(binding.getRoot());
            binding.getRoot().setOnClickListener(this);
            
            this.binding = binding;
        }
        
        public void bind(FoodIntake bindFoodIntake) {
            foodIntake = bindFoodIntake;
            binding.setFoodIntake(bindFoodIntake);
        }
        
        @Override
        public void onClick(View view) {
            Intent intent = ViewFoodIntakeActivity.getStartIntent(view.getContext(), foodIntake.getId());
            view.getContext().startActivity(intent);
        }
    }
}
