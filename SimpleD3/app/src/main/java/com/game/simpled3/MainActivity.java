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
import com.game.simpled3.UI.ItemTooltip;
import com.game.simpled3.UI.LoadingScreen;
import com.game.simpled3.UI.MainPage;
import com.game.simpled3.UI.RewardPage;
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

    final Game game = Game.getInstance();
    final Player player = Player.getInstance();
    final ItemFactory itemFactory = ItemFactory.getInstance();
    DialogFragment gearPage = null;
    ItemTooltip itemView = null;
    LoadingScreen loadingScreen = null;
    DeathPage deathPage = null;
    RewardPage rewardPage = null;
    MainPage mainPage = null;

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
        gearPage = new EquipmentPage();
        loadingScreen = new LoadingScreen();
        ItemFactory.initialize(res);
        Context context = getApplicationContext();
        itemView = new ItemTooltip(context);
        deathPage = new DeathPage();
        rewardPage = new RewardPage();

        mainPage = (MainPage) getFragmentManager().findFragmentById(R.id.mainPage);
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
        showItemTooltip(view, false);
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
