package com.xytong.data.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xytong.data.ShData;

import java.util.List;

import lombok.Data;

@Data
public class ShRequestJson {
    String mode;
    @JsonProperty(value = "num_start")
    int numStart;
    @JsonProperty(value = "need_num")
    int needNum;
    @JsonProperty(value = "num_end")
    int numEnd;
    long timestamp;
    @JsonProperty(value = "sh_data")
    List<ShData> shData;

    public void setShData(List<ShData> shData) {
        this.shData = shData;
    }

    public List<ShData> getShData() {
        return shData;
    }

    public String getTimestamp() {
        return String.valueOf(timestamp);
    }
}
