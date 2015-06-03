package com.game.simpled3.UI;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.game.simpled3.engine.gear.Item;

/**
 * Created by JFCaron on 2015-06-03.
 */
public class ItemButton extends ImageButton {
    private Item mItem = null;

    public ItemButton(Context context) {
        super(context, null);
    }

    public ItemButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ItemButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
    }

    public void setItem(Item item) {
        mItem = item;
    }

    public Item getItem() {
        return mItem;
    }
}
