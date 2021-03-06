package com.game.simpled3.engine.gear.slots;

import com.game.simpled3.engine.gear.Item;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_RIGHT_WEAPON;

/**
 * Created by JFCaron on 2015-05-05.
 */
public class Shield extends Item {

    /**
     * @param lvl item level
     */
    public Shield(int lvl) {
        super(lvl);
        mSlot = ITEM_SLOT_RIGHT_WEAPON;
    }

    @Override
    public void setStats(double dps, double def) {
        super.setStats(0, def);
    }
}
