package com.game.simpled3.UI;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.game.simpled3.R;
import com.game.simpled3.engine.Player;
import com.game.simpled3.engine.gear.Item;
import com.game.simpled3.engine.gear.Loot;
import com.game.simpled3.utils.FontHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JFCaron on 2015-05-13.
 */
public class RewardPage extends DialogFragment {

    private OnRewardPageInteractionListener mListener;
    private Loot mLoot = null;
    private Context mContext = null;
    private Player mPlayer = null;
    @InjectView(R.id.lootViewLayout)
    LinearLayout mLootViewLayout;

    private String mediaSourceURL = "http://media.blizzard.com/d3/icons/items/large/";

    public RewardPage(Context ctx, Player player) {
        mContext = ctx;
        mPlayer = player;
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

        ButterKnife.inject(this, rootView);

        Button okButton = (Button) rootView.findViewById(R.id.okRewardButton);
        okButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            onOkButtonClicked();
                                        }
                                    }
        );
        FontHelper.applyFont(rootView, false, true);
        showLoot();
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

    private void onOkButtonClicked() {
        giveLoot(mPlayer);
        mListener.onRewardPageClosed();
        dismiss();
    }

    private void showLoot() {
        if (mLoot == null)
            return;
        ArrayList<Item> items = mLoot.getItems();
        if (items == null || items.isEmpty())
            return;
        mLootViewLayout.removeAllViewsInLayout();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        for (Item item : items) {
            View rewardItem = inflater.inflate(R.layout.reward_item_view, null, false);
            ItemButton itemIcon = (ItemButton) rewardItem.findViewById(R.id.rewardItemIconView);
            itemIcon.setItem(item);
            itemIcon.setOnTouchListener(new View.OnTouchListener() {
                                            @Override
                                            public boolean onTouch(View view, MotionEvent event) {
                                                if (event.getAction() != MotionEvent.ACTION_UP) {
                                                    return false;
                                                }
                                                mListener.onInspectItem((ItemButton) view);
                                                return true;
                                            }
                                        }
            );
            if (item.isIconSquare()) {
                Picasso.with(mContext).load(mediaSourceURL + item.getImageID() + ".png").resizeDimen(R.dimen.item_icon_width, R.dimen.item_icon_width).into(itemIcon);
            } else {
                Picasso.with(mContext).load(mediaSourceURL + item.getImageID() + ".png").resizeDimen(R.dimen.item_icon_width, R.dimen.item_big_icon_height).into(itemIcon);
            }
            mLootViewLayout.addView(rewardItem);
        }
    }

    public void setLoot(Loot loot) {
        mLoot = loot;
    }

    public void giveLoot(Player player) {
        if (player == null)
            return;
        player.giveShards(mLoot.getShards());
        player.giveGold(mLoot.getGold());
        ArrayList<Item> items = mLoot.getItems();
        if (items == null || items.isEmpty())
            return;
        for (int index = 0; index < items.size(); index++) {
            LinearLayout itemLayout = (LinearLayout) mLootViewLayout.getChildAt(index);
            ToggleButton replaceToggleButton = (ToggleButton) itemLayout.findViewById(R.id.replaceToggleButton);
            if (replaceToggleButton.isChecked()) {
                player.giveItem(items.get(index));
            }
        }
    }

    public interface OnRewardPageInteractionListener {
        void onRewardPageClosed();

        void onInspectItem(ItemButton view);
    }
}
