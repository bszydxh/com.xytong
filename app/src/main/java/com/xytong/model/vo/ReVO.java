package com.xytong.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ReVO extends CardVO implements Serializable {
    private String price = "null";

}
