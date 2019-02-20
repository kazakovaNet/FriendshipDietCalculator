package ru.kazakova_net.friendshipdietcalculator.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.adapter.FoodIntakesAdapter;
import ru.kazakova_net.friendshipdietcalculator.databinding.ListFoodIntakesActivityBinding;
import ru.kazakova_net.friendshipdietcalculator.fragment.DatePickerFragment;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntakeLab;

public class ListFoodIntakesActivity extends AppCompatActivity implements DatePickerFragment.DateSelectListener {
    
    private static final String DIALOG_TIME = "DialogTime";
    private static final String DIALOG_DATE = "DialogDate";
    
    private ListFoodIntakesActivityBinding binding;
    private FoodIntakesAdapter adapter;
    
    private Date filterDate = new Date();
    
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
        
        binding.listFoodIntakesDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(filterDate);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        
        updateDate();
    }
    
    @Override
    public void onDateSelect(Date date) {
        filterDate = date;
        
        updateDate();
        
        Calendar sourceCalendar = Calendar.getInstance();
        sourceCalendar.setTime(filterDate);
        
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(Calendar.YEAR, sourceCalendar.get(Calendar.YEAR));
        gregorianCalendar.set(Calendar.MONTH, sourceCalendar.get(Calendar.MONTH));
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, sourceCalendar.get(Calendar.DAY_OF_MONTH));
        gregorianCalendar.set(Calendar.HOUR_OF_DAY, 0);
        gregorianCalendar.set(Calendar.MINUTE, 0);
        gregorianCalendar.set(Calendar.SECOND, 0);
        
        long timeMillisStart = gregorianCalendar.getTimeInMillis();
        
        gregorianCalendar.set(Calendar.HOUR_OF_DAY, 23);
        gregorianCalendar.set(Calendar.MINUTE, 59);
        gregorianCalendar.set(Calendar.SECOND, 59);
        
        long timeMillisEnd = gregorianCalendar.getTimeInMillis();
        
        adapter.addItems(FoodIntakeLab.get().getByTimeRange(timeMillisStart, timeMillisEnd));
    }
    
    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy г", Locale.getDefault());
        
        binding.listFoodIntakesDateBtn.setText(dateFormat.format(filterDate));
    }
}
