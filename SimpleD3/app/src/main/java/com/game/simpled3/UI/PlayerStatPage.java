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
import com.game.simpled3.engine.gear.Loot;
import com.game.simpled3.utils.FontHelper;
import com.game.simpled3.utils.StringManipulation;

/**
 * Player Sheet Fragment
 */
public class PlayerStatPage extends Fragment {

    private OnPlayerSheetInteractionListener mListener;

    private static ProgressBar mProgressBar;
    private static byte mProgressValue = 0;

    // values
    private static TextView mPlayerLevelValueTextView;
    private static TextView mXpToLevelValueTextView;
    private static TextView mPlayerDPSValueTextView;
    private static TextView mPlayerDEFValueTextView;
    private static TextView mPlayerShardsValueTextView;
    private static TextView mPlayerGoldValueTextView;
    private static TextView mDungeonLevelValueTextView;
    private static Button mKillButton;
    private static Button mStartDungeonButton;
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

        mKillButton = (Button) rootView.findViewById(R.id.killButton);
        mKillButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onKillButtonClicked();
                        mListener.onPlayerSheetButtonClicked(view);
                    }
                }
        );
        mStartDungeonButton = (Button) rootView.findViewById(R.id.startDungeonButton);
        mStartDungeonButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onStartDungeonButtonClicked();
                        mListener.onPlayerSheetButtonClicked(view);
                    }
                }
        );
        Button openGearPageButton = (Button) rootView.findViewById(R.id.openGearPageButton);
        openGearPageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onOpenGearPageButtonClicked();
                        mListener.onPlayerSheetButtonClicked(view);
                    }
                }
        );
        FontHelper.applyFont(rootView, false, true);
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

    public void updateUI(Game game, Player player) {
        player.updateDPSandDEF();
        mProgressBar.setProgress(game.updateDungeonProgress());
        mPlayerLevelValueTextView.setText((String.valueOf(player.getLevel())));
        mXpToLevelValueTextView.setText(StringManipulation.formatBigNumbers(player.getXpToLevel()));
        mPlayerDPSValueTextView.setText(StringManipulation.formatBigNumbers(player.getDPS()));
        mPlayerDEFValueTextView.setText(StringManipulation.formatBigNumbers(player.getDEF()));
        mPlayerShardsValueTextView.setText((String.valueOf(player.getShards())));
        mPlayerGoldValueTextView.setText(StringManipulation.formatBigNumbers(player.getGold()));
        mDungeonLevelValueTextView.setText((String.valueOf(game.getDungeonLevelForDisplay())));
        mKillButton.setEnabled(!player.isDead() && (game.isDungeonInProgress() && !game.isDungeonDone()));
        mStartDungeonButton.setEnabled(!player.isDead() && (!game.isDungeonInProgress() || game.isDungeonDone()));
    }

    private void onOpenGearPageButtonClicked() {
    }

    //Kill monsters to gain xp and stuff
    private void onKillButtonClicked() {
        Game game = Game.getInstance();
        Player player = Player.getInstance();

        mListener.onGetReward(game.playerAttacks(player));
        updateUI(game, player);
    }

    private void onStartDungeonButtonClicked() {
        mStartDungeonButton.setEnabled(false);
    }

    public interface OnPlayerSheetInteractionListener {
        void onPlayerSheetButtonClicked(View view);
        void onGetReward(Loot loot);
    }
}
