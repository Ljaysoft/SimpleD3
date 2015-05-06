package com.game.simpled3.gameEntities.Gear.Slots;

import com.game.simpled3.gameEntities.Gear.Item;

import static com.game.simpled3.gameEntities.Enums.GameEnums.ITEM_SLOT_LEFT_WEAPON;

/**
 * Created by JFCaron on 2015-05-05.
 */
public class Weapon extends Item{

    private boolean mIsRight = false;

    public Weapon(int lvl) {
        super(lvl);
        mSlot = ITEM_SLOT_LEFT_WEAPON;
    }

    public boolean isRight(){
        return mIsRight;
    }

    public void setRight(boolean isRight) {
        mIsRight = isRight;
    }
}
