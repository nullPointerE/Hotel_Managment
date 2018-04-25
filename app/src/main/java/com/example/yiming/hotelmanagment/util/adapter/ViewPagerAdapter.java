package com.example.yiming.hotelmanagment.util.adapter;


import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.yiming.hotelmanagment.common.Constants;
import com.example.yiming.hotelmanagment.view.fragment.BookedFragment;
import com.example.yiming.hotelmanagment.view.fragment.RoomFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {
    int count;
    public ViewPagerAdapter(FragmentManager fm, int i) {
        super(fm);
        count=i;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RoomFragment();
            case 1:
                return new BookedFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        if(position==0)
            return Constants.ROOMS;
        else{
            return Constants.BOOKED;
        }
    }
}
