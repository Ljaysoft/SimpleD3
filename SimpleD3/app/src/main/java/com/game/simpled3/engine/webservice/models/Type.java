package com.game.simpled3.engine.webservice.models;

import java.io.Serializable;

/**
 * Created by JFCaron on 2015-05-27.
 */
public class Type implements Serializable {
    private static final long serialVersionUID = 5940957116321853228L;
    private String id;
    private boolean twoHanded;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isTwoHanded() {
        return twoHanded;
    }

    public void setTwoHanded(boolean twoHanded) {
        this.twoHanded = twoHanded;
    }
}