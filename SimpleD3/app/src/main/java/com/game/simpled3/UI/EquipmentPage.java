package com.game.simpled3.UI;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.game.simpled3.R;
import com.game.simpled3.engine.Player;
import com.game.simpled3.engine.gear.Item;

import java.util.ArrayList;

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


public class EquipmentPage extends DialogFragment {

    private OnEquipmentPageInteractionListener mListener;

    public EquipmentPage() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
    }

    // TODO show icons in equipment page
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_equipment_page, container, false);

        for (View v : rootView.getTouchables()) {
            if (v instanceof ItemButton) {
                v.setOnTouchListener(new View.OnTouchListener() {
                                         @Override
                                         public boolean onTouch(View view, MotionEvent event) {
                                             onSlotButtonPressed((ItemButton) view, event);
                                             return true;
                                         }
                                     }
                );
                ArrayList<Item> playerItems = Player.getInstance().getItems();
                Item item = Item.getDummy();
                switch (v.getId()) {
                    case R.id.shoulderButton:
                        item = playerItems.get(ITEM_SLOT_SHOULDER);
                        break;
                    case R.id.helmButton:
                        item = playerItems.get(ITEM_SLOT_HELM);
                        break;
                    case R.id.neckButton:
                        item = playerItems.get(ITEM_SLOT_NECK);
                        break;
                    case R.id.chestButton:
                        item = playerItems.get(ITEM_SLOT_CHEST);
                        break;
                    case R.id.glovesButton:
                        item = playerItems.get(ITEM_SLOT_GLOVE);
                        break;
                    case R.id.bracerButton:
                        item = playerItems.get(ITEM_SLOT_BRACER);
                        break;
                    case R.id.beltButton:
                        item = playerItems.get(ITEM_SLOT_BELT);
                        break;
                    case R.id.leftRingButton:
                        item = playerItems.get(ITEM_SLOT_RING1);
                        break;
                    case R.id.rightRingButton:
                        item = playerItems.get(ITEM_SLOT_RING2);
                        break;
                    case R.id.pantsButton:
                        item = playerItems.get(ITEM_SLOT_PANTS);
                        break;
                    case R.id.bootButton:
                        item = playerItems.get(ITEM_SLOT_BOOTS);
                        break;
                    case R.id.leftWeaponButton:
                        item = playerItems.get(ITEM_SLOT_LEFT_WEAPON);
                        break;
                    case R.id.rightWeaponButton:
                        item = playerItems.get(ITEM_SLOT_RIGHT_WEAPON);
                        break;
                }
                ((ItemButton) v).setItem(item);
            }
        }

        return rootView;
    }

    private void onSlotButtonPressed(ItemButton button, MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_UP) {
            return;
        }
        if (mListener != null) {
            mListener.onEquipmentPageInteraction(button);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnEquipmentPageInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnEquipmentPageInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnEquipmentPageInteractionListener {
        void onEquipmentPageInteraction(ItemButton view);
    }

}
