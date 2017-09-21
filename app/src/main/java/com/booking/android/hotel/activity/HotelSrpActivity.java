package com.booking.android.hotel.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.booking.android.hotel.R;
import com.booking.android.hotel.helpers.RealmController;
import com.booking.android.hotel.httpclient.HotelsHttpClient;
import com.booking.android.hotel.utils.Utils;
import com.booking.android.hotel.adapter.FeaturedEventsAdapter;
import com.booking.android.hotel.model.LocationItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

public class HotelSrpActivity extends BaseActivity {
    private ImageView ivHeroImage;
    private TextView tvLocation;
    private LinkedList<LocationItem> locationsList;
    private EasyRecyclerAdapter<LocationItem> locationsAdapter;
    private RelativeLayout filtersLayout, headerLayout;
    private FloatingActionButton fabFilters;
    private boolean hidden = true;
    private Map<String, String> params = new HashMap<>();

    private LocationItem locationItem;

    @BindView(R.id.empty_layout)
    LinearLayout emptyLayout;

    @BindView(R.id.hotels_count)
    TextView hotelsCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_srp);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation();
        }
        fabFilters = (FloatingActionButton) findViewById(R.id.fab_filters);
        filtersLayout = (RelativeLayout) findViewById(R.id.filters_layout);
        headerLayout = (RelativeLayout) findViewById(R.id.header_layout);
        ivHeroImage = (ImageView) findViewById(R.id.iv_hero_image);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        int locationItemId= getIntent().getIntExtra("locationItemid", 0);

        locationItem = RealmController.with(this).getLocationItemByID(locationItemId);
        setValues();

        initUi();
    }

    private void setValues(){
        ivHeroImage.setImageResource(locationItem.getImageUrl());
        tvLocation.setText(locationItem.getFullName());
        hotelsCount.setText(locationItem.getHotelsCount()+" hotels in this city");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.change_bounds_with_arc);
        transition.setDuration(200);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }


    private void initUi() {
        initializeToolbar();

        RecyclerView recyclerViewLocations = (RecyclerView) findViewById(R.id.recycler_view_locations);
        recyclerViewLocations.setHasFixedSize(true);
        recyclerViewLocations.setNestedScrollingEnabled(false);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(
                this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewLocations.setLayoutManager(mLayoutManager);

        locationsList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            LocationItem locationItem = new LocationItem();
            locationsList.add(locationItem);
        }
        params.clear();

        String tomorrow = Utils.dateFormat.format(Utils.getTomorrow());
        String treeDayAgo = Utils.dateFormat.format(Utils.getTreeDayAgo());
        String iata = "HKT";
        String customerIP =Utils.getLocalIpAddress();
        String lang = "en_US";
        String currency = "USD";
        String waitForResult = "0";
        String adultsCount = "2";
        String childrenCount = "0";
        //http://engine.hotellook.com/api/v2/search/start.json?adultsCount=2&waitForResult=0&marker=123388&childrenCount=0&signature=4801b071448b2ede01f04f03045e5d0d&lang=en&customerIP=fe80%3A%3Ae49d%3Acfff%3Afe3d%3A2a63%25dummy0&checkIn=2017-07-21&currency=USD&cityId=5044&checkOut=2017-07-23

        params.put("iata", iata);
        params.put("checkIn", tomorrow);
        params.put("checkOut", treeDayAgo);
        params.put("adultsCount", adultsCount);
        params.put("customerIP", customerIP);
        params.put("childrenCount", childrenCount);
        params.put("lang", lang);
        params.put("currency", currency);
        params.put("waitForResult", waitForResult);

       // params.put("signature", Utils.MD5(params));
        params.put("marker", Utils.TRAVEL_PAYOUTS_MARKER);

        String md5 = Utils.MD5(Utils.generateSignatureGetHotels(adultsCount, tomorrow, treeDayAgo, childrenCount,  currency, customerIP, iata, lang, waitForResult));

        params.put("signature", md5);

        HotelsHttpClient.getApiService().getHotelsByLocation(params, new Callback<String>() {
            @Override
            public void success (String s, Response response) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String searchId = jsonObject.getString("searchId");
                    getHotels(searchId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.v("TTTTTT",s);
            }

            @Override
            public void failure (RetrofitError error) {
                Log.v("TTTTTT",error.toString());
            }
        });

        locationsAdapter = new EasyRecyclerAdapter<>(this, FeaturedEventsAdapter.class, locationsList);
        recyclerViewLocations.setAdapter(locationsAdapter);


        fabFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabRevealAnimation();
            }
        });

        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HotelSrpActivity.this, HotelDetailActivity.class));
            }
        });
    }

    private void getHotels(String searchId){
        Map<String, String> params = new HashMap<>();
        params.put("limit", "10");
        params.put("offset", "0");
        params.put("roomsCount", "1");
        params.put("searchId", searchId);
        params.put("sortAsc", "1");
        params.put("sortBy", "price");

        // params.put("signature", Utils.MD5(params));
        params.put("marker", Utils.TRAVEL_PAYOUTS_MARKER);

        String md5 = Utils.MD5(Utils.generateSignature("10", "0", "1",  searchId, "1","price"));

        params.put("signature", md5);


        HotelsHttpClient.getApiService().getHotel(params, new Callback<String>() {
            @Override
            public void success (String s, Response response) {
                Log.v("TTTTTT",s);
            }

            @Override
            public void failure (RetrofitError error) {
                Log.v("TTTTTT",error.toString());
            }
        });
    }

    private void initializeToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("");
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (hidden) {
            super.onBackPressed();
        } else {
            fabRevealAnimation();
        }
    }

    private void fabRevealAnimation() {
        int cx = fabFilters.getLeft() + 84;
        int cy = fabFilters.getBottom() - 84;
        int radius = Math.max(filtersLayout.getWidth(), filtersLayout.getHeight());
        if (hidden) {
            Animator anim = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                anim = android.view.ViewAnimationUtils.createCircularReveal(filtersLayout, cx, cy, 0, radius);
            }
            Utils.animationOut(fabFilters, R.anim.scale_down, 0, true, HotelSrpActivity.this);
            filtersLayout.setVisibility(View.VISIBLE);
            anim.start();
            hidden = false;
        } else {
            Animator anim = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                anim = android.view.ViewAnimationUtils.createCircularReveal(filtersLayout, cx, cy, radius, 0);
            }
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    Utils.animationIn(fabFilters, R.anim.scale_up, 0, HotelSrpActivity.this);
                    filtersLayout.setVisibility(View.INVISIBLE);
                    hidden = true;
                }
            });
            anim.start();
        }
    }
}
