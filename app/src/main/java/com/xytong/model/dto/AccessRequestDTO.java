package com.xytong.model.dto;

import lombok.Data;


@Data
public class AccessRequestDTO {
    String mode;
    String username;
    String token;
    long timestamp;

    public String getTimestamp() {
        return String.valueOf(timestamp);
    }
}
