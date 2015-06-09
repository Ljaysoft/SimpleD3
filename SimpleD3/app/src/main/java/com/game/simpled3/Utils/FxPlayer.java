package com.game.simpled3.utils;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by JFCaron on 2015-06-08.
 */
public class FxPlayer {
    private static final FxPlayer sInstance = new FxPlayer();
    private static boolean isInit = false;
    private Context mContext;
    private MediaPlayer mMediaPlayer = null;

    private FxPlayer() {
    }

    public static void init(Context context) {
        if (!isInit) {
            sInstance.mContext = context;
            isInit = true;
        }
    }

    public static void playSound(int ResId) {
        if (!isInit)
            return;

        if (sInstance.mMediaPlayer != null) {
            sInstance.mMediaPlayer.reset();
            sInstance.mMediaPlayer.release();
        }

        sInstance.mMediaPlayer = MediaPlayer.create(sInstance.mContext, ResId);
        sInstance.mMediaPlayer.start();
    }
}
