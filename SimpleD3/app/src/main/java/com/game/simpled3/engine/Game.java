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
    private static final Game S_INSTANCE = new Game();
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
        return S_INSTANCE;
    }

    //Acquisition des données des arrays
    public static void initialize(Resources res, MainActivity activity) {
        if (sIsInit)
            return;

        S_INSTANCE.mRes = res;
        S_INSTANCE.mListener = activity;

        //Init ints
        S_INSTANCE.mNbMonsterPerDungeon = S_INSTANCE.mRes.getInteger(R.integer.base_number_monsters_per_dungeon);
        S_INSTANCE.mBaseDungeonBonusGold = S_INSTANCE.mRes.getInteger(R.integer.base_bonus_gold_per_dungeon);
        S_INSTANCE.mMaxPlayerLevel = S_INSTANCE.mRes.getInteger(R.integer.number_of_player_levels);
        S_INSTANCE.mMaxDungeonLevel = S_INSTANCE.mRes.getInteger(R.integer.number_of_dungeon_levels);
        S_INSTANCE.mBaseMonsterXP = S_INSTANCE.mRes.getInteger(R.integer.base_monster_xp);
        S_INSTANCE.mBaseMonsterHP = S_INSTANCE.mRes.getInteger(R.integer.base_monster_HP);
        S_INSTANCE.mBaseGoldPerMonster = S_INSTANCE.mRes.getInteger(R.integer.base_gold_per_monster);

        //Init Arrays

        // xp/gold given per lvl
        int lvl = 0;
        TypedArray resourceTypedArr = S_INSTANCE.mRes.obtainTypedArray(R.array.float_array_xp_to_lvl);
        S_INSTANCE.mXpToLvl = new float[S_INSTANCE.mMaxPlayerLevel];
        while (lvl < S_INSTANCE.mMaxPlayerLevel) {
            S_INSTANCE.mXpToLvl[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }
        resourceTypedArr.recycle();

        lvl = 0;
        resourceTypedArr = S_INSTANCE.mRes.obtainTypedArray(R.array.float_array_gold_coef_per_lvl);
        S_INSTANCE.mGoldCoefPerLvl = new float[S_INSTANCE.mMaxDungeonLevel];
        while (lvl < S_INSTANCE.mMaxDungeonLevel) {
            S_INSTANCE.mGoldCoefPerLvl[lvl] = resourceTypedArr.getFloat(lvl, 0);
            lvl++;
        }
        resourceTypedArr.recycle();

        S_INSTANCE.mShardForDungeonLvl = S_INSTANCE.mRes.getIntArray(R.array.int_array_shard_for_dungeon_lvl);
        S_INSTANCE.mXpForDungeonLvl = S_INSTANCE.mRes.getIntArray(R.array.int_array_xp_for_dungeon_lvl);

        S_INSTANCE.mBaseNumberOfItemPerDungeon = S_INSTANCE.mRes.getInteger(R.integer.base_number_of_item_per_dungeon);
        //Create Dungeons
        lvl = 0;
        while (lvl < S_INSTANCE.mMaxDungeonLevel) {
            S_INSTANCE.mDungeons.add(new Dungeon(lvl, S_INSTANCE.mNbMonsterPerDungeon, lvl * S_INSTANCE.mBaseMonsterHP,
                    S_INSTANCE.mShardForDungeonLvl[lvl]));
            lvl++;
        }

        sIsInit = true;
    }

    public static byte updateDungeonProgress() {
        if (S_INSTANCE.mCurrentDungeonLvl == -1)
            return 0;
        return S_INSTANCE.mDungeons.get(S_INSTANCE.mCurrentDungeonLvl).getProgress();
    }

    /**
     * Player attacks
     *
     * @return getLoot
     */
    public static Loot playerAttacks() {
        if (StdRandom.bernoulli(S_INSTANCE.mChanceToDie)) {
            Player.kill();
            return null;
        }

        if (S_INSTANCE.mCurrentDungeonLvl == -1)
            return null;

        // do stuff if broken
        //if (player.isGearBroke()) {
        //}


        Dungeon currentDungeon = S_INSTANCE.mDungeons.get(S_INSTANCE.mCurrentDungeonLvl);
        int monstersKilled = currentDungeon.playerAttacked();

        if (!currentDungeon.isDone()) {
            Player.giveGold(monstersKilled * S_INSTANCE.mBaseGoldPerMonster * S_INSTANCE.mGoldCoefPerLvl[S_INSTANCE.mCurrentDungeonLvl]);
            Player.giveXP(monstersKilled * S_INSTANCE.mBaseMonsterXP, S_INSTANCE.mXpToLvl);
            return null;
        } else {
            Player.giveXP(S_INSTANCE.mXpForDungeonLvl[S_INSTANCE.mCurrentDungeonLvl], S_INSTANCE.mXpToLvl);
            return S_INSTANCE.mCurrentLoot;
        }
    }

    public static int getDungeonLevelForDisplay() {
        return S_INSTANCE.mCurrentDungeonLvl + 1;
    }

    public static boolean isDungeonInProgress() {
        if (S_INSTANCE.mCurrentDungeonLvl == -1)
            return false;
        return S_INSTANCE.mDungeons.get(S_INSTANCE.mCurrentDungeonLvl).getProgress() < 100;
    }

    public static boolean isDungeonDone() {
        if (S_INSTANCE.mCurrentDungeonLvl == -1)
            return false;
        return S_INSTANCE.mDungeons.get(S_INSTANCE.mCurrentDungeonLvl).getProgress() >= 100;
    }

    public static boolean isDungeonStarted() {
        if (S_INSTANCE.mCurrentDungeonLvl == -1)
            return false;
        return S_INSTANCE.mDungeons.get(S_INSTANCE.mCurrentDungeonLvl).getProgress() >= 0 && !isDungeonDone();
    }

    public static void nextDungeon() {
        S_INSTANCE.mCurrentDungeonLvl++;
        S_INSTANCE.mCurrentLoot = null;
        ItemFactory.buildDungeonItems(S_INSTANCE.mBaseNumberOfItemPerDungeon);
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
