package com.game.simpled3.gameEntities;

import android.content.res.Resources;

import com.game.simpled3.R;
import com.game.simpled3.gameEntities.Gear.Item;

import java.util.ArrayList;

/**
 * Created by JFCaron on 2015-04-27.
 */
public class Player {
    private static Player sInstance;
    private static boolean sIsInit = false;

    private static int mLevel = 0;
    private static float mXpToLevel = 0;
    private static double mDPS = 1.0;
    private static double mDEF = 1.0;
    private static double mGold = 0;
    private static double mShards = 0;
    private static ArrayList<Item> mItems;

    private Player() {
    }

    public static Player getInstance() {
        if (sInstance == null) {
            sInstance = new Player();
        }
        return sInstance;
    }

    public static void initialize(Resources res) {
        if (sInstance == null && sIsInit)
            return;
        mItems = new ArrayList<>(res.getInteger(R.integer.number_of_item_slots));
        sIsInit = true;
    }

    public double getDEF() {
        return mDEF;
    }

    public double getDPS() {
        return mDPS;
    }

    public double getGold() {
        return mGold;
    }

    public int getLevel() {
        return mLevel;
    }

    public double getShards() {
        return mShards;
    }

    public float getXpToLevel() {
        return mXpToLevel;
    }

    public ArrayList<Item> getItems() {
        return mItems;
    }

    public void giveGold(double goldGiven) {
        mGold += goldGiven;
    }

    public void giveShards(double shardsGiven) {
        mShards += shardsGiven;
    }

    public void giveXP(float xpToGive, float[] xpToLvlArr) {
        double xpLeftToGive = xpToGive;
        boolean isDoneGivingXp = false;
        while (!isDoneGivingXp){
            if( xpLeftToGive <= mXpToLevel){
                mXpToLevel -= xpLeftToGive;
                isDoneGivingXp = true;
            } else {
                xpLeftToGive -= mXpToLevel;
                mLevel++;
                mXpToLevel = xpToLvlArr[mLevel];
            }
        }
    }

    public void giveItem(Item item) {
        //TODO Player receives items
        //@ItemSlot int iSlot = item.getSlot();
        //mItems.set(iSlot,item);
    }

    public boolean takeGold(double gold) {
        if (mGold < gold)
            return false;
        else {
            mGold -= gold;
            return true;
        }
    }

    public boolean takeShards(double shards) {
        if (mShards < shards)
            return false;
        else {
            mShards -= shards;
            return true;
        }
    }

    public boolean updateDPSandDEF() {
        double DPS = 0;
        double DEF = 0;
        for (Item item : mItems) {
            DPS+= item.getDPS();
            DEF+= item.getDEF();
        }
        return mDPS != DPS | DEF != mDEF;
    }

}
