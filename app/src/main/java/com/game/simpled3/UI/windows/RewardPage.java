package com.game.simpled3.UI.windows;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.game.simpled3.R;
import com.game.simpled3.UI.widgets.ItemButton;
import com.game.simpled3.engine.Player;
import com.game.simpled3.engine.gear.Item;
import com.game.simpled3.engine.gear.Loot;
import com.game.simpled3.utils.FontHelper;
import com.game.simpled3.utils.UIHelper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JFCaron on 2015-05-13.
 */
public class RewardPage extends DialogFragment {

    private OnRewardPageInteractionListener mListener;
    private Loot mLoot = null;
    @InjectView(R.id.lootViewLayout)
    LinearLayout mLootViewLayout;

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

        View rootView = inflater.inflate(R.layout.reward_page_layout, container, false);

        ButterKnife.inject(this, rootView);

        Button okButton = (Button) rootView.findViewById(R.id.okRewardButton);
        okButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            onOkButtonClicked();
                                        }
                                    }
        );
        showLoot();
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

    private void onOkButtonClicked() {
        giveLoot();
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
        LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
        for (Item item : items) {
            View rewardItem = inflater.inflate(R.layout.reward_item_layout, null, false);
            final ImageView destroyXImage = (ImageView) rewardItem.findViewById(R.id.destroyXView);
            ItemButton itemIcon = (ItemButton) rewardItem.findViewById(R.id.rewardItemIconView);
            itemIcon.setItem(item);
            itemIcon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        Point point = UIHelper.getRelativePosition(view, event);
                        if (point.x >= view.getMeasuredWidth() / 2
                                && point.y <= view.getMeasuredHeight() / 4) {
                            mListener.onInspectItem((ItemButton) view);
                            return true;
                        }
                        toggleVisible(destroyXImage);
                    }
                    return false;
                }
            });
            mLootViewLayout.addView(rewardItem);
        }
    }

    private void toggleVisible(View view) {
        if (view.getVisibility() == View.VISIBLE)
            view.setVisibility(View.GONE);
        else if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public void setLoot(Loot loot) {
        mLoot = loot;
    }

    private void giveLoot() {
        Player.giveShards(mLoot.getShards());
        Player.giveGold(mLoot.getGold());
        ArrayList<Item> items = mLoot.getItems();
        if (items == null || items.isEmpty())
            return;
        for (int index = 0; index < items.size(); index++) {
            View itemLayout = mLootViewLayout.getChildAt(index);
            final ImageView destroyXImage = (ImageView) itemLayout.findViewById(R.id.destroyXView);
            if (destroyXImage.getVisibility() == View.GONE) {
                Player.giveItem(items.get(index));
            }
        }
    }

    public interface OnRewardPageInteractionListener {
        void onRewardPageClosed();

        void onInspectItem(ItemButton view);
    }
}
