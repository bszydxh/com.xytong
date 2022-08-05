package com.xytong.data;

import java.io.Serializable;

public class ReData extends CardData implements Serializable {
    private String price = "0.00";

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }
}
