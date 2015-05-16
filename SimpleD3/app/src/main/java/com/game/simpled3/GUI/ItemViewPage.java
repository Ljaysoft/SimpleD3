package com.game.simpled3.gUI;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.game.simpled3.R;
import com.game.simpled3.engine.gear.Item;

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
    TextView mItemName;
    TextView mSlot;
    TextView miLvl;
    TextView mColor;
    TextView mDPS;
    TextView mDEF;

    public ItemViewPage(Context ctx) {
        super(ctx);

        context = ctx;
        setContentView(LayoutInflater.from(context).inflate(R.layout.fragment_item_view, null));
        View popupView = getContentView();
        setWidth(600);
        setHeight(800);
        setFocusable(true);

        mItemName = (TextView) popupView.findViewById(R.id.itemNameTextView);
        mSlot = (TextView) popupView.findViewById(R.id.slotTextView);
        miLvl = (TextView) popupView.findViewById(R.id.itemILvlTextView);
        mColor = (TextView) popupView.findViewById(R.id.colorTextView);
        mDPS = (TextView) popupView.findViewById(R.id.itemDpsTextView);
        mDEF = (TextView) popupView.findViewById(R.id.itemDefTextView);
    }

    public void showItem(Item item) {
        mCurrentItem = item;
        updateItemValues();
    }

    public void show(View view) {
        Rect locateView = locateView(view);
        showAtLocation(view, Gravity.NO_GRAVITY, locateView.left, locateView.top);
    }

    public static Rect locateView(View v)
    {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try
        {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException e)
        {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
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
        miLvl.setText("Lvl " + String.valueOf(mCurrentItem.getILvl()+1));
        switch (mCurrentItem.getColor()) {
            case ITEM_COLOR_GRAY:
                mColor.setText("Junk");
                mColor.setTextColor(Color.GRAY);
                break;
            case ITEM_COLOR_WHITE:
                mColor.setText("Common");
                mColor.setTextColor(Color.WHITE);
                break;
            case ITEM_COLOR_BLUE:
                mColor.setText("Magic");
                mColor.setTextColor(Color.BLUE);
                break;
            case ITEM_COLOR_YELLOW:
                mColor.setText("Rare");
                mColor.setTextColor(Color.YELLOW);
                break;
            case ITEM_COLOR_ORANGE:
                mColor.setText("Legendary");
                mColor.setTextColor(Color.parseColor("#FFA500"));
                break;

        }
        mDPS.setText(String.valueOf(mCurrentItem.getDPS()) + " DPS");
        mDEF.setText(String.valueOf(mCurrentItem.getDEF()) + " DEF");
    }

}
