package com.game.simpled3.gameEntities.Gear;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.game.simpled3.R;

import java.util.ArrayList;

import static com.game.simpled3.gameEntities.Enums.GameEnums.ITEM_COLOR_GRAY;

/**
 * Created by JFCaron on 2015-05-05.
 */
public class ItemFactory {
    private static boolean sIsInit = false;
    private static ItemFactory sInstance = null;

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
        Item rItem = Item.createItem(mLvl);
        String name = buildName();
        double dps = buildDPS();
        double def = buildDEF();
        int color = ITEM_COLOR_GRAY;


        rItem.setStats(name,dps,def,color);
        return rItem;
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

    private static String buildName() {
        //TODO build name


        return new String();
    }

    private static double buildDPS() {
        return 0.0;
    }

    private static double buildDEF() {
        return 0.0;
    }
}
