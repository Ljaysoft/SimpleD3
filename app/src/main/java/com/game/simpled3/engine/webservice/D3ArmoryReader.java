package com.game.simpled3.engine.webservice;

import android.os.AsyncTask;
import android.util.Log;

import com.game.simpled3.engine.gear.ItemTypeNameList;
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
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    private static final D3ArmoryReader S_INSTANCE = new D3ArmoryReader();

    private static boolean sIsInit = false;
    private static final String END_POINT = "https://us.api.battle.net";
    private static final String ARMORY_ADRESS = "http://us.battle.net/d3/en/item/";
    private static final byte HTTP_TIMEOUT = 6;

    private D3ArmoryReader() {
        OK_HTTP_CLIENT.setConnectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
        OK_HTTP_CLIENT.setWriteTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
        OK_HTTP_CLIENT.setReadTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS);
    }

    public static D3ArmoryReader getInstance() {
        return S_INSTANCE;
    }

    public static void initialize() {
        if (sIsInit)
            return;

        sIsInit = true;
    }

    //public static void set

    public static void requestItemsFromItemType(ItemTypeNameList itemType, ArmoryReaderCallback listener) {
        RequestItemNamesOfTypeAsyncTask reader = new RequestItemNamesOfTypeAsyncTask(listener);
        reader.execute(itemType);
    }


    public static RestAdapter getRestAdapter() {
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(END_POINT)
                .setRequestInterceptor(new RequestInterceptor())
                .setClient(new OkClient(OK_HTTP_CLIENT))
                .build();

        return restAdapter;
    }

    private static class RequestInterceptor implements retrofit.RequestInterceptor {
        @Override
        public void intercept(RequestFacade request) {
            request.addEncodedQueryParam("apikey", "b8mwu9fbpxxy4zgc5x9sk3a3p6k8pzsj");
        }
    }

    private static class RequestItemNamesOfTypeAsyncTask extends AsyncTask<ItemTypeNameList, Void, ArrayList<ItemTypeNameList>> {
        private ArmoryReaderCallback mListener;

        public RequestItemNamesOfTypeAsyncTask(ArmoryReaderCallback listener) {
            mListener = listener;
        }

        @Override
        protected ArrayList<ItemTypeNameList> doInBackground(ItemTypeNameList... itemTypes) {
            ArrayList<ItemTypeNameList> namesForSlots = new ArrayList<>(itemTypes.length);
            for (ItemTypeNameList itemType : itemTypes) {
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
        protected void onPostExecute(ArrayList<ItemTypeNameList> itemNamesForSlot) {
            mListener.onFetchNamesForSlotsDone(itemNamesForSlot);
        }
    }

    public interface ArmoryReaderCallback {
        void onFetchNamesForSlotsDone(ArrayList<ItemTypeNameList> slotsNames);
    }
}

