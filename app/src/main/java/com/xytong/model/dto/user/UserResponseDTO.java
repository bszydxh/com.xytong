package com.xytong.model.dto.user;

import lombok.Data;

@Data
public class UserResponseDTO {
    String mode;
    String username;
    String avatar;
    String signature;
    String gender;
    Long birthday_timestamp;
    String phone;
    String email;
}
