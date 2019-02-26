package ru.kazakova_net.friendshipdietcalculator.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Locale;

/**
 * Created by nkazakova on 15/02/2019.
 */
public class CommonUtil {
    /**
     * Реагирует на клик вне виджета EditText и скрывает клавиатуру
     *
     * @param ev событие клика
     * @return результат назначения обработчика
     */
    public static MotionEvent dispatchTouchEvent(MotionEvent ev, View view, InputMethodManager imm) {
        
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (view instanceof EditText) {
                
                hideKeyboard(view, imm);
            }
        }
        
        return ev;
    }
    
    /**
     * Метод скрывает клавиатуру
     *
     * @param view
     * @param imm
     */
    public static void hideKeyboard(View view, InputMethodManager imm) {
        if (view != null && imm != null) {
            view.clearFocus();
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    
    public static String formatDouble(double result) {
        return String.format(Locale.getDefault(), "%1$,.2f", result);
    }
    
    public static String formatShortDouble(double result) {
        return String.format(Locale.getDefault(), "%1$,.0f", result);
    }
}
