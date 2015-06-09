package com.game.simpled3.engine.webservice.models;

import java.io.Serializable;

/**
 * Created by JFCaron on 2015-05-27.
 */
public class MinMaxDouble implements Serializable {

    private static final long serialVersionUID = -2287755241223818435L;
    private double min;
    private double max;

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
