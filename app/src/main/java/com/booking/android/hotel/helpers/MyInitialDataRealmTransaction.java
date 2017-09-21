package com.booking.android.hotel.helpers;

import android.util.Log;

import com.booking.android.hotel.R;
import com.booking.android.hotel.httpclient.HotelsHttpClient;
import com.booking.android.hotel.model.LocationItem;
import com.booking.android.hotel.utils.HotelsApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;

/**
 * Created by dgalstya on 14.06.2017.
 */

public class MyInitialDataRealmTransaction
        implements Realm.Transaction {
    private int[] drawables = {R.drawable.img_city_2, R.drawable.img_city_15,
            R.drawable.img_city_18, R.drawable.img_city_13, R.drawable.img_city_9,
            R.drawable.img_city_10, R.drawable.img_city_8};
    @Override
    public void execute(Realm realm) {
        realm.delete(LocationItem.class);
        createObjects(realm);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = HotelsApplication.context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void createObjects(Realm realm){
        GsonBuilder builder = new GsonBuilder()
                .serializeNulls();
        Gson baseGson = builder.create();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray array = obj.getJSONArray("locations");
            for (int i = 0; i < array.length(); i++) {
                JSONObject locationItemJson = array.getJSONObject(i);
                Log.v("TTTTT", HotelsHttpClient.getDefaultBuilder()+" dddddd "+locationItemJson.toString());
                LocationItem locationItem = baseGson.fromJson(locationItemJson.toString(), LocationItem.class);
                locationItem.setImageUrl(drawables[i]);
                Log.v("TTTTT", "dddddd "+locationItem.getCountryName());
                realm.copyToRealmOrUpdate(locationItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
