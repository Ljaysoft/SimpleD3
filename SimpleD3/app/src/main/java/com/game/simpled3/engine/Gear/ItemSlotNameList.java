package com.game.simpled3.engine.gear;

import java.util.ArrayList;

/**
 * Created by JFCaron on 2015-05-30.
 */
public class ItemSlotNameList {
    private String mSlotName;
    private int mSlotID;
    private final ArrayList<String> mNames = new ArrayList<>();

    public ItemSlotNameList(String slotName) {
        mSlotName = slotName;
    }

    public String getSlotName() {
        return mSlotName;
    }

    public int getSlotID() {
        return mSlotID;
    }

    public ArrayList<String> getAllNames() {
        return mNames;
    }

}
