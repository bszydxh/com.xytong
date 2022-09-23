package com.xytong.model.dto;

import lombok.Data;

@Data
public class AccessPostDTO {
    String mode;
    String username;
    String password;
    String token;
    long timestamp;

    public String getTimestamp() {
        return String.valueOf(timestamp);
    }
}
