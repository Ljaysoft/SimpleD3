package com.game.simpled3.gameEntities.Gear.Slots;

import com.game.simpled3.gameEntities.Gear.Item;

import static com.game.simpled3.gameEntities.Enums.GameEnums.ITEM_SLOT_BOOTS;

/**
 * Created by JFCaron on 2015-05-05.
 */
public class Boots extends Item {

    public Boots(int lvl) {
        super(lvl);
        mSlot = ITEM_SLOT_BOOTS;
    }
}
