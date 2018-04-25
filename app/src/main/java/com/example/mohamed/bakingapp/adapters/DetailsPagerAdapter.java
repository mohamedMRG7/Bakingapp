package com.example.mohamed.bakingapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mohamed.bakingapp.fragments.IngridentsFragment;
import com.example.mohamed.bakingapp.fragments.StepsFragment;

/**
 * Created by mohamed on 4/18/18.
 */

public class DetailsPagerAdapter extends FragmentStatePagerAdapter {
    private int tabCount;
    private String json;
    private int recipId;

    public DetailsPagerAdapter(FragmentManager fm, int tabCount,String json,int recipId) {
        super(fm);
        this.tabCount=tabCount;
        this.json=json;
        this.recipId=recipId;
    }

    @Override
    public Fragment getItem(int position) {

       switch (position)
       {
           case 0:
               IngridentsFragment ingridentsFragment=new IngridentsFragment();
               ingridentsFragment.setFragmentData(json,recipId);
               return  ingridentsFragment;

           case 1:
               StepsFragment stepsFragment=new StepsFragment();
               stepsFragment.setFragmentData(json,recipId);
               return stepsFragment;

           default:return null;
       }


    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
