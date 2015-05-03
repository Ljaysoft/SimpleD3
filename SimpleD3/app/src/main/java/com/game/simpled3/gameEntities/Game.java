package com.game.simpled3.gameEntities;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.game.simpled3.R;

import java.util.ArrayList;

/**
 * Created by JFCaron on 2015-04-27.
 */
public class Game {
    private static Resources mRes;
    float[] mXpToLvl = null;
    float[] mGoldCoefPerLvl = null;
    int[] mItemPowerPerLvl = null;
    float[] mItemPowerForItemType = null;
    int mNbItemTypes = 0;
    int mNbItemColors = 0;
    //name
    String[][] mGearPrefixForGearType = null;
    String[][] mGearSuffixForGearType = null;

    float[] mPowerCoefForColor = null;
    private ArrayList<Dungeon> mDungeons = new ArrayList<>(100);
    int mCurrentDungeonLvl = 0;
    int mMaxDungeonLevel = 0;

    int[] mXpForDungeonLvl = null;
    int[] mShardForDungeonLvl = null;
    int mNbMonsterPerDungeon = 0;
    int mMaxPlayerLevel = 0;
    int mBaseMonsterXP = 0;
    int mBaseDungeonBonusGold = 0;
    int mBaseNumberOfItemPerLvl = 0;
    int mBaseGoldPerMonster = 0;

    double mChanceToDie = 0.0;

    private Game(Resources res) {
        initialize(res);
    }

    //Acquisition des données des arrays
    private void initialize(Resources res) {
        mRes = res;

        //Init ints
        mNbMonsterPerDungeon = mRes.getInteger(R.integer.base_number_monsters_per_dungeon);
        mBaseDungeonBonusGold = mRes.getInteger(R.integer.base_bonus_gold_per_dungeon);
        mMaxPlayerLevel = mRes.getInteger(R.integer.number_of_player_levels);
        mMaxDungeonLevel = mRes.getInteger(R.integer.number_of_dungeon_levels);
        mNbItemTypes = mRes.getInteger(R.integer.number_of_item_slots);
        mNbItemColors = mRes.getInteger(R.integer.number_of_item_colors);
        mBaseMonsterXP = mRes.getInteger(R.integer.base_monster_xp);
        mBaseNumberOfItemPerLvl = mRes.getInteger(R.integer.base_number_of_item_per_lvl);
        mBaseGoldPerMonster = mRes.getInteger(R.integer.base_gold_per_monster);

        //Init Arrays

        // xp/gold given per lvl
        int lvl = 0;
        TypedArray resourceTypedArr = mRes.obtainTypedArray(R.array.float_array_xp_to_lvl);
        mXpToLvl = new float[mMaxPlayerLevel];
        while (lvl < mMaxPlayerLevel) {
            mXpToLvl[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }

        lvl = 0;
        resourceTypedArr = mRes.obtainTypedArray(R.array.float_array_gold_coef_per_lvl);
        mGoldCoefPerLvl = new float[mMaxDungeonLevel];
        while (lvl < mMaxDungeonLevel) {
            mGoldCoefPerLvl[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }

        // Item arrays
        mItemPowerPerLvl = mRes.getIntArray(R.array.int_array_ipower_for_lvl);

        lvl = 0;
        resourceTypedArr = mRes.obtainTypedArray(R.array.float_array_ipower_for_type);
        mItemPowerForItemType = new float[mNbItemTypes];
        while (lvl < mNbItemTypes) {
            mItemPowerForItemType[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }

        lvl = 0;
        resourceTypedArr = mRes.obtainTypedArray(R.array.float_array_ipower_coef_for_color);
        mPowerCoefForColor = new float[mNbItemColors];
        while (lvl < mNbItemColors) {
            mPowerCoefForColor[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }

        mGearPrefixForGearType = new String[12][5];
        mGearSuffixForGearType = new String[12][5];
        mShardForDungeonLvl = mRes.getIntArray(R.array.int_array_shard_for_dungeon_lvl);
        mXpForDungeonLvl = mRes.getIntArray(R.array.int_array_xp_for_dungeon_lvl);

        //Create Dungeons
        lvl = 0;
        while (lvl < mMaxDungeonLevel) {
            mDungeons.add(new Dungeon(lvl, mNbMonsterPerDungeon,
                                      mShardForDungeonLvl[lvl] ) );
            lvl++;
        }

        //

    }
    public static Game createGame(Resources res) {
        return new Game(res);
    }

    public int updateDungeonProgress(Player player) {
        return mDungeons.get(mCurrentDungeonLvl).getProgress();
    }

    public void playerAttacks(Player player) {
        Dungeon currentDungeon = mDungeons.get(mCurrentDungeonLvl);
        int monstersKilled = currentDungeon.playerAttacked(player);

        if (currentDungeon.isDone()) {
            Loot loot = generateLoot(currentDungeon, player);
            player.giveShards(loot.getShards());
            player.giveGold(loot.getGold());
            for(Item item: loot.getItems()) {
                player.giveItem(item);
            }

            player.giveXP(mXpForDungeonLvl[mCurrentDungeonLvl], mXpToLvl);
            mCurrentDungeonLvl++;
            return;
        }

        player.giveGold(monstersKilled * mBaseGoldPerMonster * mGoldCoefPerLvl[mCurrentDungeonLvl]);
        player.giveXP(monstersKilled * mBaseMonsterXP,
                mXpToLvl);
    }

    public Loot generateLoot(Dungeon dungeon, Player player) {
        ArrayList<Item> items = new ArrayList<>(mBaseNumberOfItemPerLvl);
        Item item;
        for (int i = 0; i < mBaseNumberOfItemPerLvl; i++) {
            item = Item.createItem(player.getLevel());
            item.generateStats(mRes);
            items.add(item);
        }
        return new Loot(mBaseDungeonBonusGold * mGoldCoefPerLvl[mCurrentDungeonLvl],
                        dungeon.getShards(), items);
    }

    public int getDungeonLevelForDisplay(){
        return mCurrentDungeonLvl+1;
    }
}
