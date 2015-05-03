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
    //name
    String[][] mGearPrefixForType = null;
    String[][] mGearSuffixfortype = null;

    float[] mPowerCoefForColor = null;
    private ArrayList<Dungeon> mDungeons = new ArrayList<>();
    int mCurrentDungeonLvl = 0;

    int[] mXpForDungeonLvl = null;
    int[] mShardForDungeonLvl = null;
    int[] mNbMonsterForLvl = null;

    double mChanceToDie = 0.0;

    private Game(Resources res) {
        initialize(res);
    }

    //Acquisition des données des arrays
    private void initialize(Resources res) {
        int tempNumberOfLvl;
        int tempLvlitarator = 0;
        mRes = res;
        TypedArray resourceTypedArr = mRes.obtainTypedArray(R.array.float_array_xp_to_lvl);
        tempNumberOfLvl = resourceTypedArr.length();
        mXpToLvl = new float[tempNumberOfLvl];
        while (tempLvlitarator < tempNumberOfLvl) {
            mXpToLvl[tempLvlitarator] = resourceTypedArr.getFloat(tempLvlitarator, 0);
            tempLvlitarator++;
        }

        tempLvlitarator = 0;
        resourceTypedArr = mRes.obtainTypedArray(R.array.float_array_gold_coef_per_lvl);
        tempNumberOfLvl = resourceTypedArr.length();
        mGoldCoefPerLvl = new float[tempNumberOfLvl];
        while (tempLvlitarator < tempNumberOfLvl) {
            mGoldCoefPerLvl[tempLvlitarator] = resourceTypedArr.getFloat(tempLvlitarator, 0);
            tempLvlitarator++;
        }

        tempLvlitarator = 0;
        resourceTypedArr = mRes.obtainTypedArray(R.array.int_array_ipower_for_lvl);
        tempNumberOfLvl = resourceTypedArr.length();
        mItemPowerPerLvl = new int[tempNumberOfLvl];
        while (tempLvlitarator < tempNumberOfLvl) {
            mItemPowerPerLvl[tempLvlitarator] = resourceTypedArr.getInt(tempLvlitarator, 0);
            tempLvlitarator++;
        }

        tempLvlitarator = 0;
        resourceTypedArr = mRes.obtainTypedArray(R.array.float_array_ipower_for_type);
        tempNumberOfLvl = resourceTypedArr.length();
        mItemPowerForItemType = new float[tempNumberOfLvl];
        while (tempLvlitarator < tempNumberOfLvl) {
            mItemPowerForItemType[tempLvlitarator] = resourceTypedArr.getFloat(tempLvlitarator, 0);
            tempLvlitarator++;
        }

        tempLvlitarator = 0;
        resourceTypedArr = mRes.obtainTypedArray(R.array.float_array_ipower_coef_for_color);
        tempNumberOfLvl = resourceTypedArr.length();
        mPowerCoefForColor = new float[tempNumberOfLvl];
        while (tempLvlitarator < tempNumberOfLvl) {
            mPowerCoefForColor[tempLvlitarator] = resourceTypedArr.getFloat(tempLvlitarator, 0);
            tempLvlitarator++;
        }

        mGearPrefixForType = new String[12][5];
        mGearSuffixfortype = new String[12][5];

        tempLvlitarator = 0;
        resourceTypedArr = mRes.obtainTypedArray(R.array.int_array_shard_for_dungeon_lvl);
        tempNumberOfLvl = resourceTypedArr.length();
        mShardForDungeonLvl = new int[tempNumberOfLvl];
        while (tempLvlitarator < tempNumberOfLvl) {
            mShardForDungeonLvl[tempLvlitarator] = resourceTypedArr.getInt(tempLvlitarator, 0);
            tempLvlitarator++;
        }

        tempLvlitarator = 0;
        resourceTypedArr = mRes.obtainTypedArray(R.array.int_nb_monsters_for_lvl);
        tempNumberOfLvl = resourceTypedArr.length();
        mNbMonsterForLvl = new int[tempNumberOfLvl];
        while (tempLvlitarator < tempNumberOfLvl) {
            mNbMonsterForLvl[tempLvlitarator] = resourceTypedArr.getInt(tempLvlitarator, 0);
            tempLvlitarator++;
        }

        tempLvlitarator = 0;
        resourceTypedArr = mRes.obtainTypedArray(R.array.int_array_xp_for_dungeon_lvl);
        tempNumberOfLvl = resourceTypedArr.length();
        mXpForDungeonLvl = new int[tempNumberOfLvl];
        while (tempLvlitarator < tempNumberOfLvl) {
            mXpForDungeonLvl[tempLvlitarator] = resourceTypedArr.getInt(tempLvlitarator, 0);
            tempLvlitarator++;
        }

        tempLvlitarator = 0;
        mDungeons.ensureCapacity(tempNumberOfLvl);
        while (tempLvlitarator < tempNumberOfLvl) {
            mDungeons.add(new Dungeon(tempLvlitarator,mNbMonsterForLvl[tempLvlitarator],
                                  mShardForDungeonLvl[tempLvlitarator] ) );
            tempLvlitarator++;
        }

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
        player.giveXP(monstersKilled * mXpForDungeonLvl[currentDungeon.getDungeonLvl()],
                      mXpToLvl);

        if (currentDungeon.isDone()) {
            player.giveShards(currentDungeon.getShards());
            mCurrentDungeonLvl++;
        }
    }

    public int getDungeonLevelForDisplay(){
        return mCurrentDungeonLvl+1;
    }
}
