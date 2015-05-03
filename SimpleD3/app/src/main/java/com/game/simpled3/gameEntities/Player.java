package com.game.simpled3.gameEntities;

import android.content.res.Resources;

import com.game.simpled3.gameEntities.Enums.GameEnums.ItemSlot;

import java.util.ArrayList;

/**
 * Created by JFCaron on 2015-04-27.
 */
public class Player {
    private int mLevel = 0;
    private float mXpToLevel = 0;
    private double mDPS = 1.0;
    private double mDEF = 1.0;
    private double mGold = 0;
    private double mShards = 0;
    private ArrayList<Item> mItems;
    private Player(Resources res) {
        initialize(res);
    }

    private void initialize(Resources res) {
        mItems = new ArrayList<>(12);
    }

    public static Player createPlayer(Resources res) {
        return new Player(res);
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
        @ItemSlot int iSlot = item.getSlot();
        mItems.set(iSlot,item);
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
