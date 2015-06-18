package com.game.simpled3.engine.gear;

import java.util.ArrayList;

/**
 * Created by JFCaron on 2015-05-30.
 */
public class ItemTypeNameList {
    private final ArrayList<String> mNames = new ArrayList<>();
    private String mSlotName;
    private int mSlotID;

    public ItemTypeNameList(String slotName) {
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
