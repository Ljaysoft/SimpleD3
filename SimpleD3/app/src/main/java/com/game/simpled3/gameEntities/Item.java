package com.game.simpled3.gameEntities;

import android.content.res.Resources;

import static com.game.simpled3.gameEntities.Enums.GameEnums.*;

/**
 * Created by JFCaron on 2015-04-27.
 */
public class Item {
    private int mIlvl = 0;
    private String mName = "";
    @ItemColor
    private int mColor = ITEM_COLOR_GRAY;
    @ItemSlot
    private int mSlot = ITEM_SLOT_HELM;
    private double mDPS = 0.0;
    private double mDEF = 0.0;
    //private bitmap mImage = null;

    public boolean generateStats(Resources res) {
        return true;
    }

    public int getIlvl() {
        return mIlvl;
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
}