package com.game.simpled3.engine.gear;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;

import com.game.simpled3.R;
import com.game.simpled3.engine.Game;
import com.game.simpled3.engine.gear.slots.Belt;
import com.game.simpled3.engine.gear.slots.Boots;
import com.game.simpled3.engine.gear.slots.Bracers;
import com.game.simpled3.engine.gear.slots.Chestpiece;
import com.game.simpled3.engine.gear.slots.Gloves;
import com.game.simpled3.engine.gear.slots.Helmet;
import com.game.simpled3.engine.gear.slots.Jewel;
import com.game.simpled3.engine.gear.slots.Pants;
import com.game.simpled3.engine.gear.slots.Ring;
import com.game.simpled3.engine.gear.slots.Shoulders;
import com.game.simpled3.engine.gear.slots.Weapon;
import com.game.simpled3.engine.webservice.BlizzardService;
import com.game.simpled3.engine.webservice.D3ArmoryReader;
import com.game.simpled3.engine.webservice.models.FullItem;
import com.game.simpled3.utils.StdRandom;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_BLUE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GRAY;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GREEN;
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
public class ItemFactory implements D3ArmoryReader.ArmoryReaderCallback {
    private static final String TAG = ItemFactory.class.getSimpleName();
    private static final ItemFactory sInstance = new ItemFactory();
    private static boolean sIsInit = false;
    private ItemFactoryCallback mListener = Game.getInstance();

    private ArrayList<Item> mNewItems;
    private int mNbNewItemsToBuild = 0;

    private int mLvl = 0;

    private int[] mItemPowerPerLvl = null;
    private int mNbItemSlots = 0;
    private int mNbItemColors = 0;
    private float[] mItemPowerForItemType = null;
    private float[] mPowerCoefForColor = null;

    private ArrayList<ItemName> mGearNamesForColor = null;
    private ArrayList<ItemTypeNameList> mGearNamesForType = null;
    private int mNbItemTypes = 0;
    private String[] mGearPrefixForGearColor = null;
    private String[] mGearSuffixes = null;

    private ArrayList<NumberColorPair> mNumberOfItemsPerColor = null;

    private ItemFactory() {
    }

    public static ItemFactory getInstance() {
        return sInstance;
    }

