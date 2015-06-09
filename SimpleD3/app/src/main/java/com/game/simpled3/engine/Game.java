package com.game.simpled3.engine;

import android.content.res.Resources;
import android.content.res.TypedArray;

import com.game.simpled3.MainActivity;
import com.game.simpled3.R;
import com.game.simpled3.engine.gear.Item;
import com.game.simpled3.engine.gear.ItemFactory;
import com.game.simpled3.engine.gear.ItemFactory.ItemFactoryCallback;
import com.game.simpled3.engine.gear.Loot;
import com.game.simpled3.utils.StdRandom;

import java.util.ArrayList;

/**
 * Created by JFCaron on 2015-04-27.
 */
public final class Game implements ItemFactoryCallback {
    private static final Game sInstance = new Game();
    private GameListener mListener;
    private static boolean sIsInit = false;

    private Resources mRes;
    private float[] mXpToLvl = null;
    private float[] mGoldCoefPerLvl = null;


    private final ArrayList<Dungeon> mDungeons = new ArrayList<>(100);
    private int mCurrentDungeonLvl = -1;
    private int mMaxDungeonLevel = 0;

    private int[] mXpForDungeonLvl = null;
    private int[] mShardForDungeonLvl = null;
    private int mNbMonsterPerDungeon = 0;
    private int mMaxPlayerLevel = 0;
    private int mBaseMonsterXP = 0;
    private int mBaseMonsterHP = 0;
    private int mBaseDungeonBonusGold = 0;
    private int mBaseGoldPerMonster = 0;
    private int mBaseNumberOfItemPerDungeon = 0;

    private double mChanceToDie = 0.01;

    private Loot mCurrentLoot = null;

    private Game() {

    }

    public static Game getInstance() {
        return sInstance;
    }

    //Acquisition des données des arrays
    public static void initialize(Resources res, MainActivity activity) {
        if (sIsInit)
            return;

        sInstance.mRes = res;
        sInstance.mListener = activity;

        //Init ints
        sInstance.mNbMonsterPerDungeon = sInstance.mRes.getInteger(R.integer.base_number_monsters_per_dungeon);
        sInstance.mBaseDungeonBonusGold = sInstance.mRes.getInteger(R.integer.base_bonus_gold_per_dungeon);
        sInstance.mMaxPlayerLevel = sInstance.mRes.getInteger(R.integer.number_of_player_levels);
        sInstance.mMaxDungeonLevel = sInstance.mRes.getInteger(R.integer.number_of_dungeon_levels);
        sInstance.mBaseMonsterXP = sInstance.mRes.getInteger(R.integer.base_monster_xp);
        sInstance.mBaseMonsterHP = sInstance.mRes.getInteger(R.integer.base_monster_HP);
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
            sInstance.mDungeons.add(new Dungeon(lvl, sInstance.mNbMonsterPerDungeon, lvl * sInstance.mBaseMonsterHP,
                    sInstance.mShardForDungeonLvl[lvl]));
            lvl++;
        }

        sIsInit = true;
    }

    public static byte updateDungeonProgress() {
        if (sInstance.mCurrentDungeonLvl == -1)
            return 0;
        return sInstance.mDungeons.get(sInstance.mCurrentDungeonLvl).getProgress();
    }

    /**
     * Player attacks
     *
     * @return getLoot
     */
    public static Loot playerAttacks() {
        if (StdRandom.bernoulli(sInstance.mChanceToDie)) {
            Player.kill();
            return null;
        }

        if (sInstance.mCurrentDungeonLvl == -1)
            return null;

        // do stuff if broken
        //if (player.isGearBroke()) {
        //}


        Dungeon currentDungeon = sInstance.mDungeons.get(sInstance.mCurrentDungeonLvl);
        int monstersKilled = currentDungeon.playerAttacked();

        if (!currentDungeon.isDone()) {
            Player.giveGold(monstersKilled * sInstance.mBaseGoldPerMonster * sInstance.mGoldCoefPerLvl[sInstance.mCurrentDungeonLvl]);
            Player.giveXP(monstersKilled * sInstance.mBaseMonsterXP, sInstance.mXpToLvl);
            return null;
        } else {
            Player.giveXP(sInstance.mXpForDungeonLvl[sInstance.mCurrentDungeonLvl], sInstance.mXpToLvl);
            return sInstance.mCurrentLoot;
        }
    }

    public static int getDungeonLevelForDisplay() {
        return sInstance.mCurrentDungeonLvl + 1;
    }

    public static boolean isDungeonInProgress() {
        if (sInstance.mCurrentDungeonLvl == -1)
            return false;
        return sInstance.mDungeons.get(sInstance.mCurrentDungeonLvl).getProgress() < 100;
    }

    public static boolean isDungeonDone() {
        if (sInstance.mCurrentDungeonLvl == -1)
            return false;
        return sInstance.mDungeons.get(sInstance.mCurrentDungeonLvl).getProgress() >= 100;
    }

    public static boolean isDungeonStarted() {
        if (sInstance.mCurrentDungeonLvl == -1)
            return false;
        return sInstance.mDungeons.get(sInstance.mCurrentDungeonLvl).getProgress() >= 0 && !isDungeonDone();
    }

    public static void nextDungeon() {
        sInstance.mCurrentDungeonLvl++;
        sInstance.mCurrentLoot = null;
        ItemFactory.buildDungeonItems(sInstance.mBaseNumberOfItemPerDungeon);
    }

    @Override
    public void onItemInitialisationStarted() {
        mListener.onItemFactoryInitStarted();
    }

    @Override
    public void onItemCreationDone(ArrayList<Item> items) {
        Dungeon dungeon = mDungeons.get(mCurrentDungeonLvl);
        mCurrentLoot = new Loot(mBaseDungeonBonusGold * mGoldCoefPerLvl[mCurrentDungeonLvl],
                dungeon.getShards(), items);
        mListener.onLootReady();
    }

    public interface GameListener {
        void onItemFactoryInitStarted();

        void onLootReady();
    }
}
