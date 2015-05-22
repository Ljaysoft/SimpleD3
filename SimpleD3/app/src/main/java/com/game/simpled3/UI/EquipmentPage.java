package com.game.simpled3.UI;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_equipment_page, container, false);

        ImageButton shoulderButton = (ImageButton) rootView.findViewById(R.id.shoulderButton);
        shoulderButton.setOnTouchListener(new View.OnTouchListener() {
                                              @Override
                                              public boolean onTouch(View view, MotionEvent event) {
                                                  onSlotButtonPressed(view, event);
                                                  return true;
                                              }
                                          }
        );

        ImageButton helmButton = (ImageButton) rootView.findViewById(R.id.helmButton);
        helmButton.setOnTouchListener(new View.OnTouchListener() {
                                          @Override
                                          public boolean onTouch(View view, MotionEvent event) {
                                              onSlotButtonPressed(view, event);
                                              return true;
                                          }
                                      }
        );

        ImageButton neckButton = (ImageButton) rootView.findViewById(R.id.neckButton);
        neckButton.setOnTouchListener(new View.OnTouchListener() {
                                          @Override
                                          public boolean onTouch(View view, MotionEvent event) {
                                              onSlotButtonPressed(view, event);
                                              return true;
                                          }
                                      }
        );

        ImageButton chestButton = (ImageButton) rootView.findViewById(R.id.chestButton);
        chestButton.setOnTouchListener(new View.OnTouchListener() {
                                           @Override
                                           public boolean onTouch(View view, MotionEvent event) {
                                               onSlotButtonPressed(view, event);
                                               return true;
                                           }
                                       }
        );

        ImageButton glovesButton = (ImageButton) rootView.findViewById(R.id.glovesButton);
        glovesButton.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent event) {
                                                onSlotButtonPressed(view, event);
                                                return true;
                                            }
                                        }
        );

        ImageButton bracerButton = (ImageButton) rootView.findViewById(R.id.bracerButton);
        bracerButton.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent event) {
                                                onSlotButtonPressed(view, event);
                                                return true;
                                            }
                                        }
        );

        ImageButton beltButton = (ImageButton) rootView.findViewById(R.id.beltButton);
        beltButton.setOnTouchListener(new View.OnTouchListener() {
                                          @Override
                                          public boolean onTouch(View view, MotionEvent event) {
                                              onSlotButtonPressed(view, event);
                                              return true;
                                          }
                                      }
        );

        ImageButton pantsButton = (ImageButton) rootView.findViewById(R.id.pantsButton);
        pantsButton.setOnTouchListener(new View.OnTouchListener() {
                                           @Override
                                           public boolean onTouch(View view, MotionEvent event) {
                                               onSlotButtonPressed(view, event);
                                               return true;
                                           }
                                       }
        );

        ImageButton bootsButton = (ImageButton) rootView.findViewById(R.id.bootButton);
        bootsButton.setOnTouchListener(new View.OnTouchListener() {
                                           @Override
                                           public boolean onTouch(View view, MotionEvent event) {
                                               onSlotButtonPressed(view, event);
                                               return true;
                                           }
                                       }
        );

        ImageButton leftRingButton = (ImageButton) rootView.findViewById(R.id.leftRingButton);
        leftRingButton.setOnTouchListener(new View.OnTouchListener() {
                                              @Override
                                              public boolean onTouch(View view, MotionEvent event) {
                                                  onSlotButtonPressed(view, event);
                                                  return true;
                                              }
                                          }
        );

        ImageButton rightRingButton = (ImageButton) rootView.findViewById(R.id.rightRingButton);
        rightRingButton.setOnTouchListener(new View.OnTouchListener() {
                                               @Override
                                               public boolean onTouch(View view, MotionEvent event) {
                                                   onSlotButtonPressed(view, event);
                                                   return true;
                                               }
                                           }
        );

        ImageButton leftWeaponButton = (ImageButton) rootView.findViewById(R.id.leftWeaponButton);
        leftWeaponButton.setOnTouchListener(new View.OnTouchListener() {
                                                @Override
                                                public boolean onTouch(View view, MotionEvent event) {
                                                    onSlotButtonPressed(view, event);
                                                    return true;
                                                }
                                            }
        );

        ImageButton rightWeaponButton = (ImageButton) rootView.findViewById(R.id.rightWeaponButton);
        rightWeaponButton.setOnTouchListener(new View.OnTouchListener() {
                                                 @Override
                                                 public boolean onTouch(View view, MotionEvent event) {
                                                     onSlotButtonPressed(view, event);
                                                     return true;
                                                 }
                                             }
        );

        return rootView;
    }

    public void onSlotButtonPressed(View view, MotionEvent event) {
        if (event.getAction() !=  MotionEvent.ACTION_UP) {
            return;
        }
        ArrayList<Item> items = Player.getInstance().getItems();
        Item item = Item.getDummy();
        switch (view.getId()) {
            case R.id.shoulderButton:
                item = items.get(ITEM_SLOT_SHOULDER);
                break;
            case R.id.helmButton:
                item = items.get(ITEM_SLOT_HELM);
                break;
            case R.id.neckButton:
                item = items.get(ITEM_SLOT_NECK);
                break;
            case R.id.chestButton:
                item = items.get(ITEM_SLOT_CHEST);
                break;
            case R.id.glovesButton:
                item = items.get(ITEM_SLOT_GLOVE);
                break;
            case R.id.bracerButton:
                item = items.get(ITEM_SLOT_BRACER);
                break;
            case R.id.beltButton:
                item = items.get(ITEM_SLOT_BELT);
                break;
            case R.id.leftRingButton:
                item = items.get(ITEM_SLOT_RING1);
                break;
            case R.id.rightRingButton:
                item = items.get(ITEM_SLOT_RING2);
                break;
            case R.id.pantsButton:
                item = items.get(ITEM_SLOT_PANTS);
                break;
            case R.id.bootButton:
                item = items.get(ITEM_SLOT_BOOTS);
                break;
            case R.id.leftWeaponButton:
                item = items.get(ITEM_SLOT_LEFT_WEAPON);
                break;
            case R.id.rightWeaponButton:
                item = items.get(ITEM_SLOT_RIGHT_WEAPON);
                break;

        }
        if (mListener != null) {
            mListener.onEquipmentPageInteraction(view, event, item);
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
        void onEquipmentPageInteraction(View view, MotionEvent event, Item item);
    }

}
