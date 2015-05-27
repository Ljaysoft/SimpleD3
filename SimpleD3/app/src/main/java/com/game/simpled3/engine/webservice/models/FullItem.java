package com.game.simpled3.engine.webservice.models;

import com.game.simpled3.engine.gear.Item;

import java.io.Serializable;
import java.util.ArrayList;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_BLUE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GRAY;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GREEN;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_ORANGE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_YELLOW;

/**
 * Created by JFCaron on 2015-05-26.
 */
public class FullItem implements Serializable{
    private static final long serialVersionUID = -4170977537570531827L;
    private String id;
    private String name;
    private String icon;
    private String displayColor;
    private String tooltipParams;
    private int requiredLevel;
    private int itemLevel;
    private int bonusAffixes;
    private String typeName;
    private Type type;

    private MinMaxDouble dps;
    private MinMaxDouble attackPerSecond;

    private MinMaxInt minDamage;
    private MinMaxInt maxDamage;

    private Attributes attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDisplayColor() {
        return displayColor;
    }

    public void setDisplayColor(String displayColor) {
        this.displayColor = displayColor;
    }

    public String getTooltipParams() {
        return tooltipParams;
    }

    public void setTooltipParams(String tooltipParams) {
        this.tooltipParams = tooltipParams;
    }

    public int getRequiredLevel() {
        return requiredLevel;
    }

    public void setRequiredLevel(int requiredLevel) {
        this.requiredLevel = requiredLevel;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public MinMaxDouble getDps() {
        return dps;
    }

    public void setDps(MinMaxDouble dps) {
        this.dps = dps;
    }

    public MinMaxDouble getAttackPerSecond() {
        return attackPerSecond;
    }

    public void setAttackPerSecond(MinMaxDouble attackPerSecond) {
        this.attackPerSecond = attackPerSecond;
    }

    public MinMaxInt getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(MinMaxInt minDamage) {
        this.minDamage = minDamage;
    }

    public MinMaxInt getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(MinMaxInt maxDamage) {
        this.maxDamage = maxDamage;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public int getItemLevel() {
        return itemLevel;
    }

    public void setItemLevel(int itemLevel) {
        this.itemLevel = itemLevel;
    }

    public int getBonusAffixes() {
        return bonusAffixes;
    }

    public void setBonusAffixes(int bonusAffixes) {
        this.bonusAffixes = bonusAffixes;
    }

    public Item getItem(){
        Item item = Item.createItem(itemLevel);
        item.setStats(name,0,0,getColorCode(displayColor));
        return item;
    }

    private int getColorCode(String colorStr) {
        if (colorStr == "gray")
            return ITEM_COLOR_GRAY;
        else if (colorStr == "blue")
            return ITEM_COLOR_BLUE;
        else if (colorStr == "yellow")
            return ITEM_COLOR_YELLOW;
        else if (colorStr == "orange")
            return ITEM_COLOR_ORANGE;
        else if (colorStr == "green")
            return ITEM_COLOR_GREEN;
        return -1;
    }
}