package com.game.simpled3.UI;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.game.simpled3.R;
import com.game.simpled3.engine.Game;
import com.game.simpled3.engine.Player;
import com.game.simpled3.utils.FontCache;
import com.game.simpled3.utils.StringManipulation;

/**
 * Player Sheet Fragment
 */
public class PlayerStatPage extends Fragment {

    private static ProgressBar mProgressBar;
    private static int mProgressValue = 0;

    // values
    private static TextView mPlayerLevelValueTextView;
    private static TextView mXpToLevelValueTextView;
    private static TextView mPlayerDPSValueTextView;
    private static TextView mPlayerDEFValueTextView;
    private static TextView mPlayerShardsValueTextView;
    private static TextView mPlayerGoldValueTextView;
    private static TextView mDungeonLevelValueTextView;
    private OnPlayerSheetInteractionListener mListener;

    public PlayerStatPage() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player_stat_page, container, false);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);

        // value textview fonts
        mPlayerLevelValueTextView = (TextView) rootView.findViewById(R.id.lvlValueTextView);
        mXpToLevelValueTextView = (TextView) rootView.findViewById(R.id.xpToLvlValueTextView);
        mPlayerDPSValueTextView = (TextView) rootView.findViewById(R.id.dpsValueTextView);
        mPlayerDEFValueTextView = (TextView) rootView.findViewById(R.id.defValueTextView);
        mPlayerShardsValueTextView = (TextView) rootView.findViewById(R.id.shardValueTextView);
        mPlayerGoldValueTextView = (TextView) rootView.findViewById(R.id.goldValueTextView);
        mDungeonLevelValueTextView = (TextView) rootView.findViewById(R.id.dungeonLvlValueTextView);

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
        FontCache.applyFont(rootView);
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

    private void updateUI(Game game, Player player) {
        mProgressBar.setProgress(mProgressValue);
        mPlayerLevelValueTextView.setText((String.valueOf(player.getLevel())));
        mXpToLevelValueTextView.setText(StringManipulation.formatBigNumbers((double) player.getXpToLevel()));
        mPlayerDPSValueTextView.setText((String.valueOf(player.getDPS())));
        mPlayerDEFValueTextView.setText((String.valueOf(player.getDEF())));
        mPlayerShardsValueTextView.setText((String.valueOf(player.getShards())));
        mPlayerGoldValueTextView.setText(StringManipulation.formatBigNumbers(player.getGold()));
        mDungeonLevelValueTextView.setText((String.valueOf(game.getDungeonLevelForDisplay())));
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


}
