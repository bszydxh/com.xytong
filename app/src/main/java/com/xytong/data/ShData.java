package com.xytong.data;

import java.io.Serializable;

public class ShData extends CardData implements Serializable {
    private String price= "0.00";

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
