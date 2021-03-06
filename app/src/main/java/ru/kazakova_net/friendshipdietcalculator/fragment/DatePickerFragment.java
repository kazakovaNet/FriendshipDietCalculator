package ru.kazakova_net.friendshipdietcalculator.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.kazakova_net.friendshipdietcalculator.R;

/**
 * Created by Kazakova_net on 29.01.2019.
 */
public class DatePickerFragment extends DialogFragment {
    
    public static final String EXTRA_DATE = "ru.kazakova_net.friendshipdietcalculator.date";
    private static final String ARG_DATE = "date";
    private DatePicker datePicker;
    private Button okButton;
    
    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);
        
        datePicker = view.findViewById(R.id.dialog_date_picker);
        initDatePickerFromArguments();
        
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK, getNewDate());
                            }
                        })
                .create();
    }
    
    private void sendResult(int resultCode, Date date) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        
        if (getTargetFragment() == null) {
            getActivity().setResult(resultCode, intent);
            
            return;
        }
        
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
    
    private Date getNewDate() {
        return updateSourceDate();
    }
    
    private Date updateSourceDate() {
        Calendar sourceDateCalendar = Calendar.getInstance();
        sourceDateCalendar.setTime((Date) getArguments().getSerializable(ARG_DATE));
        
        Calendar newDateCalendar = Calendar.getInstance();
        newDateCalendar.setTime(getDatePickerDate());
        
        sourceDateCalendar.set(Calendar.YEAR, newDateCalendar.get(Calendar.YEAR));
        sourceDateCalendar.set(Calendar.MONTH, newDateCalendar.get(Calendar.MONTH));
        sourceDateCalendar.set(Calendar.DAY_OF_MONTH, newDateCalendar.get(Calendar.DAY_OF_MONTH));
        
        return sourceDateCalendar.getTime();
    }
    
    private Date getDatePickerDate() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        
        return new GregorianCalendar(year, month, day).getTime();
    }
    
    private void initDatePickerFromArguments() {
        Date date = (Date) getArguments().getSerializable(ARG_DATE);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        datePicker.init(year, month, day, null);
    }
}
