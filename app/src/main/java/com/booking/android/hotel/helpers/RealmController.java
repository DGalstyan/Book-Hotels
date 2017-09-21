package com.booking.android.hotel.helpers;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import com.booking.android.hotel.model.LocationItem;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by dgalstya on 14.06.2017.
 */

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {
        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {
        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }


    //clear all objects from Book.class
    public void clearAll() {
        realm.beginTransaction();
        realm.delete(LocationItem.class);
        realm.commitTransaction();
    }

    public RealmResults<LocationItem> getLocationItems() {
        return realm.where(LocationItem.class).findAll();
    }

    public LocationItem getLocationItemByID(int id) {
        return realm.where(LocationItem.class).equalTo("id", id).findFirst();
    }
}
