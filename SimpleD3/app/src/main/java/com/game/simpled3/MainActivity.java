package com.game.simpled3;

import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.game.simpled3.UI.DeathPage;
import com.game.simpled3.UI.EquipmentPage;
import com.game.simpled3.UI.ItemButton;
import com.game.simpled3.UI.ItemViewPage;
import com.game.simpled3.UI.PlayerStatPage;
import com.game.simpled3.UI.RewardPage;
import com.game.simpled3.engine.Game;
import com.game.simpled3.engine.Player;
import com.game.simpled3.engine.gear.ItemFactory;
import com.game.simpled3.engine.gear.Loot;

public final class MainActivity extends AppCompatActivity
        implements PlayerStatPage.OnPlayerSheetInteractionListener,
        EquipmentPage.OnEquipmentPageInteractionListener,
        DeathPage.OnDeathPageInteractionListener,
        RewardPage.OnRewardPageInteractionListener,
        Game.GameListener {

    final Game game = Game.getInstance();
    final Player player = Player.getInstance();
    final ItemFactory itemFactory = ItemFactory.getInstance();
    DialogFragment gearPage = null;
    ItemViewPage itemView = null;
    DeathPage deathPage = null;
    RewardPage rewardPage = null;
    PlayerStatPage playerStatPage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            initializeGame();
        }
    }

    void initializeGame() {
        Resources res = getResources();
        Game.initialize(res, this);
        Player.initialize(res);
        ItemFactory.initialize(res);
        gearPage = new EquipmentPage();
        Context context = getApplicationContext();
        itemView = new ItemViewPage(context);
        deathPage = new DeathPage();
        rewardPage = new RewardPage();
        playerStatPage = (PlayerStatPage) getFragmentManager().findFragmentById(R.id.playerStatPage);
    }

    private void showEquipmentPage() {
        gearPage.show(getFragmentManager(), "gear_page");
    }

    private void showItemTooltip(ItemButton button) {
        if (button == null || button.getItem() == null)
            return;
        itemView.show(button);
    }

    //
    // Pages callbacks
    //
    // Main player sheet page
    @Override
    public void onPlayerSheetButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.openGearPageButton:
                showEquipmentPage();
                break;
            case R.id.startDungeonButton:
                game.nextDungeon();
                break;
            case R.id.killButton:
                if (player.isDead()) {
                    deathPage.show(getFragmentManager(), "death_page");
                }
                break;
        }
    }

    @Override
    public void onGetReward(Loot loot) {
        if (loot == null)
            return;
        rewardPage.setLoot(loot);
        rewardPage.show(getFragmentManager(), "reward_page");
    }

    // Equipment page
    @Override
    public void onEquipmentPageInteraction(ItemButton view) {
        showItemTooltip(view);
    }

    // Death page
    @Override
    public void onDeathPageInteraction(View view) {
        switch (view.getId()) {
            case R.id.okDeathButton:
                player.revive();
                deathPage.dismiss();
        }
    }

    // Reward page
    @Override
    public void onRewardPageClosed() {
    }

    @Override
    public void onInspectItem(ItemButton view) {
        showItemTooltip(view);
    }

    // Game callbacks
    @Override
    public void onLootReady() {
    }
}
