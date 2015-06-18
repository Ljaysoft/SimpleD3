package com.game.simpled3.UI.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.game.simpled3.R;
import com.game.simpled3.engine.gear.Item;
import com.squareup.picasso.Picasso;

/**
 * Created by JFCaron on 2015-06-03.
 */
public class ItemButton extends ImageButton {
    private static final String mediaSourceURL = "http://media.blizzard.com/d3/icons/items/large/";
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

    public Item getItem() {
        return mItem;
    }

    public void setItem(Item item) {
        mItem = item;
        setImage();
    }

    private void setImage() {
        if (mItem == null)
            return;

        if (mItem.isIconSquare()) {
            Picasso.with(getContext())
                    .load(mediaSourceURL + mItem.getImageID() + ".png")
                    .resizeDimen(R.dimen.item_icon_width, R.dimen.item_icon_width)
                    .into(this);
        } else {
            Picasso.with(getContext())
                    .load(mediaSourceURL + mItem.getImageID() + ".png")
                    .resizeDimen(R.dimen.item_icon_width, R.dimen.item_big_icon_height)
                    .into(this);
        }

    }
}
