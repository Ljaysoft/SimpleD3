package com.game.simpled3.UI;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.game.simpled3.R;
import com.game.simpled3.engine.gear.Item;
import com.game.simpled3.utils.AutoResizeTextView;
import com.game.simpled3.utils.FontHelper;
import com.game.simpled3.utils.StringManipulation;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_BLUE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GRAY;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_ORANGE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_WHITE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_YELLOW;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GREEN;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_BELT;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_BOOTS;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_BRACER;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_CHEST;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_DUMMY;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_GLOVE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_HELM;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_LEFT_WEAPON;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_NECK;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_PANTS;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_RIGHT_WEAPON;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_RING1;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_RING2;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_SHOULDER;


/**
 * Created by JFCaron on 2015-05-13.
 */
public class ItemViewPage extends PopupWindow {

    private Context context = null;
    private Item mCurrentItem = Item.createItem(1);
    @InjectView(R.id.itemNameTextView) AutoResizeTextView mItemName;
    @InjectView(R.id.slotTextView) TextView mSlot;
    @InjectView(R.id.itemILvlTextView) TextView miLvl;
    @InjectView(R.id.colorTextView) TextView mColor;
    @InjectView(R.id.itemDpsTextView) TextView mDPS;
    @InjectView(R.id.itemDefTextView) TextView mDEF;
    @InjectView(R.id.flavorText) TextView mFlavor;
    private Bitmap mTooltipBorders;
    private int tooltipTitleHeight;

    public ItemViewPage(Context ctx) {
        super(ctx);

        context = ctx;
        Resources res = context.getResources();
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_item_view, null);
        setContentView(view);
        View popupView = getContentView();
        setWidth(res.getDimensionPixelSize(R.dimen.item_view_width));
        setHeight(res.getDimensionPixelSize(R.dimen.item_view_height));
        tooltipTitleHeight = res.getDimensionPixelSize(R.dimen.item_view_title_height);
        setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1)
            setAttachedInDecor(false);

        ButterKnife.inject(this, view);
        mCurrentItem = Item.createItem(1);
        mTooltipBorders = BitmapFactory.decodeResource(res, R.drawable.tooltip_titles);
        FontHelper.applyFont(popupView, false, true);
        FontHelper.applyFont(mFlavor, true, false);

    }

    public void setItemToShow(Item item) {
        mCurrentItem = item;
        updateItemValues();
    }

    public void show(View view) {
        showAsDropDown(view);
    }

    private void updateItemValues() {
        mItemName.setText(mCurrentItem.getName());
        switch (mCurrentItem.getSlot()) {
            case ITEM_SLOT_DUMMY:
                mSlot.setText("Scraps");
                break;
            case ITEM_SLOT_BELT:
                mSlot.setText("Belt");
                break;
            case ITEM_SLOT_SHOULDER:
                mSlot.setText("Shoulderpad");
                break;
            case ITEM_SLOT_HELM:
                mSlot.setText("Helmet");
                break;
            case ITEM_SLOT_CHEST:
                mSlot.setText("Chestpiece");
                break;
            case ITEM_SLOT_NECK:
                mSlot.setText("Jewel");
                break;
            case ITEM_SLOT_GLOVE:
                mSlot.setText("Gloves");
                break;
            case ITEM_SLOT_BRACER:
                mSlot.setText("Bracers");
                break;
            case ITEM_SLOT_PANTS:
                mSlot.setText("Pants");
                break;
            case ITEM_SLOT_BOOTS:
                mSlot.setText("Boots");
                break;
            case ITEM_SLOT_RING1:
            case ITEM_SLOT_RING2:
                mSlot.setText("Finger");
                break;
            case ITEM_SLOT_LEFT_WEAPON:
            case ITEM_SLOT_RIGHT_WEAPON:
                mSlot.setText("Weapon");
                break;
        }
        miLvl.setText("Item Level: " + String.valueOf(mCurrentItem.getILvl() + 1));
        mFlavor.setText(mCurrentItem.getFlavorText());
        Bitmap title;
        switch (mCurrentItem.getColor()) {
            case ITEM_COLOR_GRAY:
                mColor.setText("Junk");
                mColor.setTextColor(Color.GRAY);
                mSlot.setTextColor(Color.GRAY);
                mItemName.setTextColor(Color.GRAY);
                title = Bitmap.createBitmap(mTooltipBorders, 0, 0, mTooltipBorders.getWidth(), tooltipTitleHeight);
                mItemName.setBackgroundDrawable(new BitmapDrawable(context.getResources(), title));
                break;
            case ITEM_COLOR_WHITE:
                mColor.setText("Normal");
                mColor.setTextColor(Color.WHITE);
                mSlot.setTextColor(Color.WHITE);
                mItemName.setTextColor(Color.WHITE);
                title = Bitmap.createBitmap(mTooltipBorders, 0, tooltipTitleHeight, mTooltipBorders.getWidth(), tooltipTitleHeight);
                mItemName.setBackgroundDrawable(new BitmapDrawable(context.getResources(), title));
                break;
            case ITEM_COLOR_BLUE:
                mColor.setText("Magic");
                mColor.setTextColor(Color.BLUE);
                mSlot.setTextColor(Color.BLUE);
                mItemName.setTextColor(Color.BLUE);
                title = Bitmap.createBitmap(mTooltipBorders, 0, tooltipTitleHeight * 2, mTooltipBorders.getWidth(), tooltipTitleHeight);
                mItemName.setBackgroundDrawable(new BitmapDrawable(context.getResources(), title));
                break;
            case ITEM_COLOR_YELLOW:
                mColor.setText("Rare");
                mColor.setTextColor(Color.YELLOW);
                mSlot.setTextColor(Color.YELLOW);
                mItemName.setTextColor(Color.YELLOW);
                title = Bitmap.createBitmap(mTooltipBorders, 0, tooltipTitleHeight * 3, mTooltipBorders.getWidth(), tooltipTitleHeight);
                mItemName.setBackgroundDrawable(new BitmapDrawable(context.getResources(), title));
                break;
            case ITEM_COLOR_ORANGE:
                mColor.setText("Legendary");
                int orange = Color.parseColor("#BF642F");
                mColor.setTextColor(orange);
                mSlot.setTextColor(orange);
                mItemName.setTextColor(orange);
                title = Bitmap.createBitmap(mTooltipBorders, 0, tooltipTitleHeight * 4, mTooltipBorders.getWidth(), tooltipTitleHeight);
                mItemName.setBackgroundDrawable(new BitmapDrawable(context.getResources(), title));
                break;
            case ITEM_COLOR_GREEN:
                mColor.setText("Set");
                int green = Color.parseColor("#8BD442");
                mColor.setTextColor(green);
                mSlot.setTextColor(green);
                mItemName.setTextColor(green);
                title = Bitmap.createBitmap(mTooltipBorders, 0, tooltipTitleHeight * 6, mTooltipBorders.getWidth(), tooltipTitleHeight);
                mItemName.setBackgroundDrawable(new BitmapDrawable(context.getResources(), title));
                break;

        }
        mDPS.setText(StringManipulation.formatBigNumbers(mCurrentItem.getDPS()));
        mDEF.setText(StringManipulation.formatBigNumbers(mCurrentItem.getDEF()));
    }

}
