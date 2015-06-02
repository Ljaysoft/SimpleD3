package com.game.simpled3.engine.webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.game.simpled3.engine.gear.ItemSlotNameList;
import com.squareup.okhttp.OkHttpClient;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
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
    private static final String ARMORY_ADRESS = "http://us.battle.net/d3/en/item/";
    private static final byte HTTP_TIMEOUT = 6;

    private D3ArmoryReader() {
        okHttpClient.setConnectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
    }

    public static D3ArmoryReader getInstance() {
        return sInstance;
    }

    public static void initialize() {
        if (sInstance.sIsInit)
            return;

        sInstance.sIsInit = true;
    }

    //public static void set

    public static void requestItemsFromItemType(ItemSlotNameList itemType, ArmoryReaderCallback listener) {
        RequestItemNamesOfTypeAsyncTask reader = new RequestItemNamesOfTypeAsyncTask(listener);
        reader.execute(itemType);
    }


    public static RestAdapter getRestAdapter() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setRequestInterceptor(new RequestInterceptor())
                .setClient(new OkClient(okHttpClient))
                .build();

        return restAdapter;
    }

    private static class RequestInterceptor implements retrofit.RequestInterceptor {
        @Override
        public void intercept(RequestFacade request) {
            request.addEncodedQueryParam("apikey", "b8mwu9fbpxxy4zgc5x9sk3a3p6k8pzsj");
        }
    }

    private static class RequestItemNamesOfTypeAsyncTask extends AsyncTask<ItemSlotNameList, Void, ArrayList<ItemSlotNameList>> {
        private ArmoryReaderCallback mListener;

        public RequestItemNamesOfTypeAsyncTask(ArmoryReaderCallback listener) {
            mListener = listener;
        }

        @Override
        protected ArrayList<ItemSlotNameList> doInBackground(ItemSlotNameList... itemTypes) {
            ArrayList<ItemSlotNameList> namesForSlots = new ArrayList<>(itemTypes.length);
            for (ItemSlotNameList itemType : itemTypes) {
                itemType.getAllNames().addAll(getNamesFromHtmlBody(requestHtmlBodyForItemType(itemType.getSlotName())));
                namesForSlots.add(itemType);
            }
            //TODO savoir quand tout est load
            return namesForSlots;
        }

        private ArrayList<String> getNamesFromHtmlBody(Document body) {
            ArrayList<String> items = new ArrayList<>();
            if (body != null) {
                Elements itemIcons = body.getElementsByClass("item-details-icon");
                for (Element element : itemIcons) {
                    for (Element item : element.getElementsByAttributeValueMatching("href", "\\/d3\\/en\\/item\\/")) {
                        items.add(item.attr("href").substring(12));
                    }
                }
            }
            return items;
        }

        private Document requestHtmlBodyForItemType(String itemType) {
            Document htmlBody = null;
            try {
                htmlBody = Jsoup.connect(ARMORY_ADRESS + itemType + "/").get();
            } catch (Exception e) {
                Log.e(D3ArmoryReader.class.getSimpleName(),e.toString());
                e.printStackTrace();
            }
            return htmlBody;
        }

        @Override
        protected void onPostExecute(ArrayList<ItemSlotNameList> itemNamesForSlot) {
            mListener.onFetchNamesForSlotsDone(itemNamesForSlot);
        }
    }

    public interface ArmoryReaderCallback {
        void onFetchNamesForSlotsDone(ArrayList<ItemSlotNameList> slotsNames);
    }
}

