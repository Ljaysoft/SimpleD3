package com.game.simpled3.engine.gear;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GRAY;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_DUMMY;
import static com.game.simpled3.engine.enums.GameEnums.ItemColor;
import static com.game.simpled3.engine.enums.GameEnums.ItemSlot;

/**
 * Created by JFCaron on 2015-04-27.
 */
public class Item {
    @ItemSlot
    protected int mSlot = ITEM_SLOT_DUMMY;
    private int mILvl = 0;
    private String mName = "no_name";
    @ItemColor
    private int mColor = ITEM_COLOR_GRAY;

    private String mFlavorText = "";
    private double mDPS = 0.0;
    private double mDEF = 0.0;

    private boolean mIsDiscovered = false;

    private String mImageID = "";
    protected boolean mIsIconSquare = false;

    protected Item(int lvl) {
        mILvl = lvl;
    }

    public static Item getDummy() {
        return new Item(0);
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

    public String getFlavorText() {
        return mFlavorText;
    }

    public void setFlavorText(String flavorText) {
        this.mFlavorText = flavorText;
    }

    public double getDPS() {
        return mDPS;
    }

    public double getDEF() {
        return mDEF;
    }

    public boolean isDiscovered() {
        return mIsDiscovered;
    }

    public void setImageID(String imageID) {
        mImageID = imageID;
    }

    public String getImageID() {
        return mImageID;
    }

    public boolean isIconSquare() {
        return mIsIconSquare;
    }

    public void setStats(double dps, double def) {
        if (mIsDiscovered)
            return;
        mDPS = dps;
        mDEF = def;

        mIsDiscovered = true;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setColor(int color) {
        mColor = color;
    }
}