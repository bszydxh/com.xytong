package com.xytong.model.vo;

import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "sh_list")
public class ShVO extends CardVO implements Serializable {
    private String price = "null";

    ////////////////////////////////////////////////////////
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
