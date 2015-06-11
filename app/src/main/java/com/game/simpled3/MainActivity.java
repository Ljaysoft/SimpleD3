package com.game.simpled3;

import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.game.simpled3.UI.widgets.ItemButton;
import com.game.simpled3.UI.windows.DeathPage;
import com.game.simpled3.UI.windows.EquipmentPage;
import com.game.simpled3.UI.windows.ItemTooltip;
import com.game.simpled3.UI.windows.LoadingScreen;
import com.game.simpled3.UI.windows.MainPage;
import com.game.simpled3.UI.windows.RewardPage;
import com.game.simpled3.engine.Game;
import com.game.simpled3.engine.Player;
import com.game.simpled3.engine.gear.ItemFactory;
import com.game.simpled3.engine.gear.Loot;

public final class MainActivity extends AppCompatActivity
        implements MainPage.OnPlayerSheetInteractionListener,
        EquipmentPage.OnEquipmentPageInteractionListener,
        DeathPage.OnDeathPageInteractionListener,
        RewardPage.OnRewardPageInteractionListener,
        LoadingScreen.OnLoadingScreenInteractionListener,
        Game.GameListener {

    private DialogFragment gearPage = null;
    private ItemTooltip itemView = null;
    private LoadingScreen loadingScreen = null;
    private DeathPage deathPage = null;
    private RewardPage rewardPage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            initializeGame();
        }
    }

    private void initializeGame() {
        Resources res = getResources();
        Game.initialize(res, this);
        Player.initialize(res);
        gearPage = new EquipmentPage();
        loadingScreen = new LoadingScreen();
        ItemFactory.initialize(res);
        Context context = getApplicationContext();
        itemView = new ItemTooltip(context);
        deathPage = new DeathPage();
        rewardPage = new RewardPage();
    }

    private void showEquipmentPage() {
        gearPage.show(getFragmentManager(), "gear_page");
    }

    private void showItemTooltip(ItemButton button, boolean compare) {
        if (button == null || button.getItem() == null)
            return;
        itemView.show(button, compare);
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
                Game.nextDungeon();
                break;
            case R.id.killButton:
                if (Player.isDead()) {
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
        showItemTooltip(view, false);
    }

    // Death page
    @Override
    public void onDeathPageInteraction(View view) {
        switch (view.getId()) {
            case R.id.okDeathButton:
                Player.revive();
                deathPage.dismiss();
        }
    }

    // Reward page
    @Override
    public void onRewardPageClosed() {
    }

    @Override
    public void onInspectItem(ItemButton view) {
        showItemTooltip(view, true);
    }

    // Loading screen
    @Override
    public void OnLoadingDone() {
        loadingScreen.dismiss();
    }

    // Game callbacks
    @Override
    public void onLootReady() {
    }

    @Override
    public void onItemFactoryInitStarted() {
        loadingScreen.show(getFragmentManager(), "loading_screen");
    }

}
