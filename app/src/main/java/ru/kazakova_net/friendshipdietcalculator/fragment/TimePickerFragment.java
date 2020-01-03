package ru.kazakova_net.friendshipdietcalculator.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.kazakova_net.friendshipdietcalculator.R;

/**
 * Created by nkazakova on 18/02/2019.
 */
public class TimePickerFragment extends DialogFragment {
    
    public static final String EXTRA_TIME = "ru.kazakova_net.friendshipdietcalculator.time";
    private static final String ARG_TIME = "time";
    private TimePicker timePicker;
    
    public static TimePickerFragment newInstance(Date time) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, time);
        
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        
        return fragment;
    }
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Date time = (Date) getArguments().getSerializable(ARG_TIME);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);
        
        timePicker = view.findViewById(R.id.dialog_time_picker);
        timePicker.setHour(hour);
        timePicker.setMinute(minute);
        
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK, getNewTime());
                            }
                        })
                .create();
    }
    
    private void sendResult(int resultCode, Date time) {
        if (getTargetFragment() == null) {
            return;
        }
        
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, time);
        
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    private Date getNewTime() {
        return updateSourceTime();
    }
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    private Date updateSourceTime() {
        Calendar sourceDateCalendar = Calendar.getInstance();
        sourceDateCalendar.setTime((Date) getArguments().getSerializable(ARG_TIME));
        
        Calendar newDateCalendar = Calendar.getInstance();
        newDateCalendar.setTime(getTimePickerTime());
        
        sourceDateCalendar.set(Calendar.HOUR_OF_DAY, newDateCalendar.get(Calendar.HOUR_OF_DAY));
        sourceDateCalendar.set(Calendar.MINUTE, newDateCalendar.get(Calendar.MINUTE));
        
        return sourceDateCalendar.getTime();
    }
    
    @RequiresApi(api = Build.VERSION_CODES.M)
    private Date getTimePickerTime() {
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.set(GregorianCalendar.HOUR_OF_DAY, hour);
        gregorianCalendar.set(GregorianCalendar.MINUTE, minute);
        
        return gregorianCalendar.getTime();
    }
}
