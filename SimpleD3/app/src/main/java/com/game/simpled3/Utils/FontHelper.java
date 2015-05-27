package com.game.simpled3.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by JFCaron on 2015-05-19.
 */
public final class FontHelper {

    private static Font font;

    public static void applyFont(View parentView, boolean isFlavor, boolean applyToChildren) {

        font = Font.getInstance(parentView.getContext());

        if (applyToChildren)
            applyToAllChildren((ViewGroup) parentView, isFlavor);
        else if (parentView instanceof TextView) {
            if (isFlavor)
                ((TextView) parentView).setTypeface(font.PALATINO_LINOTYPE_I);
            else
                ((TextView) parentView).setTypeface(font.DIABLO_H);

        }
    }


    private static void applyToAllChildren(ViewGroup parentView, boolean isFlavor) {
        for (int i = 0; i < parentView.getChildCount(); i++) {

            View view = parentView.getChildAt(i);

//You can add any view element here on which you want to applyToAllChildren font

            if (view instanceof EditText) {

                ((EditText) view).setTypeface(font.DIABLO_H);

            }
            if (view instanceof TextView) {
                if (isFlavor)
                    ((TextView) view).setTypeface(font.PALATINO_LINOTYPE_I);
                else
                    ((TextView) view).setTypeface(font.DIABLO_H);

            } else if (view instanceof ViewGroup
                    && ((ViewGroup) view).getChildCount() > 0) {
                applyToAllChildren((ViewGroup) view, isFlavor);
            }

        }

    }

}
