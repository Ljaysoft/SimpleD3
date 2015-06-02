package com.game.simpled3.engine.webservice.models;

import android.util.Log;

import com.game.simpled3.engine.gear.Item;
import com.game.simpled3.engine.gear.slots.Belt;
import com.game.simpled3.engine.gear.slots.Boots;
import com.game.simpled3.engine.gear.slots.Bracers;
import com.game.simpled3.engine.gear.slots.Chestpiece;
import com.game.simpled3.engine.gear.slots.Gloves;
import com.game.simpled3.engine.gear.slots.Helmet;
import com.game.simpled3.engine.gear.slots.Jewel;
import com.game.simpled3.engine.gear.slots.Pants;
import com.game.simpled3.engine.gear.slots.Ring;
import com.game.simpled3.engine.gear.slots.Shield;
import com.game.simpled3.engine.gear.slots.Shoulders;
import com.game.simpled3.engine.gear.slots.Weapon;

import java.io.Serializable;
import java.util.ArrayList;

import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_BLUE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GRAY;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_GREEN;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_ORANGE;
import static com.game.simpled3.engine.enums.GameEnums.ITEM_COLOR_WHITE;
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

    private String flavorText;
    private String typeName;
    private Type type;
    private ArrayList<String> slots;

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

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
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

    public ArrayList<String> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<String> slots) {
        this.slots = slots;
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
        Item item = null;
        if (slots.size() > 1 && slots.get(1) == "right-hand")
            item = new Weapon(itemLevel, true);
        else {
            switch (slots.get(0)) {
                case "waist":
                    item = new Belt(itemLevel);
                    break;
                case "shoulder":
                    item = new Shoulders(itemLevel);
                    break;
                case "head":
                    item = new Helmet(itemLevel);
                    break;
                case "chest":
                    item = new Chestpiece(itemLevel);
                    break;
                case "neck":
                    item = new Jewel(itemLevel);
                    break;
                case "hands":
                    item = new Gloves(itemLevel);
                    break;
                case "bracers":
                    item = new Bracers(itemLevel);
                    break;
                case "legs":
                    item = new Pants(itemLevel);
                    break;
                case "feet":
                    item = new Boots(itemLevel);
                    break;
                case "left-finger":
                    item = new Ring(itemLevel, false);
                    break;
                case "right-finger":
                    item = new Ring(itemLevel, true);
                    break;
                case "left-hand":
                    item = new Weapon(itemLevel, false);
                    break;
                case "right-hand":
                    item = new Shield(itemLevel);
            }
            try {
                item.setName(name);
                item.setColor(getColorCode(displayColor));
                item.setFlavorText(flavorText);
                item.setImageID(icon);
            } catch (NullPointerException e) {
                Log.e(FullItem.class.getSimpleName(),e.toString());
                e.printStackTrace();
            }
        }
        return item;
    }

    private int getColorCode(String colorStr) {
        int color = -1;
        switch (colorStr) {
            case "blue":
                color = ITEM_COLOR_BLUE;
                break;
            case "white":
                color = ITEM_COLOR_WHITE;
                break;
            case "yellow":
                color = ITEM_COLOR_YELLOW;
                break;
            case "orange":
                color = ITEM_COLOR_ORANGE;
                break;
            case "green":
            case "set":
                color = ITEM_COLOR_GREEN;
                break;
            case "grey":
            case "gray":
            default:
                color = ITEM_COLOR_GRAY;
                break;
        }
        return color;
    }
}
