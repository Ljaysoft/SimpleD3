package com.game.simpled3.engine.webservice.models;

import java.io.Serializable;

/**
 * Created by JFCaron on 2015-05-27.
 */
public class MinMaxInt implements Serializable {

    private static final long serialVersionUID = 2258461291869428078L;
    private int min;
    private int max;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
