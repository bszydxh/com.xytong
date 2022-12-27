package com.xytong.model.dto.user;

import lombok.Data;

@Data
public class UserRequestDTO {
    String mode;
    String token;
    String username;
    String timestamp;
}
