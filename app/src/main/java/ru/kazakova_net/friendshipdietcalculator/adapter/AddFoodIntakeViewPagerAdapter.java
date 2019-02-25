package ru.kazakova_net.friendshipdietcalculator.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.fragment.FragmentProductsFoodIntake;
import ru.kazakova_net.friendshipdietcalculator.fragment.FragmentTimeFoodIntake;

/**
 * Created by nkazakova on 25/02/2019.
 */
public class AddFoodIntakeViewPagerAdapter extends FragmentPagerAdapter {
    private final static int MAX_TABS = 2;
    private Context context;
    
    public AddFoodIntakeViewPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        
        this.context = context;
    }
    
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return FragmentTimeFoodIntake.newInstance();
        } else {
            return FragmentProductsFoodIntake.newInstance();
        }
    }
    
    @Override
    public int getCount() {
        return MAX_TABS;
    }
    
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.fragment_time_food_intake_title);
        } else {
            return context.getString(R.string.fragment_products_food_intake_title);
        }
    }
}
