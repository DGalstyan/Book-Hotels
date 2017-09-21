package com.booking.android.hotel.httpclient;

import com.booking.android.hotel.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Davit Galstyan on 7/18/17.
 */
public class HotelsHttpClient <T> {

    private static Gson baseGson;
    private static GsonBuilder builder;
    private final T mApiService;

    /**
     * Instantiates a new Rentecarlo http client.
     *
     * @param apiService the api service
     * @param targetLink the target link
     */
    private HotelsHttpClient(Class<T> apiService, String targetLink) {

        builder = new GsonBuilder()
                .serializeNulls();
        baseGson = builder.create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(targetLink)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new EntityEscapedStringConverter(baseGson))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade
                                                  request) {

                    }
                })
                .build();
        mApiService = restAdapter.create(apiService);

    }


    /**
     * Gets api service.
     *
     * @return the api service
     */
    public static HotelsApi getApiService() {
        return new HotelsHttpClient<>(HotelsApi.class, Utils.LIVE_URL).mApiService;
    }

    public static GsonBuilder getDefaultBuilder() {
        return builder;
    }

    public static Gson getGson() {
        return baseGson;
    }
}
