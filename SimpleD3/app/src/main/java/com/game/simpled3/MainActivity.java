package com.game.simpled3;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.game.simpled3.UI.DeathPage;
import com.game.simpled3.UI.EquipmentPage;
import com.game.simpled3.UI.ItemViewPage;
import com.game.simpled3.UI.PlayerStatPage;
import com.game.simpled3.engine.Game;
import com.game.simpled3.engine.Player;
import com.game.simpled3.engine.gear.Item;
import com.game.simpled3.engine.gear.ItemFactory;

public class MainActivity extends AppCompatActivity
        implements PlayerStatPage.OnPlayerSheetInteractionListener,
        EquipmentPage.OnEquipmentPageInteractionListener,
        DeathPage.OnDeathPageInteractionListener {

    DialogFragment gearPage = null;
    ItemViewPage itemView = null;
    DeathPage deathPage = null;
    final Game game = Game.getInstance();
    final Player player = Player.getInstance();
    final ItemFactory itemFactory = ItemFactory.getInstance();

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
        game.initialize(res);
        player.initialize(res);
        itemFactory.initialize(res);
        gearPage = new EquipmentPage();
        itemView = new ItemViewPage(getApplicationContext());
        deathPage = new DeathPage();
    }

    @Override
    public void onPlayerSheetInteraction(View view) {
        switch (view.getId()) {
            case R.id.openGearPageButton:
                showEquipmentPage();
                break;
            case R.id.killButton:
                if(player.isDead()) {
                    deathPage.setCancelable(false);
                    deathPage.show(getFragmentManager(), "death_page");
                }
        }
    }

    private void showEquipmentPage() {
        gearPage.show(getFragmentManager(), "gear_page");
    }


    @Override
    public void onEquipmentPageInteraction(View view, MotionEvent event, Item item) {
        itemView.setItemToShow(item);
        itemView.show(view);
    }

    @Override
    public void onDeathPageInteraction(View view) {
        switch (view.getId()) {
            case R.id.okDeathButton:
                player.revive();
                deathPage.dismiss();
        }
    }
}
