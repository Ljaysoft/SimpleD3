package com.game.simpled3.gUI;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.game.simpled3.R;
import com.game.simpled3.engine.gear.Item;
import com.game.simpled3.utils.AutoResizeTextView;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_BLUE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GRAY;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_ORANGE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_WHITE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_YELLOW;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_BELT;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_BOOTS;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_BRACER;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_SLOT_CHEST;
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

    Context context;
    Item mCurrentItem = null;
    AutoResizeTextView mItemName;
    TextView mSlot;
    TextView miLvl;
    TextView mColor;
    TextView mDPS;
    TextView mDEF;

    public ItemViewPage(Context ctx) {
        super(ctx);

        context = ctx;
        Resources res = context.getResources();
        setContentView(LayoutInflater.from(context).inflate(R.layout.fragment_item_view, null));
        View popupView = getContentView();
        setWidth(res.getDimensionPixelSize(R.dimen.item_view_width));
        setHeight(res.getDimensionPixelSize(R.dimen.item_view_height));
        setFocusable(true);

        mItemName = (AutoResizeTextView) popupView.findViewById(R.id.itemNameTextView);
        mSlot = (TextView) popupView.findViewById(R.id.slotTextView);
        miLvl = (TextView) popupView.findViewById(R.id.itemILvlTextView);
        mColor = (TextView) popupView.findViewById(R.id.colorTextView);
        mDPS = (TextView) popupView.findViewById(R.id.itemDpsTextView);
        mDEF = (TextView) popupView.findViewById(R.id.itemDefTextView);
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
        miLvl.setText("Lvl " + String.valueOf(mCurrentItem.getILvl() + 1));
        switch (mCurrentItem.getColor()) {
            case ITEM_COLOR_GRAY:
                mColor.setText("Junk");
                mColor.setTextColor(Color.GRAY);
                mSlot.setTextColor(Color.GRAY);
                mItemName.setTextColor(Color.GRAY);
                mItemName.setBackgroundResource(R.drawable.black_item_title);
                break;
            case ITEM_COLOR_WHITE:
                mColor.setText("Common");
                mColor.setTextColor(Color.WHITE);
                mSlot.setTextColor(Color.WHITE);
                mItemName.setTextColor(Color.WHITE);
                mItemName.setBackgroundResource(R.drawable.white_item_title);
                break;
            case ITEM_COLOR_BLUE:
                mColor.setText("Magic");
                mColor.setTextColor(Color.BLUE);
                mSlot.setTextColor(Color.BLUE);
                mItemName.setTextColor(Color.BLUE);
                mItemName.setBackgroundResource(R.drawable.blue_item_title);
                break;
            case ITEM_COLOR_YELLOW:
                mColor.setText("Rare");
                mColor.setTextColor(Color.YELLOW);
                mSlot.setTextColor(Color.YELLOW);
                mItemName.setTextColor(Color.YELLOW);
                mItemName.setBackgroundResource(R.drawable.yellow_item_title);
                break;
            case ITEM_COLOR_ORANGE:
                mColor.setText("Legendary");
                mColor.setTextColor(Color.parseColor("#FFA500"));
                mSlot.setTextColor(Color.parseColor("#FFA500"));
                mItemName.setTextColor(Color.parseColor("#FFA500"));
                mItemName.setBackgroundResource(R.drawable.orange_item_title);
                break;

        }
        mDPS.setText(String.valueOf(mCurrentItem.getDPS()) + " DPS");
        mDEF.setText(String.valueOf(mCurrentItem.getDEF()) + " DEF");
    }

}
