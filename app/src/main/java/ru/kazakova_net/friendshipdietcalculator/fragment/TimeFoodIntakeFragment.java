package ru.kazakova_net.friendshipdietcalculator.fragment;

import android.app.Activity;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.activity.AddFoodIntakeActivity;
import ru.kazakova_net.friendshipdietcalculator.databinding.TimeFoodIntakeFragmentBinding;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;

/**
 * Created by nkazakova on 25/02/2019.
 */
public class TimeFoodIntakeFragment extends Fragment {
    
    private static final String DIALOG_TIME = "DialogTime";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    
    private TimeFoodIntakeFragmentBinding binding;
    private FoodIntake foodIntake;
    
    public TimeFoodIntakeFragment() {
    }
    
    public static TimeFoodIntakeFragment newInstance() {
        return new TimeFoodIntakeFragment();
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_food_intake, container, false);
        
        foodIntake = AddFoodIntakeActivity.getFoodIntake();
        foodIntake.setType(getActivity().getString(R.string.type_intake_breakfast));
        
        binding.addFoodIntakeTypeRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                String foodIntakeType = "";
                
                switch (checkedId) {
                    case R.id.add_food_intake_type_breakfast_radio_btn:
                        foodIntakeType = getActivity().getString(R.string.type_intake_breakfast);
                        break;
                    case R.id.add_food_intake_type_lunch_radio_btn:
                        foodIntakeType = getActivity().getString(R.string.type_intake_lunch);
                        break;
                    case R.id.add_food_intake_type_dinner_radio_btn:
                        foodIntakeType = getActivity().getString(R.string.type_intake_dinner);
                        break;
                    case R.id.add_food_intake_type_afternoon_tea_radio_btn:
                        foodIntakeType = getActivity().getString(R.string.type_intake_afternoon_tea);
                        break;
                    case R.id.add_food_intake_type_supper_radio_btn:
                        foodIntakeType = getActivity().getString(R.string.type_intake_supper);
                        break;
                }
                
                foodIntake.setType(foodIntakeType);
            }
        });
        
        binding.addFoodIntakeDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(new Date(foodIntake.getTimeMillis()));
                dialog.setTargetFragment(TimeFoodIntakeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        binding.addFoodIntakeTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(new Date(foodIntake.getTimeMillis()));
                dialog.setTargetFragment(TimeFoodIntakeFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });
        
        updateDate();
        
        updateTime();
        
        return binding.getRoot();
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
        
        updateTime();
    }
    
    public void onDateSelect(Date date) {
        foodIntake.setTimeMillis(date.getTime());
        
        updateDate();
    }
}
