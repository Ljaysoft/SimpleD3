package com.game.simpled3.engine.gear.slots;

import com.game.simpled3.engine.gear.Item;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_LEFT_WEAPON;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_RIGHT_WEAPON;

/**
 * Created by JFCaron on 2015-05-05.
 */
public class Weapon extends Item {

    public Weapon(int lvl, boolean isRight) {
        super(lvl);
        if (isRight)
            mSlot = ITEM_SLOT_RIGHT_WEAPON;
        else
            mSlot = ITEM_SLOT_LEFT_WEAPON;

    }
}
