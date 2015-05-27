package com.game.simpled3.engine.webservice.models;

import java.io.Serializable;

/**
 * Created by JFCaron on 2015-05-27.
 */
public class Stat implements Serializable {
    private static final long serialVersionUID = -4170177547570531827L;

    private String text;
    private String color;
    private String affixType;

    public String getAffixType() {
        return affixType;
    }

    public void setAffixType(String affixType) {
        this.affixType = affixType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
