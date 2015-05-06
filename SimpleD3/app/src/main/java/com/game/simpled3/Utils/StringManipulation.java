package com.game.simpled3.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by JFCaron on 2015-05-06.
 */
public class StringManipulation {

    // don't instantiate
    private StringManipulation() {
    }

    public static String formatBigNumbers(double value) {
        int power;
        String suffix = " KMBT";
        String formattedNumber = "";

        NumberFormat formatter = new DecimalFormat("#,###.#");
        power = (int)StrictMath.log10(value);
        value = value/(Math.pow(10,(power/3)*3));
        formattedNumber = formatter.format(value);
        formattedNumber = formattedNumber + suffix.charAt(power/3);
        return formattedNumber.length()>4 ?  formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
    }
}
