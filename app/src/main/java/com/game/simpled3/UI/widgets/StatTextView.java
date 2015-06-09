package com.game.simpled3.UI.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.game.simpled3.engine.Player;
import com.game.simpled3.engine.gear.Item;
import com.game.simpled3.engine.gear.Stat;
import com.game.simpled3.utils.StringManipulation;

/**
 * Created by JFCaron on 2015-06-05.
 */
public class StatTextView extends TextView {

    public StatTextView(Context context) {
        super(context);
    }

    public StatTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public StatTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setStat(Stat stat, int slotID) {
        Item playerItem = Player.getItems().get(slotID);
        double pStat = 0.0;
        switch (stat.text) {
            case "dps":
                pStat = playerItem.getDPS();
                break;
            case "def":
                pStat = playerItem.getDEF();
                break;
        }

        double diff = stat.value - pStat;
        if (diff == 0) {
            setVisibility(GONE);
            return;
        }

        setVisibility(VISIBLE);

        char sign = ' ';
        if (diff > 0) {
            setTextColor(Color.GREEN);
            sign = '+';
        } else {
            setTextColor(Color.RED);
        }

        setText(sign + StringManipulation.formatBigNumbers(diff) + " " + stat.text);
    }
}
