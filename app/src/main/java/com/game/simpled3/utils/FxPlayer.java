package com.game.simpled3.utils;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by JFCaron on 2015-06-08.
 */
public class FxPlayer {
<<<<<<< HEAD
    private static final FxPlayer sInstance = new FxPlayer();
=======
    private static final FxPlayer S_INSTANCE = new FxPlayer();
>>>>>>> origin/Alpha_0_1
    private static boolean isInit = false;
    private Context mContext;
    private MediaPlayer mMediaPlayer = null;

    private FxPlayer() {
    }

    public static void init(Context context) {
        if (!isInit) {
<<<<<<< HEAD
            sInstance.mContext = context;
=======
            S_INSTANCE.mContext = context;
>>>>>>> origin/Alpha_0_1
            isInit = true;
        }
    }

    public static void playSound(int ResId) {
        if (!isInit)
            return;

<<<<<<< HEAD
        if (sInstance.mMediaPlayer != null) {
            sInstance.mMediaPlayer.reset();
            sInstance.mMediaPlayer.release();
        }

        sInstance.mMediaPlayer = MediaPlayer.create(sInstance.mContext, ResId);
        sInstance.mMediaPlayer.start();
=======
        if (S_INSTANCE.mMediaPlayer != null) {
            S_INSTANCE.mMediaPlayer.reset();
            S_INSTANCE.mMediaPlayer.release();
        }

        S_INSTANCE.mMediaPlayer = MediaPlayer.create(S_INSTANCE.mContext, ResId);
        S_INSTANCE.mMediaPlayer.start();
>>>>>>> origin/Alpha_0_1
    }
}
