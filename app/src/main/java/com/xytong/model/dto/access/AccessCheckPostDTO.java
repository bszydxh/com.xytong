package com.xytong.model.dto.access;

import lombok.Data;

@Data
public class AccessCheckPostDTO {
    String mode;
    String username;
    String password;
    String token;
    Long timestamp;

}
