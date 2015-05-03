package com.game.simpled3.gameEntities;

/**
 * Created by JFCaron on 2015-04-27.
 */
public class Dungeon {
    private int mDungeonLvl = 1;
    private int mNbMonsters = 100;
    private int mNbMonstersKilled = 0;
    private double mMonsterHP = 0.1;
    private double mMonsterDMG = 1;
    private double mShards = 1;
    private boolean mIsDone = false;

    public Dungeon(int lvl, int monsters, double shards) {
        mDungeonLvl = lvl;
        mNbMonsters = monsters;
        mShards = shards;
    }

    public int getDungeonLvl() {
        return mDungeonLvl;
    }

    public int getNbMonsters() {
        return mNbMonsters;
    }

    public double getMonsterHP() {
        return mMonsterHP;
    }

    public double getMonsterDMG() {
        return mMonsterDMG;
    }

    public double getShards() {
        return mShards;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public int getProgress() {
        return mNbMonstersKilled *100 / mNbMonsters;
    }

    /*
    @return number of monsters killed
     */
    public int playerAttacked(Player player) {
        double pDPS = player.getDPS();
        double pDEF = player.getDEF();
        int monstersKilledPerSec = (int) (pDPS / mMonsterHP);
        if (mNbMonstersKilled < mNbMonsters) {
            mNbMonstersKilled += monstersKilledPerSec;
        } else {
            mIsDone = true;
            return 0;
        }
        return 1;
    }
}
