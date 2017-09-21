package com.booking.android.hotel.activity;

import android.os.Bundle;

import com.booking.android.hotel.R;

public class ToursActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours);

        initializeToolbar();
    }

    private void initializeToolbar() {
    }
}
