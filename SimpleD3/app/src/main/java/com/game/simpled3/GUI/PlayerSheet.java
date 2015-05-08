package com.game.simpled3.GUI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.game.simpled3.R;
import com.game.simpled3.Utils.StringManipulation;
import com.game.simpled3.gameEntities.Game;
import com.game.simpled3.gameEntities.Player;

/**
 * Player Sheet Fragment
 */
public class PlayerSheet extends Fragment {

    private OnPlayerSheetInteractionListener mListener;

    private static ProgressBar mProgressBar;
    private static int mProgressValue = 0;
    private static TextView mPlayerLevelTextView;
    private static TextView mXpToLevelTextView;
    private static TextView mPlayerDPSTextView;
    private static TextView mPlayerDEFTextView;
    private static TextView mPlayerShardsTextView;
    private static TextView mPlayerGoldTextView;
    private static TextView mDungeonLevelTextView;

    public PlayerSheet() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_sheet, container, false);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        mPlayerLevelTextView = (TextView) rootView.findViewById(R.id.lvlValueTtextView);
        mXpToLevelTextView = (TextView) rootView.findViewById(R.id.xpToLvlValueTextView);
        mPlayerDPSTextView = (TextView) rootView.findViewById(R.id.dpsValueTextView);
        mPlayerDEFTextView = (TextView) rootView.findViewById(R.id.defValueTextView);
        mPlayerShardsTextView = (TextView) rootView.findViewById(R.id.shardValueTextView);
        mPlayerGoldTextView = (TextView) rootView.findViewById(R.id.goldValueTextView);
        mDungeonLevelTextView = (TextView) rootView.findViewById(R.id.dungeonLvlValueTextView);
        Button killButton = (Button) rootView.findViewById(R.id.killButton);
        killButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onKillButtonClicked();
                        mListener.onPlayerSheetInteraction(view);
                    }
                }
        );
        Button startButton = (Button) rootView.findViewById(R.id.startDungeonButton);
        startButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onStartDungeonButtonClicked();
                        mListener.onPlayerSheetInteraction(view);
                    }
                }
        );
        Button openGearPageButton = (Button) rootView.findViewById(R.id.openGearPageButton);
        openGearPageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                public void onClick(View view) {
                        onOpenGearPageButtonClicked();
                        mListener.onPlayerSheetInteraction(view);
                    }
                }
        );

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPlayerSheetInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPlayerSheetInteractionListener");
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
    public interface OnPlayerSheetInteractionListener {
        void onPlayerSheetInteraction(View view);
    }

    private void updateUI(Game game, Player player) {
        mProgressBar.setProgress(mProgressValue);
        mPlayerLevelTextView.setText((String.valueOf(player.getLevel())));
        mXpToLevelTextView.setText(StringManipulation.formatBigNumbers((double) player.getXpToLevel()));
        mPlayerDPSTextView.setText((String.valueOf(player.getDPS())));
        mPlayerDEFTextView.setText((String.valueOf(player.getDEF())));
        mPlayerShardsTextView.setText((String.valueOf(player.getShards())));
        mPlayerGoldTextView.setText(StringManipulation.formatBigNumbers(player.getGold()));
        mDungeonLevelTextView.setText((String.valueOf(game.getDungeonLevelForDisplay())));
    }

    private void onOpenGearPageButtonClicked() {

    }

    //Kill monsters to gain xp and stuff
    private void onKillButtonClicked() {
        Game game = Game.getInstance();
        Player player = Player.getInstance();

        game.playerAttacks(player);
        mProgressValue = game.updateDungeonProgress(player);
        updateUI(game, player);
    }

    private void onStartDungeonButtonClicked() {
        //TODO Start dungeon
    }


}
