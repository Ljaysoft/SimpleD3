package com.game.simpled3.engine.webservice;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by JFCaron on 2015-05-26.
 */
public final class D3ArmoryReader {
    private static RestAdapter restAdapter;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static final D3ArmoryReader sInstance = new D3ArmoryReader();
    private boolean sIsInit = false;
    private static final String END_POINT = "https://us.api.battle.net";
    private static final int HTTP_TIMEOUT = 5000;

    private D3ArmoryReader() {
        okHttpClient.setConnectTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(HTTP_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public static D3ArmoryReader getInstance() {
        return sInstance;
    }

    public static void initialize() {
        if (sInstance.sIsInit)
            return;




        sInstance.sIsInit = true;
    }

    public static RestAdapter getRestAdapter() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setRequestInterceptor(new RequestInterceptor())
                .build();

        return restAdapter;
    }

    private static class RequestInterceptor implements retrofit.RequestInterceptor {
        @Override
        public void intercept(RequestFacade request) {
            request.addEncodedQueryParam("apikey", "b8mwu9fbpxxy4zgc5x9sk3a3p6k8pzsj");
        }
    }
}

