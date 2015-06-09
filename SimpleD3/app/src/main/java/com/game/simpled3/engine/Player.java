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
    private static boolean sIsInit = false;

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

    //public static Player getInstance() {
    //    return sInstance;
    //}

    public static void initialize(Resources res) {
        if (sIsInit)
            return;
        int numberOfItemSlots = res.getInteger(R.integer.number_of_item_slots);
        sInstance.mItems = new ArrayList<>(numberOfItemSlots);
        for (int slot = 0; slot < numberOfItemSlots; slot++) {
            sInstance.mItems.add(slot, Item.getDummy());
        }
        sIsInit = true;
    }

    public static double getDEF() {
        return sInstance.mDEF;
    }

    public static double getDPS() {
        return sInstance.mDPS;
    }

    public static double getGold() {
        return sInstance.mGold;
    }

    public static int getLevel() {
        return sInstance.mLevel;
    }

    public static double getShards() {
        return sInstance.mShards;
    }

    public static double getXpToLevel() {
        return sInstance.mXpToLevel;
    }

    public static ArrayList<Item> getItems() {
        return sInstance.mItems;
    }

    public static boolean isDead() {
        return sInstance.isDead;
    }

    public static boolean isGearBroke() {
        return sInstance.isGearBroke;
    }

    public static void giveGold(double goldGiven) {
        sInstance.mGold += goldGiven;
    }

    public static void giveShards(double shardsGiven) {
        sInstance.mShards += shardsGiven;
    }

    public static void giveXP(float xpToGive, float[] xpToLvlArr) {
        double xpLeftToGive = xpToGive;
        boolean isDoneGivingXp = false;
        while (!isDoneGivingXp) {
            if (xpLeftToGive <= sInstance.mXpToLevel) {
                sInstance.mXpToLevel -= xpLeftToGive;
                isDoneGivingXp = true;
            } else {
                xpLeftToGive -= sInstance.mXpToLevel;
                sInstance.mLevel++;
                sInstance.mXpToLevel = xpToLvlArr[sInstance.mLevel];
            }
        }
    }

    public static void giveItem(Item item) {
        //TODO Player receives items
        @GameEnums.ItemSlot int iSlot = item.getSlot();
        if (iSlot != GameEnums.ITEM_SLOT_DUMMY) {
            sInstance.mItems.set(iSlot, item);
        }
    }

    public static boolean takeGold(double gold) {
        if (sInstance.mGold < gold)
            return false;
        else {
            sInstance.mGold -= gold;
            return true;
        }
    }

    public static boolean takeShards(double shards) {
        if (sInstance.mShards < shards)
            return false;
        else {
            sInstance.mShards -= shards;
            return true;
        }
    }

    public static void updateDPSandDEF() {
        double DPS = 1.0;
        double DEF = 1.0;
        for (Item item : sInstance.mItems) {
            DPS += item.getDPS();
            DEF += item.getDEF();
        }
        sInstance.mDPS = DPS;
        sInstance.mDEF = DEF;
    }

    private static void loseDurability(double durabilityLoss) {
        if (!sInstance.isGearBroke)
            sInstance.durability-=MAX_DURABILITY*durabilityLoss;
        if (sInstance.durability <= 0)
            sInstance.isGearBroke = true;
    }

    public static void kill() {
        sInstance.isDead = true;
        loseDurability(0.1);
    }

    public static void revive() {
        sInstance.isDead = false;
    }

}
