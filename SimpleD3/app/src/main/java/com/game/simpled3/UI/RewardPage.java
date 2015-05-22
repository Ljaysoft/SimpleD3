package com.game.simpled3.UI;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.game.simpled3.R;
import com.game.simpled3.engine.Player;
import com.game.simpled3.engine.gear.Item;
import com.game.simpled3.engine.gear.Loot;
import com.game.simpled3.utils.FontHelper;

/**
 * Created by JFCaron on 2015-05-13.
 */
public class RewardPage extends DialogFragment {

    private OnRewardPageInteractionListener mListener;

    private Loot mLoot = null;

    public RewardPage() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_reward_page, container, false);

        Button okButton = (Button) rootView.findViewById(R.id.okRewardButton);
        okButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            mListener.onRewardPageClose();
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
            mListener = (OnRewardPageInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnRewardPageInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setLoot(Loot loot) {
        mLoot = loot;
        Player player = Player.getInstance();
        player.giveShards(loot.getShards());
        player.giveGold(loot.getGold());
        for (Item item : loot.getItems()) {
            player.giveItem(item);
        }
    }

    public interface OnRewardPageInteractionListener {
        void onRewardPageClose();
    }
}
