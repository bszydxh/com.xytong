package com.xytong.model.dto;

import lombok.Data;


@Data
public class AccessResponseDTO {
    String mode;
    String username;
    String token;
    long timestamp;

    public String getTimestamp() {
        return String.valueOf(timestamp);
    }
}
