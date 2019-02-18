package ru.kazakova_net.friendshipdietcalculator.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
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
    
    private static final String ARG_TIME = "time";
    
    private TimePicker timePicker;
    private TimeSelectListener timeSelectListener;
    
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
        try{
            timeSelectListener = (TimeSelectListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().getClass().getSimpleName() + " must implement " + TimeSelectListener.class.getSimpleName());
        }
        
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
                                int hour = timePicker.getHour();
                                int minute = timePicker.getMinute();
                                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                                gregorianCalendar.set(GregorianCalendar.HOUR_OF_DAY, hour);
                                gregorianCalendar.set(GregorianCalendar.MINUTE, minute);
                                Date time = gregorianCalendar.getTime();
                                timeSelectListener.onTimeSelect(time);
                            }
                        })
                .create();
    }
    
    public interface TimeSelectListener{
        void onTimeSelect(Date time);
    }
}
