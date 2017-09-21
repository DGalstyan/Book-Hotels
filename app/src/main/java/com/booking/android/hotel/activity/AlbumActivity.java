package com.booking.android.hotel.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.booking.android.hotel.R;
import com.booking.android.hotel.utils.Utils;
import com.booking.android.hotel.drawer.NavigationDrawerFragment;
import com.booking.android.hotel.model.LocationItem;

import java.util.LinkedList;

import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class AlbumActivity extends BaseActivity implements View.OnClickListener {

    private LinkedList<LocationItem> locationsList;
    private EasyRecyclerAdapter locationsAdapter;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private RecyclerView recyclerViewLocations;
    private LinearLayout changeDatesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setTitle("");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_dates_layout:
                Utils.startActivityWithClipReveal(AlbumActivity.this, CalendarActivity.class, v);
                break;
        }
    }
}
