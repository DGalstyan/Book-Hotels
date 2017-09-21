package com.booking.android.hotel.utils;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;
import com.booking.android.hotel.R;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;


/**
 * The type Utils.
 */
public class Utils {
    public static final String TAG = "Utils";

    public static final String LIVE_URL = "http://engine.hotellook.com/api/v2/";
    public final static String TRAVEL_PAYOUTS_MARKER = "123388";
    public final static String TRAVEL_PAYOUTS_TOKEN = "874be8fc11f563d94f9afa02ccdfb711";
    public static DateFormat formatDay = new SimpleDateFormat("dd", Locale.UK);
    public static DateFormat format = new SimpleDateFormat("dd MMM.", Locale.UK);

    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

    /**
     * Load gif image with ion.
     *
     * @param imageView the image view
     * @param imageUrl  the image url
     */
    public static void loadGifImageWithIon(ImageView imageView, String imageUrl) {
        Ion.with(imageView)
                .animateLoad(R.anim.abc_fade_in)
                .animateIn(R.anim.abc_fade_in)
                .load("file:///android_asset/" + imageUrl + ".gif");
    }

    /**
     * Animation in.
     *
     * @param view      the view
     * @param animation the animation
     * @param delayTime the delay time
     * @param context   the context
     */
    public static void animationIn(final View view, final int animation, int delayTime, final Context context) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Animation inAnimation = AnimationUtils.loadAnimation(
                        context.getApplicationContext(), animation);
                view.setAnimation(inAnimation);
                view.setVisibility(View.VISIBLE);
            }
        }, delayTime);
    }

    /**
     * Animation out.
     *
     * @param view       the view
     * @param animation  the animation
     * @param delayTime  the delay time
     * @param isViewGone the is view gone
     * @param context    the context
     */
    public static void animationOut(final View view, final int animation, int delayTime, final boolean isViewGone, final Context context) {
        view.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Animation outAnimation = AnimationUtils.loadAnimation(
                        context.getApplicationContext(), animation);
                view.setAnimation(outAnimation);
                if (isViewGone)
                    view.setVisibility(View.GONE);
                else
                    view.setVisibility(View.INVISIBLE);
            }
        }, delayTime);
    }

    /**
     * Start activity with clip reveal.
     *
     * @param context         the context
     * @param landingActivity the landing activity
     * @param view            the view
     */
    public static void startActivityWithClipReveal(Context context, Class landingActivity, View view) {
        Intent intent = new Intent(context, landingActivity);
        ActivityOptions options = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            options = ActivityOptions.makeClipRevealAnimation(view, 0, 0,
                    view.getWidth(), view.getHeight());
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }
    }


    public static Date getTomorrow(){
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
        return tomorrow;
    }

    public static Date getTreeDayAgo(){
        Date today = new Date();
        int oneDay = 1000 * 60 * 60 * 24;
        Date treeDayAgo = new Date(today.getTime() + (3 * oneDay));
        return treeDayAgo;
    }

    public static String getSubTitle(){
        return formatDay.format(getTomorrow()) + " - "+format.format(getTreeDayAgo())+" • 2 adults";
    }


    public static String MD5(Map<String, String> params) {
        try {
            String md5 = generateSignature(params);
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    private static String generateSignature(Map<String, String> params){
        String result = TRAVEL_PAYOUTS_TOKEN+":"+TRAVEL_PAYOUTS_MARKER;
        for (Map.Entry<String, String> entry : params.entrySet())
        {
            result+=":"+entry.getValue();
        }
        Log.v("TTTTTT",result);
        return result;
    }


    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String generateSignature(String limit, String offset, String roomsCount, String searchId, String sortAsc,String sortBy){
        String result = TRAVEL_PAYOUTS_TOKEN+":"+TRAVEL_PAYOUTS_MARKER+":"+limit+":"+offset+":"+roomsCount+":"+searchId+":"+sortAsc+":"+sortBy;
//        for (Map.Entry<String, String> entry : params.entrySet())
//        {
//            result+=":"+entry.getValue();
//        }
        Log.v("TTTTTT",result);
        return result;
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.i(TAG, "***** IP="+ ip);
                        return ip;
                    }
                }
            }
        } catch (SocketException ex) {
            Log.e(TAG, ex.toString());
        }
        return null;
    }

    public static String generateSignatureGetHotels(String adultsCount, String checkIn, String checkOut, String childrenCount, String currency,String customerIP, String iata,String lang,String waitForResult){

        // "YourToken:YourMarker:adultsCount:checkIn:checkOut:childrenCount:currency:customerIP:iata:lang:waitForResult"

        Log.v("TTTTTT","TRAVEL_PAYOUTS_TOKEN+\":\"+TRAVEL_PAYOUTS_MARKER+\":\"+adultsCount+\":\"+checkIn+\":\"+checkOut+\":\"+childrenCount+\":\"+currency+\":\"+customerIP+\":\"+iata+\":\"+lang+\":\"+waitForResult");
        String result = TRAVEL_PAYOUTS_TOKEN+":"+TRAVEL_PAYOUTS_MARKER+":"+adultsCount+":"+checkIn+":"+checkOut+":"+childrenCount+":"+currency+":"+customerIP+":"+iata+":"+lang+":"+waitForResult;
//        for (Map.Entry<String, String> entry : params.entrySet())
//        {
//            result+=":"+entry.getValue();
//        }
        Log.v("TTTTTT",result);
        return result;
    }

}
