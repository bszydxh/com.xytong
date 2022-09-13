package com.xytong.model.json;

import lombok.Data;

@Data
public class AccessPostJson {
    String username;
    String password;
    long timestamp;
    public String getTimestamp() {
        return String.valueOf(timestamp);
    }

}
