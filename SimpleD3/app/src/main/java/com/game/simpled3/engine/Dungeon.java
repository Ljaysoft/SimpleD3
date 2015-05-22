package com.game.simpled3.engine;

/**
 * Created by JFCaron on 2015-04-27.
 */
public class Dungeon {
    private int mDungeonLvl = 1;
    private int mNbMonsters = 100;
    private int mNbMonstersKilled = 0;
    private double mMonsterHP = 1;
    private double mMonsterDMG = 1;
    private double mShards = 1;
    private boolean mIsDone = false;

    public Dungeon(int lvl, int monsters, int monsterHP, double shards) {
        mDungeonLvl = lvl;
        mNbMonsters = monsters;
        mMonsterHP = monsterHP;
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
        return mNbMonstersKilled >= mNbMonsters;
    }

    public byte getProgress() {
        if (isDone())
            return 100;
        return (byte)(mNbMonstersKilled * 100 / mNbMonsters);
    }

    /*
    @return number of monsters killed
     */
    public int playerAttacked(Player player) {
        double pDPS = player.getDPS();
        //TODO player gets attacked back
        double pDEF = player.getDEF();
        int newMonstersKilled = (int) (pDPS / mMonsterHP);

        if ((mNbMonsters - mNbMonstersKilled) > newMonstersKilled) {
            mNbMonstersKilled += newMonstersKilled;
        }
        else {
            newMonstersKilled = mNbMonsters - mNbMonstersKilled;
            mNbMonstersKilled = mNbMonsters;
        }

        return newMonstersKilled;
    }
}
