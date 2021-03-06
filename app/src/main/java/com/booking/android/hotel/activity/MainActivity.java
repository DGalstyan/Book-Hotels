package com.booking.android.hotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.booking.android.hotel.R;
import com.booking.android.hotel.helpers.RealmController;
import com.booking.android.hotel.utils.Utils;
import com.booking.android.hotel.adapter.FeaturedEventsAdapter;
import com.booking.android.hotel.adapter.LocationsAdapter;
import com.booking.android.hotel.drawer.NavigationDrawerFragment;
import com.booking.android.hotel.model.LocationItem;

import java.util.LinkedList;

import io.realm.RealmResults;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

/**
 * The type Main activity.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinkedList<LocationItem> locationsList;
    private EasyRecyclerAdapter locationsAdapter;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private RecyclerView recyclerViewLocations;
    private LinearLayout changeDatesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setTitle("");

        setUpNavigationDrawer();
        setupRecyclerView();

        changeDatesLayout = (LinearLayout) findViewById(R.id.change_dates_layout);
        changeDatesLayout.setOnClickListener(this);
    }

    private void setupRecyclerView() {
        recyclerViewLocations = (RecyclerView) findViewById(R.id.recycler_view_locations);
        recyclerViewLocations.setHasFixedSize(true);
        recyclerViewLocations.setNestedScrollingEnabled(false);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(
                this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewLocations.setLayoutManager(mLayoutManager);

        RealmResults<LocationItem> locationsList =  RealmController.with(this).getLocationItems();

        locationsAdapter = new EasyRecyclerAdapter<>(this, LocationsAdapter.class, locationsList);
        recyclerViewLocations.setAdapter(locationsAdapter);

        setupOffers();
    }


    private void setupOffers() {
        RecyclerView recyclerViewLocations = (RecyclerView) findViewById(R.id.recycler_view_offers);
        recyclerViewLocations.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(
                this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewLocations.setLayoutManager(mLayoutManager);

        locationsList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            LocationItem locationItem = new LocationItem();
            locationsList.add(locationItem);
        }

        locationsAdapter = new EasyRecyclerAdapter<>(this, FeaturedEventsAdapter.class, locationsList);
        recyclerViewLocations.setAdapter(locationsAdapter);
    }

    private void setUpNavigationDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, ToursActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_dates_layout:
                Utils.startActivityWithClipReveal(MainActivity.this, CalendarActivity.class, v);
                break;
        }
    }
}
