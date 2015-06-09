package com.game.simpled3.utils;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by JFCaron on 2015-06-08.
 */
public class FxPlayer {
    private static final FxPlayer S_INSTANCE = new FxPlayer();
    private static boolean isInit = false;
    private Context mContext;
    private MediaPlayer mMediaPlayer = null;

    private FxPlayer() {
    }

    public static void init(Context context) {
        if (!isInit) {
            S_INSTANCE.mContext = context;
            isInit = true;
        }
    }

    public static void playSound(int ResId) {
        if (!isInit)
            return;

        if (S_INSTANCE.mMediaPlayer != null) {
            S_INSTANCE.mMediaPlayer.reset();
            S_INSTANCE.mMediaPlayer.release();
        }

        S_INSTANCE.mMediaPlayer = MediaPlayer.create(S_INSTANCE.mContext, ResId);
        S_INSTANCE.mMediaPlayer.start();
    }
}
