package com.xytong.model.dto.sh;

import lombok.Data;

@Data
public class ShAddRequestDTO {
    String module;
    String token;
    String timestamp;
    String username;
    String title;
    String text;
    String price;
}
