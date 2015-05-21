package com.game.simpled3.engine.gear;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.game.simpled3.R;
import com.game.simpled3.engine.gear.slots.Belt;
import com.game.simpled3.engine.gear.slots.Boots;
import com.game.simpled3.engine.gear.slots.Bracers;
import com.game.simpled3.engine.gear.slots.Chestpiece;
import com.game.simpled3.engine.gear.slots.Gloves;
import com.game.simpled3.engine.gear.slots.Helmet;
import com.game.simpled3.engine.gear.slots.Neck;
import com.game.simpled3.engine.gear.slots.Pants;
import com.game.simpled3.engine.gear.slots.Ring;
import com.game.simpled3.engine.gear.slots.Shoulders;
import com.game.simpled3.engine.gear.slots.Weapon;
import com.game.simpled3.utils.StdRandom;

import java.util.ArrayList;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_BLUE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GRAY;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_ORANGE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_WHITE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_YELLOW;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_BELT;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_BOOTS;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_BRACER;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_CHEST;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_GLOVE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_HELM;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_LEFT_WEAPON;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_NECK;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_PANTS;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_RIGHT_WEAPON;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_RING1;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_RING2;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_SHOULDER;

/**
 * Created by JFCaron on 2015-05-05.
 */
public class ItemFactory {
    private static boolean sIsInit = false;
    private static final ItemFactory sInstance = new ItemFactory();

    private int mLvl = 0;

    private int[] mItemPowerPerLvl = null;
    private int mNbItemTypes = 0;
    private int mNbItemColors = 0;
    private float[] mItemPowerForItemType = null;
    private float[] mPowerCoefForColor = null;

    private String[] mGearNamesForGearType = null;
    private String[] mGearPrefixForGearColor = null;
    private String[] mGearSuffixes = null;

    protected ItemFactory() {
    }

    public static ItemFactory getInstance() {
        return sInstance;
    }

    public static void initialize(Resources res) {
        if (sInstance == null || sIsInit)
            return;

        sInstance.mNbItemTypes = res.getInteger(R.integer.number_of_item_slots);
        sInstance.mNbItemColors = res.getInteger(R.integer.number_of_item_colors);

        sInstance.mItemPowerPerLvl = res.getIntArray(R.array.int_array_ipower_for_lvl);

        TypedArray resourceTypedArr = res.obtainTypedArray(R.array.float_array_ipower_for_type);
        sInstance.mItemPowerForItemType = new float[sInstance.mNbItemTypes];
        int lvl = 0;
        while (lvl < sInstance.mNbItemTypes) {
            sInstance.mItemPowerForItemType[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }
        resourceTypedArr.recycle();

        lvl = 0;
        resourceTypedArr = res.obtainTypedArray(R.array.float_array_ipower_coef_for_color);
        sInstance.mPowerCoefForColor = new float[sInstance.mNbItemColors];
        while (lvl < sInstance.mNbItemColors) {
            sInstance.mPowerCoefForColor[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }
        resourceTypedArr.recycle();

        sInstance.mGearNamesForGearType = res.getStringArray(R.array.string_array_gear_name_for_type);
        sInstance.mGearPrefixForGearColor = res.getStringArray(R.array.string_array_gear_prefix_for_color);
        sInstance.mGearSuffixes = res.getStringArray(R.array.string_array_gear_suffixes);

        sIsInit = true;
    }

    public static Item BuildNewItem() {
        Item rItem = createItem();

        int color = buildItemColor();
        String name = buildItemName(rItem);
        double dps = buildItemDPS(rItem);
        double def = buildItemDEF(rItem);

        if (rItem != null)
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
        int slot = (int) (p * ((double) sInstance.mNbItemTypes)) + 1;
        switch (slot) {
            case ITEM_SLOT_HELM:
                return new Helmet(sInstance.mLvl);
            case ITEM_SLOT_SHOULDER:
                return new Shoulders(sInstance.mLvl);
            case ITEM_SLOT_CHEST:
                return new Chestpiece(sInstance.mLvl);
            case ITEM_SLOT_NECK:
                return new Neck(sInstance.mLvl);
            case ITEM_SLOT_GLOVE:
                return new Gloves(sInstance.mLvl);
            case ITEM_SLOT_BRACER:
                return new Bracers(sInstance.mLvl);
            case ITEM_SLOT_BELT:
                return new Belt(sInstance.mLvl);
            case ITEM_SLOT_PANTS:
                return new Pants(sInstance.mLvl);
            case ITEM_SLOT_BOOTS:
                return new Boots(sInstance.mLvl);
            case ITEM_SLOT_RING1:
                return new Ring(sInstance.mLvl, false);
            case ITEM_SLOT_RING2:
                return new Ring(sInstance.mLvl, true);
            case ITEM_SLOT_LEFT_WEAPON:
                return new Weapon(sInstance.mLvl, false);
            case ITEM_SLOT_RIGHT_WEAPON:
                return new Weapon(sInstance.mLvl, true);
        }
        return null;
    }

    private static int buildItemColor() {
        int lambda = 1;
        double p = StdRandom.exp(lambda);
        int color = (int) (p * ((double) sInstance.mNbItemColors - 1));
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
        String prefix = "";
        String name = "";
        String suffix = "";

        return prefix + " " + name + " " + suffix;
    }

    private static double buildItemDPS(Item item) {
        return 0.0;
    }

    private static double buildItemDEF(Item item) {
        return 0.0;
    }
}
