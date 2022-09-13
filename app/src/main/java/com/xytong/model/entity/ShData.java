package com.xytong.model.entity;

import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "sh_list")
public class ShData extends CardData implements Serializable {
    private String price = "null";

    ////////////////////////////////////////////////////////
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
