package com.xytong.model.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    String mode;
    String username;
    String avatar;
    String signature;
    String gender;
    Long birthday_timestamp;
    String phone;
    String email;
}
