package com.game.simpled3;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;


import com.game.simpled3.GUI.EquipmentPage;
import com.game.simpled3.GUI.PlayerSheet;
import com.game.simpled3.gameEntities.Game;
import com.game.simpled3.gameEntities.Gear.ItemFactory;
import com.game.simpled3.gameEntities.Player;


public class MainActivity extends AppCompatActivity
                          implements PlayerSheet.OnPlayerSheetInteractionListener,
                                     EquipmentPage.OnEquipmentPageInteractionListener {
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            initializeGame();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.mainActivityContent, new PlayerSheet());
            transaction.add(new EquipmentPage(), "gear_page");
            transaction.commit();
        }
    }

    void initializeGame() {
        Resources res = getResources();
        Game.getInstance().initialize(res);
        Player.getInstance().initialize(res);
        ItemFactory.getInstance().initialize(res);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onEquipmentPageInteraction(View view) {

    }

    @Override
    public void onPlayerSheetInteraction(View view) {
        switch(view.getId()){
            case R.id.openGearPageButton:
                showEquipmentPage();

        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showEquipmentPage() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        View dialoglayout = getLayoutInflater().inflate(R.layout.fragment_gear_page, null);
        alertDialogBuilder.setView(dialoglayout);
        /*alertDialogBuilder.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });*/
        AlertDialog gearPage = alertDialogBuilder.create();
        gearPage.show();
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
}
