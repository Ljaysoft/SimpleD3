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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.game.simpled3.R;
import com.game.simpled3.engine.gear.Item;
import com.game.simpled3.utils.AutoResizeTextView;
import com.game.simpled3.utils.FontHelper;
import com.game.simpled3.utils.StringManipulation;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_BLUE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GRAY;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GREEN;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_ORANGE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_WHITE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_YELLOW;
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

    private Context mContext = null;
    private Item mCurrentItem = Item.createItem(1);
    @InjectView(R.id.itemNameTextView) AutoResizeTextView mItemName;
    @InjectView(R.id.slotTextView) TextView mSlot;
    @InjectView(R.id.itemILvlTextView) TextView miLvl;
    @InjectView(R.id.colorTextView) TextView mColor;
    @InjectView(R.id.itemDpsTextView) TextView mDPS;
    @InjectView(R.id.itemDefTextView) TextView mDEF;
    @InjectView(R.id.dmgPerSecTextView) TextView mDPSText;
    @InjectView(R.id.defTextView) TextView mDEFText;
    @InjectView(R.id.flavorText) TextView mFlavor;
    @InjectView(R.id.itemIconView) ImageView mImageIcon;
    private Bitmap mTooltipBorders;
    private int tooltipTitleHeight;

    private String mediaSourceURL = "http://media.blizzard.com/d3/icons/items/large/";

    public ItemViewPage(Context ctx) {
        super(ctx);

        mContext = ctx;
        Resources res = mContext.getResources();
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_item_view, null);
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

    public void show(ItemButton view) {
        mCurrentItem = view.getItem();
        updateItemValues();
        showAsDropDown(view);
    }

    private void updateItemValues() {
        String name = mCurrentItem.getName();
        if (name == "no_name") {
            mItemName.setVisibility(View.GONE);
        } else {
            mItemName.setVisibility(View.VISIBLE);
            mItemName.setText(mCurrentItem.getName());
        }

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
                mItemName.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), title));
                break;
            case ITEM_COLOR_WHITE:
                mColor.setText("Normal");
                mColor.setTextColor(Color.WHITE);
                mSlot.setTextColor(Color.WHITE);
                mItemName.setTextColor(Color.WHITE);
                title = Bitmap.createBitmap(mTooltipBorders, 0, tooltipTitleHeight, mTooltipBorders.getWidth(), tooltipTitleHeight);
                mItemName.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), title));
                break;
            case ITEM_COLOR_BLUE:
                mColor.setText("Magic");
                mColor.setTextColor(Color.BLUE);
                mSlot.setTextColor(Color.BLUE);
                mItemName.setTextColor(Color.BLUE);
                title = Bitmap.createBitmap(mTooltipBorders, 0, tooltipTitleHeight * 2, mTooltipBorders.getWidth(), tooltipTitleHeight);
                mItemName.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), title));
                break;
            case ITEM_COLOR_YELLOW:
                mColor.setText("Rare");
                mColor.setTextColor(Color.YELLOW);
                mSlot.setTextColor(Color.YELLOW);
                mItemName.setTextColor(Color.YELLOW);
                title = Bitmap.createBitmap(mTooltipBorders, 0, tooltipTitleHeight * 3, mTooltipBorders.getWidth(), tooltipTitleHeight);
                mItemName.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), title));
                break;
            case ITEM_COLOR_ORANGE:
                mColor.setText("Legendary");
                int orange = Color.parseColor("#BF642F");
                mColor.setTextColor(orange);
                mSlot.setTextColor(orange);
                mItemName.setTextColor(orange);
                title = Bitmap.createBitmap(mTooltipBorders, 0, tooltipTitleHeight * 4, mTooltipBorders.getWidth(), tooltipTitleHeight);
                mItemName.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), title));
                break;
            case ITEM_COLOR_GREEN:
                mColor.setText("Set");
                int green = Color.parseColor("#8BD442");
                mColor.setTextColor(green);
                mSlot.setTextColor(green);
                mItemName.setTextColor(green);
                title = Bitmap.createBitmap(mTooltipBorders, 0, tooltipTitleHeight * 6, mTooltipBorders.getWidth(), tooltipTitleHeight);
                mItemName.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), title));
                break;

        }
        double dps = mCurrentItem.getDPS();
        if (dps == 0) {
            mDPS.setVisibility(View.GONE);
            mDPSText.setVisibility(View.GONE);
        } else {
            mDPS.setText(StringManipulation.formatBigNumbers(dps));
            mDPS.setVisibility(View.VISIBLE);
            mDPSText.setVisibility(View.VISIBLE);
        }
        double def = mCurrentItem.getDEF();
        if (def == 0) {
            mDEF.setVisibility(View.GONE);
            mDEFText.setVisibility(View.GONE);
        } else {
            mDEF.setText(StringManipulation.formatBigNumbers(def));
            mDEF.setVisibility(View.VISIBLE);
            mDEFText.setVisibility(View.VISIBLE);
        }
        boolean isSquare = mCurrentItem.isIconSquare();

        //TODO marche pas, à fixer
        if (isSquare) {
            Picasso.with(mContext).load(mediaSourceURL + mCurrentItem.getImageID() + ".png").resizeDimen(R.dimen.item_icon_width,R.dimen.item_icon_width).into(mImageIcon);
        }
        else {
            Picasso.with(mContext).load(mediaSourceURL + mCurrentItem.getImageID() + ".png").resizeDimen(R.dimen.item_icon_width, R.dimen.item_big_icon_height).into(mImageIcon);
        }
    }

}
