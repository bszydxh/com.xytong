package com.xytong.model.dto.access;

import lombok.Data;


@Data
public class AccessCheckResponseDTO {
    String mode;
    String username;
    String token;
    Long timestamp;
}
