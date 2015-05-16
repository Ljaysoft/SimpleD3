package com.game.simpled3.gUI;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.game.simpled3.R;
import com.game.simpled3.engine.Player;
import com.game.simpled3.engine.gear.slots.Chestpiece;
import com.game.simpled3.engine.gear.slots.Helmet;
import com.game.simpled3.engine.gear.slots.Shoulders;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_BLUE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_ORANGE;


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
        shoulderButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSlotButtonPressed(view);
                        mListener.onEquipmentPageInteraction(view);
                    }
                }
        );

        ImageButton helmButton = (ImageButton) rootView.findViewById(R.id.helmButton);
        helmButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSlotButtonPressed(view);
                        mListener.onEquipmentPageInteraction(view);
                    }
                }
        );

        ImageButton neckButton = (ImageButton) rootView.findViewById(R.id.neckButton);
        neckButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSlotButtonPressed(view);
                        mListener.onEquipmentPageInteraction(view);
                    }
                }
        );

        ImageButton chestButton = (ImageButton) rootView.findViewById(R.id.chestButton);
        chestButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSlotButtonPressed(view);
                        mListener.onEquipmentPageInteraction(view);
                    }
                }
        );

        ImageButton glovesButton = (ImageButton) rootView.findViewById(R.id.glovesButton);
        glovesButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSlotButtonPressed(view);
                        mListener.onEquipmentPageInteraction(view);
                    }
                }
        );

        ImageButton bracerButton = (ImageButton) rootView.findViewById(R.id.bracerButton);
        bracerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSlotButtonPressed(view);
                        mListener.onEquipmentPageInteraction(view);
                    }
                }
        );

        ImageButton beltButton = (ImageButton) rootView.findViewById(R.id.beltButton);
        beltButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSlotButtonPressed(view);
                        mListener.onEquipmentPageInteraction(view);
                    }
                }
        );

        ImageButton pantsButton = (ImageButton) rootView.findViewById(R.id.pantsButton);
        pantsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSlotButtonPressed(view);
                        mListener.onEquipmentPageInteraction(view);
                    }
                }
        );

        ImageButton bootsButton = (ImageButton) rootView.findViewById(R.id.bootButton);
        bootsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSlotButtonPressed(view);
                        mListener.onEquipmentPageInteraction(view);
                    }
                }
        );

        ImageButton leftRingButton = (ImageButton) rootView.findViewById(R.id.leftRingButton);
        leftRingButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSlotButtonPressed(view);
                        mListener.onEquipmentPageInteraction(view);
                    }
                }
        );

        ImageButton rightRingButton = (ImageButton) rootView.findViewById(R.id.rightRingButton);
        rightRingButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSlotButtonPressed(view);
                        mListener.onEquipmentPageInteraction(view);
                    }
                }
        );

        ImageButton leftWeaponButton = (ImageButton) rootView.findViewById(R.id.leftWeaponButton);
        leftWeaponButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSlotButtonPressed(view);
                        mListener.onEquipmentPageInteraction(view);
                    }
                }
        );

        ImageButton rightWeaponButton = (ImageButton) rootView.findViewById(R.id.rightWeaponButton);
        rightWeaponButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSlotButtonPressed(view);
                        mListener.onEquipmentPageInteraction(view);
                    }
                }
        );


        return rootView;
    }

    public void onSlotButtonPressed(View view) {
        if (mListener != null) {
            mListener.onEquipmentPageInteraction(view);
        }
        ItemViewPage itemView = new ItemViewPage(view.getContext());
        switch (view.getId()) {
            case R.id.shoulderButton:
                Shoulders shoulders = new Shoulders(Player.getInstance().getLevel());
                shoulders.setStats("Super Shoulders of the Bear", 2.0, 4.0, ITEM_COLOR_ORANGE);
                itemView.showItem(shoulders);
                itemView.show(view);
                break;
            case R.id.helmButton:
                Helmet helm = new Helmet(Player.getInstance().getLevel());
                helm.setStats("Large Cap of the Whale", 2.0, 2.5, ITEM_COLOR_BLUE);
                itemView.showItem(helm);
                itemView.show(view);
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
                break;
            case R.id.rightWeaponButton:
                break;
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
        void onEquipmentPageInteraction(View view);
    }

}
