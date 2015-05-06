package com.game.simpled3;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.game.simpled3.Utils.StringManipulation;
import com.game.simpled3.gameEntities.Game;
import com.game.simpled3.gameEntities.Gear.ItemFactory;
import com.game.simpled3.gameEntities.Player;


public class MainActivity extends ActionBarActivity {

    static Game mMainGame = null;
    static Player mPlayer = null;
    static ItemFactory mItemFactory = null;

    private static ProgressBar mProgressBar;
    private static int mProgressValue = 0;
    private static TextView mPlayerLevelTextView;
    private static TextView mXpToLevelTextView;
    private static TextView mPlayerDPSTextView;
    private static TextView mPlayerDEFTextView;
    private static TextView mPlayerShardsTextView;
    private static TextView mPlayerGoldTextView;
    private static TextView mDungeonLevelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlayerSheetFragment())
                    .commit();
        }
        initializeGame();
    }

    void initializeGame(){
        Resources res = getResources();
        mMainGame = Game.getInstance();
        Game.initialize(res);
        mPlayer = Player.getInstance();
        Player.initialize(res);
        mItemFactory = ItemFactory.getInstance();
        ItemFactory.initialize(res);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlayerSheetFragment extends Fragment {

        public PlayerSheetFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.player_sheet_fragment, container, false);
            mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
            mPlayerLevelTextView = (TextView) rootView.findViewById(R.id.lvlValueTtextView);
            mXpToLevelTextView = (TextView) rootView.findViewById(R.id.xpToLvlValueTextView);
            mPlayerDPSTextView = (TextView) rootView.findViewById(R.id.dpsValueTextView);
            mPlayerDEFTextView = (TextView) rootView.findViewById(R.id.defValueTextView);
            mPlayerShardsTextView = (TextView) rootView.findViewById(R.id.shardValueTextView);
            mPlayerGoldTextView = (TextView) rootView.findViewById(R.id.goldValueTextView);
            mDungeonLevelTextView = (TextView) rootView.findViewById(R.id.dungeonLvlValueTextView);

            return rootView;
        }
    }
    //Kill monsters to gain xp and stuff
    public void onKillButtonClicked(View view) {
        mMainGame.playerAttacks(mPlayer);
        mProgressValue = mMainGame.updateDungeonProgress(mPlayer);
        updateUI();
    }

    public void onStartDungeonButtonClicked(View view) {
        //TODO Start dungeon
    }

    private void updateUI() {
        mProgressBar.setProgress(mProgressValue);
        mPlayerLevelTextView.setText((String.valueOf(mPlayer.getLevel())));
        mXpToLevelTextView.setText(StringManipulation.formatBigNumbers((double) mPlayer.getXpToLevel()));
        mPlayerDPSTextView.setText((String.valueOf(mPlayer.getDPS())));
        mPlayerDEFTextView.setText((String.valueOf(mPlayer.getDEF())));
        mPlayerShardsTextView.setText((String.valueOf(mPlayer.getShards())));
        mPlayerGoldTextView.setText(StringManipulation.formatBigNumbers(mPlayer.getGold()));
        mDungeonLevelTextView.setText((String.valueOf(mMainGame.getDungeonLevelForDisplay())));
    }
}
