package ru.kazakova_net.friendshipdietcalculator.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.activity.AddFoodIntakeActivity;
import ru.kazakova_net.friendshipdietcalculator.databinding.NoteFoodIntakeFragmentBinding;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;
import ru.kazakova_net.friendshipdietcalculator.model.lab.FoodIntakeLab;

/**
 * Created by nkazakova on 25/02/2019.
 */
public class NoteFoodIntakeFragment extends Fragment {
    
    private static final String ARG_FOOD_INTAKE_ID = "food_intake_id";
    
    private NoteFoodIntakeFragmentBinding binding;
    private FoodIntake foodIntake;
    
    public NoteFoodIntakeFragment() {
    }
    
    public static NoteFoodIntakeFragment newInstance(long foodIntakeId) {
        Bundle args = new Bundle();
        args.putLong(ARG_FOOD_INTAKE_ID, foodIntakeId);
        NoteFoodIntakeFragment fragment = new NoteFoodIntakeFragment();
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_food_intake, container, false);
        
        foodIntake = FoodIntakeLab.get().getById(getArguments().getLong(ARG_FOOD_INTAKE_ID));
        
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
    
    @Override
    public void onPause() {
        super.onPause();
        
        AddFoodIntakeActivity.saveFoodIntake(foodIntake);
    }
}
