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
<<<<<<< HEAD
    private static final Player sInstance = new Player();
=======
    private static final Player S_INSTANCE = new Player();
>>>>>>> origin/Alpha_0_1
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
<<<<<<< HEAD
    //    return sInstance;
=======
    //    return S_INSTANCE;
>>>>>>> origin/Alpha_0_1
    //}

    public static void initialize(Resources res) {
        if (sIsInit)
            return;
        int numberOfItemSlots = res.getInteger(R.integer.number_of_item_slots);
        S_INSTANCE.mItems = new ArrayList<>(numberOfItemSlots);
        for (int slot = 0; slot < numberOfItemSlots; slot++) {
            S_INSTANCE.mItems.add(slot, Item.getDummy());
        }
        sIsInit = true;
    }

    public static double getDEF() {
<<<<<<< HEAD
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
=======
        return S_INSTANCE.mDEF;
    }

    public static double getDPS() {
        return S_INSTANCE.mDPS;
    }

    public static double getGold() {
        return S_INSTANCE.mGold;
    }

    public static int getLevel() {
        return S_INSTANCE.mLevel;
    }

    public static double getShards() {
        return S_INSTANCE.mShards;
    }

    public static double getXpToLevel() {
        return S_INSTANCE.mXpToLevel;
    }

    public static ArrayList<Item> getItems() {
        return S_INSTANCE.mItems;
    }

    public static boolean isDead() {
        return S_INSTANCE.isDead;
    }

    public static boolean isGearBroke() {
        return S_INSTANCE.isGearBroke;
    }

    public static void giveGold(double goldGiven) {
        S_INSTANCE.mGold += goldGiven;
    }

    public static void giveShards(double shardsGiven) {
        S_INSTANCE.mShards += shardsGiven;
>>>>>>> origin/Alpha_0_1
    }

    public static void giveXP(float xpToGive, float[] xpToLvlArr) {
        double xpLeftToGive = xpToGive;
        boolean isDoneGivingXp = false;
        while (!isDoneGivingXp) {
<<<<<<< HEAD
            if (xpLeftToGive <= sInstance.mXpToLevel) {
                sInstance.mXpToLevel -= xpLeftToGive;
                isDoneGivingXp = true;
            } else {
                xpLeftToGive -= sInstance.mXpToLevel;
                sInstance.mLevel++;
                sInstance.mXpToLevel = xpToLvlArr[sInstance.mLevel];
=======
            if (xpLeftToGive <= S_INSTANCE.mXpToLevel) {
                S_INSTANCE.mXpToLevel -= xpLeftToGive;
                isDoneGivingXp = true;
            } else {
                xpLeftToGive -= S_INSTANCE.mXpToLevel;
                S_INSTANCE.mLevel++;
                S_INSTANCE.mXpToLevel = xpToLvlArr[S_INSTANCE.mLevel];
>>>>>>> origin/Alpha_0_1
            }
        }
    }

    public static void giveItem(Item item) {
        //TODO Player receives items
        @GameEnums.ItemSlot int iSlot = item.getSlot();
        if (iSlot != GameEnums.ITEM_SLOT_DUMMY) {
<<<<<<< HEAD
            sInstance.mItems.set(iSlot, item);
=======
            S_INSTANCE.mItems.set(iSlot, item);
>>>>>>> origin/Alpha_0_1
        }
    }

    public static boolean takeGold(double gold) {
<<<<<<< HEAD
        if (sInstance.mGold < gold)
            return false;
        else {
            sInstance.mGold -= gold;
=======
        if (S_INSTANCE.mGold < gold)
            return false;
        else {
            S_INSTANCE.mGold -= gold;
>>>>>>> origin/Alpha_0_1
            return true;
        }
    }

    public static boolean takeShards(double shards) {
<<<<<<< HEAD
        if (sInstance.mShards < shards)
            return false;
        else {
            sInstance.mShards -= shards;
=======
        if (S_INSTANCE.mShards < shards)
            return false;
        else {
            S_INSTANCE.mShards -= shards;
>>>>>>> origin/Alpha_0_1
            return true;
        }
    }

    public static void updateDPSandDEF() {
        double DPS = 1.0;
        double DEF = 1.0;
<<<<<<< HEAD
        for (Item item : sInstance.mItems) {
            DPS += item.getDPS();
            DEF += item.getDEF();
        }
        sInstance.mDPS = DPS;
        sInstance.mDEF = DEF;
    }

    private static void loseDurability(double durabilityLoss) {
        if (!sInstance.isGearBroke)
            sInstance.durability -= MAX_DURABILITY * durabilityLoss;
        if (sInstance.durability <= 0)
            sInstance.isGearBroke = true;
    }

    public static void kill() {
        sInstance.isDead = true;
=======
        for (Item item : S_INSTANCE.mItems) {
            DPS += item.getDPS();
            DEF += item.getDEF();
        }
        S_INSTANCE.mDPS = DPS;
        S_INSTANCE.mDEF = DEF;
    }

    private static void loseDurability(double durabilityLoss) {
        if (!S_INSTANCE.isGearBroke)
            S_INSTANCE.durability-=MAX_DURABILITY*durabilityLoss;
        if (S_INSTANCE.durability <= 0)
            S_INSTANCE.isGearBroke = true;
    }

    public static void kill() {
        S_INSTANCE.isDead = true;
>>>>>>> origin/Alpha_0_1
        loseDurability(0.1);
    }

    public static void revive() {
<<<<<<< HEAD
        sInstance.isDead = false;
=======
        S_INSTANCE.isDead = false;
>>>>>>> origin/Alpha_0_1
    }

}
