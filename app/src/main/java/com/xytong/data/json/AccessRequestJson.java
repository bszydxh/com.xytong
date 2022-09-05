package com.xytong.data.json;

import lombok.Data;


@Data
public class AccessRequestJson {
    String username;
    String token;
    int id;
    long timestamp;

    public String getId() {
        return String.valueOf(id);
    }

    public String getTimestamp() {
        return String.valueOf(timestamp);
    }
}
