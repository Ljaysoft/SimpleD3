package com.game.simpled3.gameEntities.Gear;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.game.simpled3.R;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.String.copyValueOf;

/**
 * Created by JFCaron on 2015-05-05.
 */
public class ItemFactory {
    private static boolean sIsInit = false;
    private static ItemFactory sInstance = null;
    private static Random mRNG = null;

    private static int mLvl = 0;

    static private int[] mItemPowerPerLvl = null;
    static private int mNbItemTypes = 0;
    static private int mNbItemColors = 0;
    static private float[] mItemPowerForItemType = null;
    static private float[] mPowerCoefForColor = null;

    static private String[] mGearNamesForGearType = null;
    static private String[] mGearPrefixForGearColor = null;
    static private String[] mGearSuffixes = null;

    private ItemFactory() {
    }

    public static ItemFactory getInstance() {
        if (sInstance == null) {
            sInstance = new ItemFactory();
        }
        return sInstance;
    }

    public static void initialize(Resources res) {
        if (sInstance == null && sIsInit)
            return;

        mRNG = new Random();

        mNbItemTypes = res.getInteger(R.integer.number_of_item_slots);
        mNbItemColors = res.getInteger(R.integer.number_of_item_colors);

        mItemPowerPerLvl = res.getIntArray(R.array.int_array_ipower_for_lvl);

        TypedArray resourceTypedArr = res.obtainTypedArray(R.array.float_array_ipower_for_type);
        mItemPowerForItemType = new float[mNbItemTypes];
        int lvl = 0;
        while (lvl < mNbItemTypes) {
            mItemPowerForItemType[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }
        resourceTypedArr.recycle();

        lvl = 0;
        resourceTypedArr = res.obtainTypedArray(R.array.float_array_ipower_coef_for_color);
        mPowerCoefForColor = new float[mNbItemColors];
        while (lvl < mNbItemColors) {
            mPowerCoefForColor[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }
        resourceTypedArr.recycle();

        mGearNamesForGearType = res.getStringArray(R.array.string_array_gear_name_for_type);
        mGearPrefixForGearColor = res.getStringArray(R.array.string_array_gear_prefix_for_color);
        mGearSuffixes = res.getStringArray(R.array.string_array_gear_suffixes);

        sIsInit = true;
    }

    public static Item createItem() {
        //TODO create Item


        return Item.createItem(mLvl);
    }

    public static ArrayList<Item> createItems(int nbOfItems) {
        ArrayList<Item> items = new ArrayList<>(nbOfItems);
        Item item;
        for (int i = 0; i < nbOfItems; i++) {
            item = createItem();
            items.add(item);
        }
        return items;
    }
}
