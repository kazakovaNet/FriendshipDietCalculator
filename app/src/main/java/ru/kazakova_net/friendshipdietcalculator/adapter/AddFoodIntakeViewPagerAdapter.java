package ru.kazakova_net.friendshipdietcalculator.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.kazakova_net.friendshipdietcalculator.R;
import ru.kazakova_net.friendshipdietcalculator.fragment.NoteFoodIntakeFragment;
import ru.kazakova_net.friendshipdietcalculator.fragment.ProductsFoodIntakeFragment;
import ru.kazakova_net.friendshipdietcalculator.fragment.TimeFoodIntakeFragment;

/**
 * Created by nkazakova on 25/02/2019.
 */
public class AddFoodIntakeViewPagerAdapter extends FragmentPagerAdapter {
    private final static int MAX_TABS = 3;
    private Context context;
    
    public AddFoodIntakeViewPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        
        this.context = context;
    }
    
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return TimeFoodIntakeFragment.newInstance();
        } else if (position == 1) {
            return ProductsFoodIntakeFragment.newInstance();
        } else {
            return NoteFoodIntakeFragment.newInstance();
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
            return context.getString(R.string.time_food_intake_fragment_title);
        } else if (position == 1) {
            return context.getString(R.string.products_food_intake_fragment_title);
        } else {
            return context.getString(R.string.note_food_intake_fragment_title);
        }
    }
}
