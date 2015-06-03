package com.game.simpled3.UI;

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
    private Item mItem = null;
    private static final String mediaSourceURL = "http://media.blizzard.com/d3/icons/items/large/";

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
        setImage();
    }

    public Item getItem() {
        return mItem;
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
