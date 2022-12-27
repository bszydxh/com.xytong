package com.xytong.model.dto.access;

import lombok.Data;

@Data
public class AccessSignupRequestDTO {
    Long timestamp;
    String username;
    String email;
    String password;
    String captchaCode;
}
