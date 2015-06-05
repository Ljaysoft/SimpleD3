package com.game.simpled3.utils;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by JFCaron on 2015-06-05.
 */
public class UIHelper {

    // don't instantiate
    private UIHelper() {
    }

    public static Point getRelativePosition(View v, MotionEvent event) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        float screenX = event.getRawX();
        float screenY = event.getRawY();
        float viewX = screenX - location[0];
        float viewY = screenY - location[1];
        return new Point((int) viewX, (int) viewY);
    }
}
