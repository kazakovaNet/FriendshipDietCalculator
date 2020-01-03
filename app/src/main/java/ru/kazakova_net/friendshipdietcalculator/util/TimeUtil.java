package ru.kazakova_net.friendshipdietcalculator.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nkazakova on 25/02/2019.
 */
public class TimeUtil {
    
    public static final String TIME_START_KEY = "time_start";
    public static final String TIME_END_KEY = "time_end";
    
    public static Map<String, Long> getRangeDay(Date filterDate) {
        Map<String, Long> range = new HashMap<>();
        
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
        
        range.put(TIME_START_KEY, timeMillisStart);
        range.put(TIME_END_KEY, timeMillisEnd);
        
        return range;
    }
}
