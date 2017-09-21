package com.booking.android.hotel.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.booking.android.hotel.R;
import com.booking.android.hotel.R.id;
import com.booking.android.hotel.R.layout;
import com.booking.android.hotel.activity.HotelSrpActivity;
import com.booking.android.hotel.activity.HotelsActivity;
import com.booking.android.hotel.model.LocationItem;
import com.booking.android.hotel.utils.Utils;
import com.squareup.picasso.Picasso;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

/**
 * The type Locations adapter.
 */
@LayoutId(layout.item_location)
public class LocationsAdapter extends ItemViewHolder<LocationItem> implements OnClickListener {
    @ViewId(id.tv_title)
    private TextView tvTitle;
    @ViewId(id.tv_subtitle)
    private TextView tvSubtitle;
    @ViewId(id.iv_location)
    private ImageView ivLocation;
    @ViewId(id.location_layout)
    private RelativeLayout locationLayout;
    @ViewId(id.search_layout)
    private RelativeLayout searchLayout;
    @ViewId(id.filters_layout)
    private LinearLayout filtersLayout;


    private LocationItem locItem;

    /**
     * Instantiates a new Locations adapter.
     *
     * @param view the view
     */
    public LocationsAdapter(View view) {
        super(view);
    }

    @Override
    public void onSetValues(LocationItem locationItem, PositionInfo positionInfo) {
        locItem = locationItem;
        this.tvTitle.setText(locationItem.getFullName());
        this.tvSubtitle.setText(Utils.getSubTitle());

        //ivLocation.setImageResource(drawables4[positionInfo.getPosition()]);
        Picasso.with(getContext()).load(locationItem.getImageUrl()).fit().centerCrop().into(this.ivLocation);


        if (positionInfo.getPosition() == 20) {
            this.searchLayout.setVisibility(View.VISIBLE);
        } else {
            this.searchLayout.setVisibility(View.GONE);
        }

        locationLayout.setOnClickListener
                (new OnClickListener() {
                    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {

                        Pair<View, String> p1 = Pair.create((View) ivLocation, ivLocation.getTransitionName());
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation((Activity) getContext(), p1);
                        Bundle bundle = options.toBundle();
                        Intent i = new Intent(getContext(), HotelSrpActivity.class);
                        i.putExtra("locationItemid", locItem.getId());
                        getContext().startActivity(i, bundle);

                    }
    }

    );
}

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
        }
    }

}