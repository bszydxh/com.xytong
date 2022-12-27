package com.xytong.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ShVO extends CardVO implements Serializable {
    private String price = "null";
    @JsonProperty("image_url")
    private String imageUrl;
}
