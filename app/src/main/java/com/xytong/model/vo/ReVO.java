package com.xytong.model.vo;

import java.io.Serializable;

public class ReVO extends CardVO implements Serializable {
    private String price = "null";

    public ReVO() {
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
        if (!(o instanceof ReVO)) return false;
        final ReVO other = (ReVO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ReVO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        return result;
    }

    public String toString() {
        return "ReVO(price=" + this.getPrice() + ")";
    }
}
