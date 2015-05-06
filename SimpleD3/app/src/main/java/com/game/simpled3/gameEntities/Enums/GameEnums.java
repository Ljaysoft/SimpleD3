package com.game.simpled3.gameEntities.Enums;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by JFCaron on 2015-04-28.
 */
public class GameEnums {

    /**
     *
     */
    // ITEM_COLOR
    @IntDef({ITEM_COLOR_GRAY,
            ITEM_COLOR_WHITE,
            ITEM_COLOR_BLUE,
            ITEM_COLOR_YELLOW,
            ITEM_COLOR_ORANGE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ItemColor {}

    public static final int ITEM_COLOR_GRAY = 0;
    public static final int ITEM_COLOR_WHITE = 1;
    public static final int ITEM_COLOR_BLUE = 2;
    public static final int ITEM_COLOR_YELLOW = 3;
    public static final int ITEM_COLOR_ORANGE = 4;

    /**
     *
     */
    // ITEM_SLOT
    @IntDef({ITEM_SLOT_ANY,
            ITEM_SLOT_HELM,
            ITEM_SLOT_SHOULDER,
            ITEM_SLOT_CHEST,
            ITEM_SLOT_NECK,
            ITEM_SLOT_GLOVE,
            ITEM_SLOT_BRACER,
            ITEM_SLOT_BELT,
            ITEM_SLOT_PANTS,
            ITEM_SLOT_BOOTS,
            ITEM_SLOT_RING1,
            ITEM_SLOT_RING2,
            ITEM_SLOT_LEFT_WEAPON,
            ITEM_SLOT_RIGHT_WEAPON})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ItemSlot {}

    public static final int ITEM_SLOT_ANY = 0;
    public static final int ITEM_SLOT_HELM = 1;
    public static final int ITEM_SLOT_SHOULDER = 2;
    public static final int ITEM_SLOT_CHEST = 3;
    public static final int ITEM_SLOT_NECK = 4;
    public static final int ITEM_SLOT_GLOVE = 5;
    public static final int ITEM_SLOT_BRACER = 6;
    public static final int ITEM_SLOT_BELT = 7;
    public static final int ITEM_SLOT_PANTS = 8;
    public static final int ITEM_SLOT_BOOTS = 9;
    public static final int ITEM_SLOT_RING1 = 10;
    public static final int ITEM_SLOT_RING2 = 11;
    public static final int ITEM_SLOT_LEFT_WEAPON = 12;
    public static final int ITEM_SLOT_RIGHT_WEAPON = 13;

}
