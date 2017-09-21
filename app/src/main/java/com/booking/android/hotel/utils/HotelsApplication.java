package com.booking.android.hotel.utils;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.booking.android.hotel.R;

import com.booking.android.hotel.helpers.MyInitialDataRealmTransaction;
import com.crashlytics.android.Crashlytics;
import com.facebook.appevents.AppEventsLogger;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * The type Hotels application.
 */
public class HotelsApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
        Realm.init(this);
        context = getApplicationContext();
        initializeCalligraphy();
    }

    private void initializeCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/open_sans_medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("myrealm.realm")
                .deleteRealmIfMigrationNeeded()
                .initialData(new MyInitialDataRealmTransaction())
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @Override
    protected void attachBaseContext (Context base) {
        super.attachBaseContext(base);
    }
}
