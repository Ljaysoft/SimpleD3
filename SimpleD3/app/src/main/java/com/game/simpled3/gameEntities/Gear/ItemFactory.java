package com.game.simpled3.gameEntities.Gear;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.game.simpled3.R;
import com.game.simpled3.Utils.StdRandom;
import com.game.simpled3.gameEntities.Gear.Slots.*;

import java.util.ArrayList;

import static com.game.simpled3.gameEntities.Enums.GameEnums.*;

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

    public static Item BuildNewItem() {
        Item rItem = createItem();

        int color = buildItemColor();
        String name = buildItemName(rItem);
        double dps = buildItemDPS(rItem);
        double def = buildItemDEF(rItem);

        rItem.setStats(name, dps, def, color);
        return rItem;
    }

    public static ArrayList<Item> BuildNewItems(int nbOfItems) {
        ArrayList<Item> items = new ArrayList<>(nbOfItems);
        Item item;
        for (int i = 0; i < nbOfItems; i++) {
            item = BuildNewItem();
            items.add(item);
        }
        return items;
    }

    private static Item createItem() {
        double p = StdRandom.uniform();
        int slot = (int) (p * ((double) mNbItemTypes - 1));
        switch (slot) {
            case ITEM_SLOT_HELM:
                return new Helmet(mLvl);
            case ITEM_SLOT_SHOULDER:
                return new Shoulders(mLvl);
            case ITEM_SLOT_CHEST:
                return new Chestpiece(mLvl);
            case ITEM_SLOT_NECK:
                return new Neck(mLvl);
            case ITEM_SLOT_GLOVE:
                return new Gloves(mLvl);
            case ITEM_SLOT_BRACER:
                return new Bracers(mLvl);
            case ITEM_SLOT_BELT:
                return new Belt(mLvl);
            case ITEM_SLOT_PANTS:
                return new Pants(mLvl);
            case ITEM_SLOT_BOOTS:
                return new Boots(mLvl);
            case ITEM_SLOT_RING1:
                return new Ring(mLvl, false);
            case ITEM_SLOT_RING2:
                return new Ring(mLvl, true);
            case ITEM_SLOT_LEFT_WEAPON:
                return new Weapon(mLvl, false);
            case ITEM_SLOT_RIGHT_WEAPON:
                return new Weapon(mLvl, true);
        }
        return null;
    }

    private static int buildItemColor() {
        int lambda = 1;
        double p = StdRandom.exp(lambda);
        int color = (int) (p * ((double) mNbItemColors - 1));
        switch (color) {
            case ITEM_COLOR_WHITE:
                return ITEM_COLOR_WHITE;
            case ITEM_COLOR_BLUE:
                return ITEM_COLOR_BLUE;
            case ITEM_COLOR_YELLOW:
                return ITEM_COLOR_YELLOW;
            case ITEM_COLOR_ORANGE:
                return ITEM_COLOR_ORANGE;
            default:
                break;
        }

        return ITEM_COLOR_GRAY;
    }

    private static String buildItemName(Item item) {
        //TODO build name


        return new String();
    }

    private static double buildItemDPS(Item item) {
        return 0.0;
    }

    private static double buildItemDEF(Item item) {
        return 0.0;
    }
}
