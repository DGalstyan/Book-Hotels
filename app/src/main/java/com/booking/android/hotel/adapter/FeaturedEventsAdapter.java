package com.booking.android.hotel.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.booking.android.hotel.model.LocationItem;
import com.booking.android.hotel.R;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@LayoutId(R.layout.item_featured_event)
public class FeaturedEventsAdapter extends ItemViewHolder<LocationItem> implements View.OnClickListener {
    @ViewId(R.id.tv_title)
    private TextView tvTitle;
    @ViewId(R.id.tv_subtitle)
    private TextView tvSubtitle;
    @ViewId(R.id.iv_location)
    private ImageView ivLocation;
    @ViewId(R.id.location_layout)
    private RelativeLayout locationLayout;

    private String[] titlesArray = {"Hungarian Museum", "National Library", "San Fransisco", "New Delhi", "Paris"};
    private String[] subtitlesArray = {"Heart of England Tourism", "The City Of Dreams", "Heart of The Bay", "Heart of England Tourism", "Heart of England Tourism"};
    private int[] drawables = {R.drawable.img_building_1, R.drawable.img_building_2, R.drawable.img_city_8, R.drawable.img_city_6, R.drawable.img_city_7};

    private LocationItem locationItem;

    public FeaturedEventsAdapter(View view) {
        super(view);
    }

    @Override
    public void onSetValues(LocationItem locationItem, final PositionInfo positionInfo) {
        this.locationItem = locationItem;
        tvTitle.setText(titlesArray[positionInfo.getPosition()]);
        tvSubtitle.setText(subtitlesArray[positionInfo.getPosition()]);
        ivLocation.setImageResource(drawables[positionInfo.getPosition()]);
    }

    @Override
    public void onClick(View clickedView) {
        switch (clickedView.getId()) {
        }
    }

}