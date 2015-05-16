package com.game.simpled3.engine.gear;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GRAY;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_ANY;
import static com.game.simpled3.engine.enums.GameEnums.ItemColor;
import static com.game.simpled3.engine.enums.GameEnums.ItemSlot;

/**
 * Created by JFCaron on 2015-04-27.
 */
public class Item {
    @ItemSlot
    protected int mSlot = ITEM_SLOT_ANY;
    private int mILvl = 0;
    private String mName = "";
    @ItemColor
    private int mColor = ITEM_COLOR_GRAY;
    private double mDPS = 0.0;
    private double mDEF = 0.0;

    private boolean mIsDiscovered = false;

    //private bitmap mImage = null;

    protected Item(int lvl) {
        mILvl = lvl;
    }

    public static Item createItem(int lvl) {
        return new Item(lvl);
    }

    public int getILvl() {
        return mILvl;
    }

    public String getName() {
        return mName;
    }

    public int getColor() {
        return mColor;
    }

    public int getSlot() {
        return mSlot;
    }

    public double getDPS() {
        return mDPS;
    }

    public double getDEF() {
        return mDEF;
    }

    public void setStats(String name, double dps, double def, int color) {
        if (mIsDiscovered)
            return;

        mName = name;
        mDPS = dps;
        mDEF = def;
        mColor = color;

        mIsDiscovered = true;
    }
}