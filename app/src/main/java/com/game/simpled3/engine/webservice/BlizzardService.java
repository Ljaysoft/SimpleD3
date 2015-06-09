package com.game.simpled3.engine.webservice;

import com.game.simpled3.engine.webservice.models.FullItem;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by JFCaron on 2015-05-27.
 */
public interface BlizzardService {

    @GET("/d3/data/item/{itemId}")
    public void getItem(@Path("itemId") String itemId, Callback<FullItem> callback);
}
