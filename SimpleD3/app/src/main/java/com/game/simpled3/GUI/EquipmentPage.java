package com.game.simpled3.gUI;

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
import com.game.simpled3.engine.gear.slots.Helmet;
import com.game.simpled3.engine.gear.slots.Shoulders;
import com.game.simpled3.engine.gear.slots.Weapon;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_BLUE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_ORANGE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_YELLOW;


public class EquipmentPage extends DialogFragment {

    private OnEquipmentPageInteractionListener mListener;

    public EquipmentPage() {
        // Required empty public constructor
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
        if (event.getAction() !=  MotionEvent.ACTION_DOWN) {
            return;
        }
        Item item = Item.createItem(0);
        ItemViewPage itemView = new ItemViewPage(view.getContext());
        switch (view.getId()) {
            case R.id.shoulderButton:
                item = new Shoulders(Player.getInstance().getLevel());
                item.setStats("Super Shoulders of the Bear", 2.0, 4.0, ITEM_COLOR_ORANGE);
                break;
            case R.id.helmButton:
                item = new Helmet(Player.getInstance().getLevel());
                item.setStats("Large Cap of the Whale", 2.0, 2.5, ITEM_COLOR_BLUE);
                break;
            case R.id.neckButton:
                break;
            case R.id.chestButton:
                break;
            case R.id.glovesButton:
                break;
            case R.id.bracerButton:
                break;
            case R.id.beltButton:
                break;
            case R.id.leftRingButton:
                break;
            case R.id.rightRingButton:
                break;
            case R.id.pantsButton:
                break;
            case R.id.bootButton:
                break;
            case R.id.leftWeaponButton:
            case R.id.rightWeaponButton:
                item = new Weapon(Player.getInstance().getLevel(), true);
                item.setStats("Flamming Sword of Destruction", 4.5, 4.0, ITEM_COLOR_YELLOW);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnEquipmentPageInteractionListener {
        void onEquipmentPageInteraction(View view, MotionEvent event, Item item);
    }

}
