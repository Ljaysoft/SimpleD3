package com.game.simpled3.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by JFCaron on 2015-05-19.
 */
public class FontHelper {

    private static Font font;

    public static void applyFont(View parentView) {

        font = Font.getInstance(parentView.getContext());

        apply((ViewGroup)parentView);

    }

    private static void apply(ViewGroup parentView) {
        for (int i = 0; i < parentView.getChildCount(); i++) {

            View view = parentView.getChildAt(i);

//You can add any view element here on which you want to apply font

            if (view instanceof EditText) {

                ((EditText) view).setTypeface(font.DIABLO_H);

            }
            if (view instanceof TextView) {

                ((TextView) view).setTypeface(font.DIABLO_H);

            }

            else if (view instanceof ViewGroup
                    && ((ViewGroup) view).getChildCount() > 0) {
                apply((ViewGroup) view);
            }

        }

    }

}
