package com.xytong.model.dto.access;

import lombok.Data;

@Data
public class AccessCheckRequestDTO {
    String mode;
    String username;
    String password;
    String token;
    Long timestamp;

}
