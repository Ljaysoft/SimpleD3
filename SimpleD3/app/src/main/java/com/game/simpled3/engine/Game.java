package com.game.simpled3.engine;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.game.simpled3.R;
import com.game.simpled3.engine.gear.Item;
import com.game.simpled3.engine.gear.ItemFactory;
import com.game.simpled3.engine.gear.Loot;

import java.util.ArrayList;

/**
 * Created by JFCaron on 2015-04-27.
 */
public class Game {
    private static Game sInstance = null;
    private static boolean sIsInit = false;

    private static Resources mRes;
    static private float[] mXpToLvl = null;
    static private float[] mGoldCoefPerLvl = null;

    static private ArrayList<Dungeon> mDungeons = new ArrayList<>(100);
    static private int mCurrentDungeonLvl = 0;
    static private int mMaxDungeonLevel = 0;

    static private int[] mXpForDungeonLvl = null;
    static private int[] mShardForDungeonLvl = null;
    static private int mNbMonsterPerDungeon = 0;
    static private int mMaxPlayerLevel = 0;
    static private int mBaseMonsterXP = 0;
    static private int mBaseDungeonBonusGold = 0;
    static private int mBaseGoldPerMonster = 0;
    static private int mBaseNumberOfItemPerDungeon = 0;

    static private double mChanceToDie = 0.0;

    private Game() {

    }

    public static Game getInstance() {
        if (sInstance == null) {
            sInstance = new Game();
        }
        return sInstance;
    }

    //Acquisition des données des arrays
    public static void initialize(Resources res) {
        if (sInstance == null || sIsInit)
            return;

        mRes = res;

        //Init ints
        mNbMonsterPerDungeon = mRes.getInteger(R.integer.base_number_monsters_per_dungeon);
        mBaseDungeonBonusGold = mRes.getInteger(R.integer.base_bonus_gold_per_dungeon);
        mMaxPlayerLevel = mRes.getInteger(R.integer.number_of_player_levels);
        mMaxDungeonLevel = mRes.getInteger(R.integer.number_of_dungeon_levels);
        mBaseMonsterXP = mRes.getInteger(R.integer.base_monster_xp);
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
        resourceTypedArr.recycle();

        lvl = 0;
        resourceTypedArr = mRes.obtainTypedArray(R.array.float_array_gold_coef_per_lvl);
        mGoldCoefPerLvl = new float[mMaxDungeonLevel];
        while (lvl < mMaxDungeonLevel) {
            mGoldCoefPerLvl[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }
        resourceTypedArr.recycle();

        mShardForDungeonLvl = mRes.getIntArray(R.array.int_array_shard_for_dungeon_lvl);
        mXpForDungeonLvl = mRes.getIntArray(R.array.int_array_xp_for_dungeon_lvl);

        mBaseNumberOfItemPerDungeon = mRes.getInteger(R.integer.base_number_of_item_per_dungeon);
        //Create Dungeons
        lvl = 0;
        while (lvl < mMaxDungeonLevel) {
            mDungeons.add(new Dungeon(lvl, mNbMonsterPerDungeon,
                    mShardForDungeonLvl[lvl]));
            lvl++;
        }


        sIsInit = true;
    }

    public int updateDungeonProgress(Player player) {
        return mDungeons.get(mCurrentDungeonLvl).getProgress();
    }

    public void playerAttacks(Player player) {
        Dungeon currentDungeon = mDungeons.get(mCurrentDungeonLvl);
        int monstersKilled = currentDungeon.playerAttacked(player);

        if (currentDungeon.isDone()) {
            Loot loot = generateLoot(currentDungeon);
            player.giveShards(loot.getShards());
            player.giveGold(loot.getGold());
            for (Item item : loot.getItems()) {
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

    public Loot generateLoot(Dungeon dungeon) {
        ArrayList<Item> items = ItemFactory.BuildNewItems(mBaseNumberOfItemPerDungeon);
        return new Loot(mBaseDungeonBonusGold * mGoldCoefPerLvl[mCurrentDungeonLvl],
                dungeon.getShards(), items);
    }

    public int getDungeonLevelForDisplay() {
        return mCurrentDungeonLvl + 1;
    }
}
