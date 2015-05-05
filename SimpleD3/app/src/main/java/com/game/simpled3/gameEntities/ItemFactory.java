package com.game.simpled3.gameEntities;

import android.content.res.Resources;

import java.util.ArrayList;

/**
 * Created by JFCaron on 2015-05-05.
 */
public class ItemFactory {
    private static ItemFactory sInstance;
    private static boolean sIsInit = false;

    private static int lvl = 0;

    private ItemFactory(){}

    public static ItemFactory getInstance(){
        if (sInstance == null) {
            sInstance = new ItemFactory();
        }
        return sInstance;
    }

    public static void initialize(Resources res) {
        if (sInstance == null && sIsInit == true)
            return;

        //TODO init factory
        sIsInit = true;
    }

    public static Item createItem(){
        return Item.createItem(lvl);
    }

    public static ArrayList<Item> createItems(int nbOfItems) {
        ArrayList<Item> items = new ArrayList<>(nbOfItems);
        Item item;
        for (int i = 0; i < nbOfItems; i++) {
            item = Item.createItem(lvl);
            items.add(item);
        }
        return items;
    }
}
