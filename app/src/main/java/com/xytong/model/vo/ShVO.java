package com.xytong.model.vo;

import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "sh_list")
public class ShVO extends CardVO implements Serializable {
    private String price = "null";

    public ShVO() {
    }

    ////////////////////////////////////////////////////////
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ShVO)) return false;
        final ShVO other = (ShVO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ShVO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        return result;
    }

    public String toString() {
        return "ShVO(price=" + this.getPrice() + ")";
    }
}
