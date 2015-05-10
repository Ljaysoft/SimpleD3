package com.game.simpled3.engine.gear.slots;

import com.game.simpled3.engine.gear.Item;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_BRACER;

/**
 * Created by JFCaron on 2015-05-05.
 */
public class Bracers extends Item {

    public Bracers(int lvl) {
        super(lvl);
        mSlot = ITEM_SLOT_BRACER;
    }
}
