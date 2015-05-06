package com.game.simpled3.gameEntities.Gear.Slots;

import com.game.simpled3.gameEntities.Gear.Item;

import static com.game.simpled3.gameEntities.Enums.GameEnums.ITEM_SLOT_RING1;

/**
 * Created by JFCaron on 2015-05-05.
 */
public class Ring extends Item{

    private boolean mIsRight = false;

    public Ring(int lvl) {
        super(lvl);
        mSlot = ITEM_SLOT_RING1;
    }

    public boolean isRight(){
        return mIsRight;
    }

    public void setRight(boolean isRight) {
        mIsRight = isRight;
    }
}
