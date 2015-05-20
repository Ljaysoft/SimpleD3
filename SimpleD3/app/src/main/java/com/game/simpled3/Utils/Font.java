package com.game.simpled3.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by JFCaron on 2015-05-20.
 */
public class Font {
    private static Font font;
    public Typeface DIABLO_H;

    private Font() {

    }

    public static Font getInstance(Context context) {
        if (font == null) {
            font = new Font();
            font.init(context);
        }
        return font;

    }

    public void init(Context context) {

        DIABLO_H = Typeface.createFromAsset(context.getAssets(),
                "fonts/diablo_h.ttf");
    }
}
