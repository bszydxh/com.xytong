package com.xytong.model.dto.captcha;

import lombok.Data;

@Data
public class CaptchaSendRequestDTO {
    Long timestamp;
    String email;
}
