package com.zeus.scan.app.utils;

import com.google.gson.Gson;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by lvzimou on 2016/11/28.
 */

public class APIUtil {

    private final static String API_BASE_URL = "http://127.0.0.1:12580/bookcase/";

    private final static String DOUBAN_BASE_URL = "https://api.douban.com/";

    public static Retrofit initService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        return retrofit;
    }

    public static Retrofit initDouBanService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DOUBAN_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        return retrofit;
    }
}
