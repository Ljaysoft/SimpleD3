package com.game.simpled3.engine;

import android.content.res.Resources;

import com.game.simpled3.R;
import com.game.simpled3.engine.enums.GameEnums;
import com.game.simpled3.engine.gear.Item;

import java.util.ArrayList;

/**
 * Created by JFCaron on 2015-04-27.
 */
public class Player {
    private static final Player sInstance = new Player();
    private boolean sIsInit = false;

    private int mLevel = 0;
    private double mXpToLevel = 0;
    private double mDPS = 1.0;
    private double mDEF = 1.0;
    private double mGold = 0;
    private double mShards = 0;
    private ArrayList<Item> mItems;
    private boolean isDead = false;
    private boolean isGearBroke = false;
    private byte durability = 100;
    private static final byte MAX_DURABILITY = 100;

    private Player() {
    }

    public static Player getInstance() {
        return sInstance;
    }

    public static void initialize(Resources res) {
        if (sInstance.sIsInit)
            return;
        int numberOfItemSlots = res.getInteger(R.integer.number_of_item_slots);
        sInstance.mItems = new ArrayList<>(numberOfItemSlots);
        for (int slot = 0; slot < numberOfItemSlots; slot++) {
            sInstance.mItems.add(slot, Item.getDummy());
        }
        sInstance.sIsInit = true;
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

    public double getXpToLevel() {
        return mXpToLevel;
    }

    public ArrayList<Item> getItems() {
        return mItems;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isGearBroke() {
        return isGearBroke;
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
        while (!isDoneGivingXp) {
            if (xpLeftToGive <= mXpToLevel) {
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
        @GameEnums.ItemSlot int iSlot = item.getSlot();
        if (iSlot != GameEnums.ITEM_SLOT_DUMMY) {
            mItems.set(iSlot, item);
        }
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
        double DPS = 1.0;
        double DEF = 1.0;
        for (Item item : mItems) {
            DPS += item.getDPS();
            DEF += item.getDEF();
        }
        mDPS = DPS;
        mDEF = DEF;
        return mDPS != DPS || DEF != mDEF;
    }

    public void loseDurability(double durabilityLoss) {
        if (!isGearBroke)
            durability-=MAX_DURABILITY*durabilityLoss;
        if (durability <= 0)
            isGearBroke = true;
    }

    public void kill() {
        isDead = true;
        loseDurability(0.1);
    }

    public void revive() {
        isDead = false;
    }

}
