package ru.kazakova_net.friendshipdietcalculator.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.databinding.ExportDataActivityBinding;
import ru.kazakova_net.friendshipdietcalculator.fragment.DatePickerFragment;
import ru.kazakova_net.friendshipdietcalculator.model.FoodIntake;
import ru.kazakova_net.friendshipdietcalculator.model.Product;
import ru.kazakova_net.friendshipdietcalculator.model.lab.FoodIntakeLab;
import ru.kazakova_net.friendshipdietcalculator.model.lab.FoodIntakeProductLab;
import ru.kazakova_net.friendshipdietcalculator.util.TimeUtil;

import static ru.kazakova_net.friendshipdietcalculator.util.CommonUtil.formatDouble;
import static ru.kazakova_net.friendshipdietcalculator.util.CommonUtil.formatShortDouble;

public class ExportDataActivity extends AppCompatActivity {
    
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;
    
    private ExportDataActivityBinding binding;
    private double sumProteins;
    private double sumFats;
    private double sumCarbohydrates;
    
    private Date filterDate = new Date();
    private String resultString = "";
    
    public static Intent getIntent(Context context) {
        return new Intent(context, ExportDataActivity.class);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_data);
        
        binding = DataBindingUtil.setContentView(this, R.layout.activity_export_data);
        
        binding.exportDataBuildBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetSumElements();
    
                buildExportString();
            }
        });
        
        binding.exportDataDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(filterDate);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        
        binding.exportDataCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("123", resultString);
                clipboard.setPrimaryClip(clip);
            }
        });
        
        updateDate();
    }
    
    private void resetSumElements() {
        sumProteins = 0;
        sumFats = 0;
        sumCarbohydrates = 0;
    }
    
    private void buildExportString() {
        final StringBuilder stringBuilder = new StringBuilder();
        Map<String, Long> rangeDay = TimeUtil.getRangeDay(filterDate);
        
        long timeMillisStart = rangeDay.get(TimeUtil.TIME_START_KEY);
        long timeMillisEnd = rangeDay.get(TimeUtil.TIME_END_KEY);
        
        List<FoodIntake> intakeList = FoodIntakeLab.get().getByTimeRange(timeMillisStart, timeMillisEnd);
        for (FoodIntake foodIntake : intakeList) {
            Map<Product, Double> products = FoodIntakeProductLab.get().getProductsForFoodIntake(foodIntake.getId());
            
            calcSumElements(products);
            
            stringBuilder
                    // [B]08.15 Завтрак[/B]
                    .append("[B]")
                    .append(formatDate(foodIntake.getTimeMillis()))
                    .append(" ")
                    .append(foodIntake.getType())
                    .append("[/B]")
                    .append("\n");
            
            for (Map.Entry<Product, Double> entry : products.entrySet()) {
                stringBuilder
                        // Льняное масло - 16 гр
                        .append(entry.getKey().getTitle())
                        .append(" - ")
                        .append(formatShortDouble(entry.getValue()))
                        .append(" гр.")
                        .append("\n");
            }
            
            if (foodIntake.hasNote()) {
                stringBuilder
                        // [I]Заметка 1[/I]
                        .append("\n")
                        .append("[I]")
                        .append(foodIntake.getNote())
                        .append("[/I]")
                        .append("\n");
            }
            
            stringBuilder
                    .append("\n");
        }
        
        stringBuilder
                // Белки - 30,2
                // Жиры - 30,4
                // Углеводы - 40,6
                .append("Белки - ")
                .append(formatDouble(sumProteins))
                .append("\n")
                .append("Жиры - ")
                .append(formatDouble(sumFats))
                .append("\n")
                .append("Углеводы - ")
                .append(formatDouble(sumCarbohydrates));
        
        resultString = stringBuilder.toString();
        
        binding.exportDataResultText.setText(resultString);
        binding.exportDataResultText.setVisibility(View.VISIBLE);
    }
    
    private void calcSumElements(Map<Product, Double> productsForFoodIntake) {
        for (Map.Entry<Product, Double> entry : productsForFoodIntake.entrySet()) {
            sumProteins += entry.getKey().getProteins() * entry.getValue() / 100;
            sumFats += entry.getKey().getFats() * entry.getValue() / 100;
            sumCarbohydrates += entry.getKey().getCarbohydrates() * entry.getValue() / 100;
        }
    }
    
    private String formatDate(long timeMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH.mm", Locale.getDefault());
        
        return dateFormat.format(timeMillis);
    }
    
    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy г", Locale.getDefault());
        
        binding.exportDataDateBtn.setText(dateFormat.format(filterDate));
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String s = "";
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            
            onDateSelect(date);
        }
    }
    
    public void onDateSelect(Date date) {
        filterDate = date;
        
        updateDate();
    }
}
