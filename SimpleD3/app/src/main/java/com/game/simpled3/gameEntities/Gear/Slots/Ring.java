package com.game.simpled3.gameEntities.Gear.Slots;

import com.game.simpled3.gameEntities.Gear.Item;

import static com.game.simpled3.gameEntities.Enums.GameEnums.ITEM_SLOT_RING1;
import static com.game.simpled3.gameEntities.Enums.GameEnums.ITEM_SLOT_RING2;

/**
 * Created by JFCaron on 2015-05-05.
 */
public class Ring extends Item {

    public Ring(int lvl, boolean isRight) {
        super(lvl);
        if (isRight)
            mSlot = ITEM_SLOT_RING2;
        else
            mSlot = ITEM_SLOT_RING1;

    }
}
