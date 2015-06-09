package com.game.simpled3.engine.webservice.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by JFCaron on 2015-05-27.
 */
public class Attributes implements Serializable {
    private static final long serialVersionUID = -4170977547570531827L;

    private ArrayList<Stat> primary;
    private ArrayList<Stat> secondary;
    private ArrayList<Stat> passive;

    public ArrayList<Stat> getPassive() {
        return passive;
    }

    public void setPassive(ArrayList<Stat> passive) {
        this.passive = passive;
    }

    public ArrayList<Stat> getPrimary() {
        return primary;
    }

    public void setPrimary(ArrayList<Stat> primary) {
        this.primary = primary;
    }

    public ArrayList<Stat> getSecondary() {
        return secondary;
    }

    public void setSecondary(ArrayList<Stat> secondary) {
        this.secondary = secondary;
    }
}

