package com.game.simpled3.engine.gear;

import java.util.ArrayList;

/**
 * Created by JFCaron on 2015-05-02.
 */
public class Loot {
    private double mGold, mShards;
    private ArrayList<Item> mItems;

    public Loot(double gold, double shards, ArrayList<Item> items) {
        mGold = gold;
        mItems = items;
        mShards = shards;
    }

    public double getGold() {
        return mGold;
    }

    public double getShards() {
        return mShards;
    }

    public ArrayList<Item> getItems() {
        return mItems;
    }
}
