package com.game.simpled3;

import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

import com.game.simpled3.engine.Game;
import com.game.simpled3.engine.Player;
import com.game.simpled3.engine.gear.Item;
import com.game.simpled3.engine.gear.ItemFactory;
import com.game.simpled3.gUI.EquipmentPage;
import com.game.simpled3.gUI.ItemViewPage;
import com.game.simpled3.gUI.PlayerStatPage;

public class MainActivity extends AppCompatActivity
        implements PlayerStatPage.OnPlayerSheetInteractionListener,
        EquipmentPage.OnEquipmentPageInteractionListener {

    DialogFragment gearPage = null;
    ItemViewPage itemView = null;

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
        Game.getInstance().initialize(res);
        Player.getInstance().initialize(res);
        ItemFactory.getInstance().initialize(res);
        gearPage = new EquipmentPage();
        itemView = new ItemViewPage(getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onPlayerSheetInteraction(View view) {
        switch (view.getId()) {
            case R.id.openGearPageButton:
                showEquipmentPage();

        }
    }

    private void showEquipmentPage() {
        //DialogFragment gearPage = new EquipmentPage();
        gearPage.show(getFragmentManager(), "gear_page");
    }


    @Override
    public void onEquipmentPageInteraction(View view, MotionEvent event, Item item) {
        itemView.setItemToShow(item);
        itemView.show(view);
    }
}
