package ru.kazakova_net.friendshipdietcalculator.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.activity.AddFoodIntakeActivity;
import ru.kazakova_net.friendshipdietcalculator.databinding.NoteFoodIntakeFragmentBinding;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;

/**
 * Created by nkazakova on 25/02/2019.
 */
public class NoteFoodIntakeFragment extends Fragment {
    
    private static final String ARG_FOOD_INTAKE_ID = "food_intake_id";
    
    private NoteFoodIntakeFragmentBinding binding;
    private FoodIntake foodIntake;
    
    public NoteFoodIntakeFragment() {
    }
    
    public static NoteFoodIntakeFragment newInstance() {
        return new NoteFoodIntakeFragment();
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_food_intake, container, false);
        
        foodIntake = AddFoodIntakeActivity.getFoodIntake();
        
        binding.addFoodIntakeNoteField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            
            }
            
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                foodIntake.setNote(String.valueOf(charSequence));
            }
            
            @Override
            public void afterTextChanged(Editable editable) {
            
            }
        });
        
        return binding.getRoot();
    }
}
