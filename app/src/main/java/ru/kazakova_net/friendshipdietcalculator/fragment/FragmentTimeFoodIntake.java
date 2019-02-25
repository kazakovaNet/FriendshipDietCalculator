package ru.kazakova_net.friendshipdietcalculator.fragment;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.databinding.TimeFoodIntakeFragmentBinding;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeLab;

/**
 * Created by nkazakova on 25/02/2019.
 */
public class FragmentTimeFoodIntake extends Fragment {
    
    private static final String DIALOG_TIME = "DialogTime";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    
    private TimeFoodIntakeFragmentBinding binding;
    private FoodIntake foodIntake;
    
    public FragmentTimeFoodIntake() {
    }
    
    public static FragmentTimeFoodIntake newInstance() {
        return new FragmentTimeFoodIntake();
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_food_intake, container, false);
        
        initFoodIntake();
        
        binding.addFoodIntakeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String foodIntakeType = (String) adapterView.getItemAtPosition(i);
                foodIntake.setType(foodIntakeType);
                
                saveFoodIntake(foodIntake);
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            
            }
        });
        binding.addFoodIntakeDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(new Date(foodIntake.getTimeMillis()));
                dialog.setTargetFragment(FragmentTimeFoodIntake.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        binding.addFoodIntakeTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(new Date(foodIntake.getTimeMillis()));
                dialog.setTargetFragment(FragmentTimeFoodIntake.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });
        
        updateDate();
        
        updateTime();
        
        return binding.getRoot();
    }
    
    private void initFoodIntake() {
        foodIntake = new FoodIntake();
        long foodIntakeId = saveFoodIntake(foodIntake);
        foodIntake.setId(foodIntakeId);
        foodIntake.setTimeMillis(System.currentTimeMillis());
    }
    
    // FIXME: 25/02/2019
    private long saveFoodIntake(FoodIntake foodIntake) {
        if (FoodIntakeLab.get().isSaved(foodIntake.getId())) {
            FoodIntakeLab.get().update(foodIntake);
            
            return foodIntake.getId();
        } else {
            return FoodIntakeLab.get().add(foodIntake);
        }
    }
    
    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy г", Locale.getDefault());
        
        binding.addFoodIntakeDateBtn.setText(dateFormat.format(foodIntake.getTimeMillis()));
    }
    
    private void updateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        
        binding.addFoodIntakeTimeBtn.setText(dateFormat.format(foodIntake.getTimeMillis()));
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            
            onDateSelect(date);
        } else if (requestCode == REQUEST_TIME) {
            Date time = (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            
            onTimeSelect(time);
        }
    }
    
    public void onTimeSelect(Date time) {
        foodIntake.setTimeMillis(time.getTime());
        saveFoodIntake(foodIntake);
        
        updateTime();
    }
    
    public void onDateSelect(Date date) {
        foodIntake.setTimeMillis(date.getTime());
        saveFoodIntake(foodIntake);
        
        updateDate();
    }
}