    public static void initialize(Resources res) {
        if (sInstance == null || sIsInit)
            return;

        sInstance.mNbItemSlots = res.getInteger(R.integer.number_of_item_slots);
        int nbOfColors = res.getInteger(R.integer.number_of_item_colors);
        sInstance.mNbItemColors = nbOfColors;
        sInstance.mNumberOfItemsPerColor = new ArrayList<>(nbOfColors);
        int color = 0;
        while (color < nbOfColors) {
            sInstance.mNumberOfItemsPerColor.add(NumberColorPair.getNewPair(color, 0));
            color++;
        }
        sInstance.mNewItems = new ArrayList<>(res.getInteger(R.integer.base_number_of_item_per_dungeon));
        sInstance.mItemPowerPerLvl = res.getIntArray(R.array.int_array_ipower_for_lvl);

        TypedArray resourceTypedArr = res.obtainTypedArray(R.array.float_array_ipower_for_type);
        sInstance.mItemPowerForItemType = new float[sInstance.mNbItemSlots];
        int lvl = 0;
        while (lvl < sInstance.mNbItemSlots) {
            sInstance.mItemPowerForItemType[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }
        resourceTypedArr.recycle();

        lvl = 0;
        resourceTypedArr = res.obtainTypedArray(R.array.float_array_ipower_coef_for_color);
        sInstance.mPowerCoefForColor = new float[nbOfColors];
        while (lvl < nbOfColors) {
            sInstance.mPowerCoefForColor[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }
        resourceTypedArr.recycle();
        sInstance.buildNamesPerColorArray(res.getStringArray(R.array.string_array_gear_names));
        sInstance.buildNamesPerSlots(res.getStringArray(R.array.string_array_gear_types_d3));

        sInstance.mGearPrefixForGearColor = res.getStringArray(R.array.string_array_gear_prefix_for_color);
        sInstance.mGearSuffixes = res.getStringArray(R.array.string_array_gear_suffixes);

        sIsInit = true;
    }

    private static String getNewItemName(int color) {
        boolean nameFound = false;
        String name = "";
        while (!nameFound) {
            ItemName itemName = sInstance.mGearNamesForColor.get(
                    StdRandom.uniform(sInstance.mGearNamesForColor.size()));
            if (itemName.color == color) {
                name = itemName.name;
                nameFound = true;
            }
        }
        return name;
    }

    private static String getNewRandomItemName() {
        boolean nameFound = false;
        String name = "";
        if (sInstance.mGearNamesForType == null || sInstance.mGearNamesForType.isEmpty())
            return name;
        synchronized (sInstance.mGearNamesForType) {
            while (!nameFound) {
                ArrayList<String> nameList = sInstance.mGearNamesForType.get(
                        StdRandom.uniform(sInstance.mGearNamesForType.size()))
                        .getAllNames();
                if (!nameList.isEmpty()) {
                    name = nameList.get(StdRandom.uniform(nameList.size()));
                }
                nameFound = true;
            }
        }
        return name;
    }

    // TODO build balanced stats
    private static Item buildNewItem(FullItem fullItem) {
        if (fullItem == null)
            return null;
        Item rItem = fullItem.getItem();
        double dps = buildItemDPS(rItem);
        double def = buildItemDEF(rItem);
        rItem.setStats(dps, def);

        return rItem;
    }

    private static void buildItemFromName(String name) {
        BlizzardService service = D3ArmoryReader.getRestAdapter().create(BlizzardService.class);
        service.getItem(name, new Callback<FullItem>() {
            @Override
            public void success(FullItem fullItem, Response response) {
                Item item = buildNewItem(fullItem);
                ItemFactory.addNewItem(item);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e(TAG, retrofitError.toString());
                Log.e(TAG, retrofitError.getUrl());
            }
        });
    }

    public static void buildDungeonItems(int nbOfItems) {
        sInstance.mNbNewItemsToBuild = nbOfItems;
        for (int i = 0; i < nbOfItems; i++) {
            String itemName = getNewRandomItemName();
            buildItemFromName(itemName);
        }
    }

    private static ArrayList<Item> getNewItems() {
        synchronized (sInstance.mNewItems) {
            ArrayList<Item> returnArray = (ArrayList<Item>) sInstance.mNewItems.clone();
            sInstance.mNewItems.clear();
            sInstance.mNbNewItemsToBuild = 0;
            return returnArray;
        }
    }

    private static void addNewItem(Item item) {
        synchronized (sInstance.mNewItems) {
            sInstance.mNewItems.add(item);
        }
        if (sInstance.areItemsBuilt()) {
            sInstance.mListener.onItemCreationDone(getNewItems());
        }
    }

    @Deprecated
    private static Item getNewItem() {
        double p = StdRandom.uniform();
        int slot = (int) (p * ((double) sInstance.mNbItemSlots));
        switch (slot) {
            case ITEM_SLOT_HELM:
                return new Helmet(sInstance.mLvl);
            case ITEM_SLOT_SHOULDER:
                return new Shoulders(sInstance.mLvl);
            case ITEM_SLOT_CHEST:
                return new Chestpiece(sInstance.mLvl);
            case ITEM_SLOT_NECK:
                return new Jewel(sInstance.mLvl);
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
            case ITEM_COLOR_GRAY:
            case ITEM_COLOR_WHITE:
                return ITEM_COLOR_WHITE;
            case ITEM_COLOR_BLUE:
                return ITEM_COLOR_BLUE;
            case ITEM_COLOR_YELLOW:
                return ITEM_COLOR_YELLOW;
            case ITEM_COLOR_ORANGE:
                return ITEM_COLOR_ORANGE;
            case ITEM_COLOR_GREEN:
                return ITEM_COLOR_GREEN;
            default:
                return ITEM_COLOR_WHITE;
        }
    }

    // TODO build full name
    private static String buildFullItemName(Item item) {
        String prefix = "";
        String name = "";
        String suffix = "";

        return prefix + " " + name + " " + suffix;
    }

    private static double buildItemDPS(Item item) {
        return (double) sInstance.mItemPowerPerLvl[item.getILvl()]
                * sInstance.mPowerCoefForColor[item.getColor()]
                * sInstance.mItemPowerForItemType[item.getSlot()];

    }

    private static double buildItemDEF(Item item) {
        return sInstance.mItemPowerPerLvl[item.getILvl()]
                * sInstance.mPowerCoefForColor[item.getColor()]
                * sInstance.mItemPowerForItemType[item.getSlot()];
    }

    public static boolean areItemsBuilt() {
        return sInstance.mNewItems.size() == sInstance.mNbNewItemsToBuild;
    }

    public static boolean isFactoryReady() {
        return sInstance.mNbItemTypes <= sInstance.mGearNamesForType.size()*4;
    }

    private void buildNamesPerColorArray(String[] namesFromRessources) {
        sInstance.mGearNamesForColor = new ArrayList<>(namesFromRessources.length);
        for (String name : namesFromRessources) {
            ItemName itemname = ItemName.getNewItemName(name);
            sInstance.mNumberOfItemsPerColor.get(itemname.color).numberOf++;
            sInstance.mGearNamesForColor.add(itemname);
        }
    }

    private void buildNamesPerSlots(String[] namesFromRessources) {
        sInstance.mGearNamesForType = new ArrayList<>(namesFromRessources.length);
        for (String name : namesFromRessources) {
            ItemTypeNameList itemTypeNameList = new ItemTypeNameList(name);
            sInstance.mNbItemTypes++;
            D3ArmoryReader.requestItemsFromItemType(itemTypeNameList, sInstance);
        }
    }

    @Override
    public void onFetchNamesForSlotsDone(ArrayList<ItemTypeNameList> slotNames) {
        synchronized (sInstance.mGearNamesForType) {
            sInstance.mGearNamesForType.addAll(slotNames);
        }
    }

    /**
     * Internal classes to manage names and color
     */
    private static class ItemName {
        public int color = ITEM_COLOR_GRAY;
        public String name = "";

        private ItemName(String name) {
            parseName(name);
            parseColor(name);
        }

        public static ItemName getNewItemName(String name) {
            return new ItemName(name);
        }

        private void parseName(String name) {
            switch (name.charAt(0)) {
                case 'G':
                    this.color = ITEM_COLOR_GRAY;
                    return;
                case 'W':
                    this.color = ITEM_COLOR_WHITE;
                    return;
                case 'B':
                    this.color = ITEM_COLOR_BLUE;
                    return;
                case 'Y':
                    this.color = ITEM_COLOR_YELLOW;
                    return;
                case 'O':
                    this.color = ITEM_COLOR_ORANGE;
                    return;
                case 'S':
                    this.color = ITEM_COLOR_GREEN;
                    return;
                default:
            }
        }

        private void parseColor(String name) {
            if (!name.isEmpty())
                this.name = name.substring(1);
        }
    }

    private static class NumberColorPair {
        public int color;
        public int numberOf;

        private NumberColorPair(int color, int numberOf) {
            this.color = color;
            this.numberOf = numberOf;
        }

        public static NumberColorPair getNewPair(int color, int numberOf) {
            return new NumberColorPair(color, numberOf);
        }

        @Override
        public boolean equals(Object object) {
            boolean result = false;
            if (object == null || object.getClass() != getClass()) {
                result = false;
            } else {
                NumberColorPair numberColorPair = (NumberColorPair) object;
                if (this.color == numberColorPair.color) {
                    result = true;
                }
            }
            return result;
        }
    }

    public interface ItemFactoryCallback {
        void onItemCreationDone(ArrayList<Item> items);
    }
}
