package com.game.simpled3.UI;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.game.simpled3.R;
import com.game.simpled3.engine.Game;
import com.game.simpled3.engine.Player;
import com.game.simpled3.engine.gear.ItemFactory;
import com.game.simpled3.engine.gear.Loot;
import com.game.simpled3.utils.FontHelper;
import com.game.simpled3.utils.StringManipulation;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Player Sheet Fragment
 */
public class MainPage extends Fragment {

    private OnPlayerSheetInteractionListener mListener;
    final Handler mHandler = new Handler();
    private Timer mTimer = new Timer();

    private static final int UPDATE_TIME_MS = 200;

    private static byte mProgressValue = 0;

    @InjectView(R.id.lvlValueTextView)
    TextView mPlayerLevelValueTextView;
    @InjectView(R.id.xpToLvlValueTextView)
    TextView mXpToLevelValueTextView;
    @InjectView(R.id.dpsValueTextView)
    TextView mPlayerDPSValueTextView;
    @InjectView(R.id.defValueTextView)
    TextView mPlayerDEFValueTextView;
    @InjectView(R.id.shardValueTextView)
    TextView mPlayerShardsValueTextView;
    @InjectView(R.id.goldValueTextView)
    TextView mPlayerGoldValueTextView;
    @InjectView(R.id.dungeonLvlValueTextView)
    TextView mDungeonLevelValueTextView;
    @InjectView(R.id.killButton)
    Button mKillButton;
    @InjectView(R.id.startDungeonButton)
    Button mStartDungeonButton;
    @InjectView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public MainPage() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_page_layout, container, false);

        ButterKnife.inject(this, rootView);

        mKillButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onKillButtonClicked();
                        mListener.onPlayerSheetButtonClicked(view);
                    }
                }
        );
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Start updateUI task
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        MainPage mainPage = (MainPage) getFragmentManager().findFragmentById(R.id.mainPage);
                        mainPage.updateUI();
                    }
                });
            }
        };
        mTimer.schedule(task,0,UPDATE_TIME_MS);
    }

    @Override
    public void onStop() {
        super.onStop();
        mTimer.cancel();
        mTimer.purge();
    }

    public void updateUI() {
        Game game = Game.getInstance();
        Player player = Player.getInstance();
        player.updateDPSandDEF();
        mProgressBar.setProgress(game.updateDungeonProgress());
        mPlayerLevelValueTextView.setText((String.valueOf(player.getLevel())));
        mXpToLevelValueTextView.setText(StringManipulation.formatBigNumbers(player.getXpToLevel()));
        mPlayerDPSValueTextView.setText(StringManipulation.formatBigNumbers(player.getDPS()));
        mPlayerDEFValueTextView.setText(StringManipulation.formatBigNumbers(player.getDEF()));
        mPlayerShardsValueTextView.setText((String.valueOf(player.getShards())));
        mPlayerGoldValueTextView.setText(StringManipulation.formatBigNumbers(player.getGold()));
        mDungeonLevelValueTextView.setText((String.valueOf(game.getDungeonLevelForDisplay())));
        mStartDungeonButton.setEnabled(!player.isDead() && !game.isDungeonStarted() && ItemFactory.isReady());
        mKillButton.setEnabled(!player.isDead() && game.isDungeonStarted() && ItemFactory.areItemsBuilt());
    }

    private void onOpenGearPageButtonClicked() {
    }

    //Kill monsters to gain xp and stuff
    private void onKillButtonClicked() {
        Game game = Game.getInstance();
        Player player = Player.getInstance();
        mListener.onGetReward(game.playerAttacks(player));
    }

    private void onStartDungeonButtonClicked() {
        mStartDungeonButton.setEnabled(false);
    }

    public interface OnPlayerSheetInteractionListener {
        void onPlayerSheetButtonClicked(View view);

        void onGetReward(Loot loot);
    }
}
