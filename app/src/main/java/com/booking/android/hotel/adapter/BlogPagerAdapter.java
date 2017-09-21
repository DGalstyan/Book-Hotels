package com.booking.android.hotel.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.booking.android.hotel.fragment.BlogItemFragment;

public class BlogPagerAdapter extends FragmentStatePagerAdapter {

    public BlogPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            default:
                return BlogItemFragment.newInstance(position);
        }
    }
}