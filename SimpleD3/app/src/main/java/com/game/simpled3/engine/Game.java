package com.game.simpled3.engine;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.game.simpled3.R;
import com.game.simpled3.engine.gear.Item;
import com.game.simpled3.engine.gear.ItemFactory;
import com.game.simpled3.engine.gear.Loot;
import com.game.simpled3.utils.StdRandom;

import java.util.ArrayList;

/**
 * Created by JFCaron on 2015-04-27.
 */
public class Game {
    private static final Game sInstance = new Game();
    private boolean sIsInit = false;

    private Resources mRes;
    private float[] mXpToLvl = null;
    private float[] mGoldCoefPerLvl = null;

    private ArrayList<Dungeon> mDungeons = new ArrayList<>(100);
    private int mCurrentDungeonLvl = 0;
    private int mMaxDungeonLevel = 0;

    private int[] mXpForDungeonLvl = null;
    private int[] mShardForDungeonLvl = null;
    private int mNbMonsterPerDungeon = 0;
    private int mMaxPlayerLevel = 0;
    private int mBaseMonsterXP = 0;
    private int mBaseDungeonBonusGold = 0;
    private int mBaseGoldPerMonster = 0;
    private int mBaseNumberOfItemPerDungeon = 0;

     private double mChanceToDie = 0.25;

    private Game() {

    }

    public static Game getInstance() {
        return sInstance;
    }

    //Acquisition des données des arrays
    public static void initialize(Resources res) {
        if (sInstance == null || sInstance.sIsInit)
            return;

        sInstance.mRes = res;

        //Init ints
        sInstance.mNbMonsterPerDungeon = sInstance.mRes.getInteger(R.integer.base_number_monsters_per_dungeon);
        sInstance.mBaseDungeonBonusGold = sInstance.mRes.getInteger(R.integer.base_bonus_gold_per_dungeon);
        sInstance.mMaxPlayerLevel = sInstance.mRes.getInteger(R.integer.number_of_player_levels);
        sInstance.mMaxDungeonLevel = sInstance.mRes.getInteger(R.integer.number_of_dungeon_levels);
        sInstance.mBaseMonsterXP = sInstance.mRes.getInteger(R.integer.base_monster_xp);
        sInstance.mBaseGoldPerMonster = sInstance.mRes.getInteger(R.integer.base_gold_per_monster);

        //Init Arrays

        // xp/gold given per lvl
        int lvl = 0;
        TypedArray resourceTypedArr = sInstance.mRes.obtainTypedArray(R.array.float_array_xp_to_lvl);
        sInstance.mXpToLvl = new float[sInstance.mMaxPlayerLevel];
        while (lvl < sInstance.mMaxPlayerLevel) {
            sInstance.mXpToLvl[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }
        resourceTypedArr.recycle();

        lvl = 0;
        resourceTypedArr = sInstance.mRes.obtainTypedArray(R.array.float_array_gold_coef_per_lvl);
        sInstance.mGoldCoefPerLvl = new float[sInstance.mMaxDungeonLevel];
        while (lvl < sInstance.mMaxDungeonLevel) {
            sInstance.mGoldCoefPerLvl[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }
        resourceTypedArr.recycle();

        sInstance.mShardForDungeonLvl = sInstance.mRes.getIntArray(R.array.int_array_shard_for_dungeon_lvl);
        sInstance.mXpForDungeonLvl = sInstance.mRes.getIntArray(R.array.int_array_xp_for_dungeon_lvl);

        sInstance.mBaseNumberOfItemPerDungeon = sInstance.mRes.getInteger(R.integer.base_number_of_item_per_dungeon);
        //Create Dungeons
        lvl = 0;
        while (lvl < sInstance.mMaxDungeonLevel) {
            sInstance.mDungeons.add(new Dungeon(lvl, sInstance.mNbMonsterPerDungeon,
                    sInstance.mShardForDungeonLvl[lvl]));
            lvl++;
        }


        sInstance.sIsInit = true;
    }

    public int updateDungeonProgress(Player player) {
        return mDungeons.get(mCurrentDungeonLvl).getProgress();
    }

    public void playerAttacks(Player player) {
        Dungeon currentDungeon = mDungeons.get(mCurrentDungeonLvl);
        int monstersKilled = currentDungeon.playerAttacked(player);
        if (StdRandom.bernoulli(mChanceToDie)) {
            player.kill();
            return;
        }

        if (!currentDungeon.isDone()) {
            player.giveGold(monstersKilled * mBaseGoldPerMonster * mGoldCoefPerLvl[mCurrentDungeonLvl]);
            player.giveXP(monstersKilled * mBaseMonsterXP,
                    mXpToLvl);

            return;
        }

        Loot loot = generateLoot(currentDungeon);
        player.giveShards(loot.getShards());
        player.giveGold(loot.getGold());
        for (Item item : loot.getItems()) {
            player.giveItem(item);
        }

        player.giveXP(mXpForDungeonLvl[mCurrentDungeonLvl], mXpToLvl);
        mCurrentDungeonLvl++;

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
