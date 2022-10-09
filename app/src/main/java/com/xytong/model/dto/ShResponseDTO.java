package com.xytong.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xytong.model.vo.ShVO;
import lombok.Data;

import java.util.List;

@Data
public class ShResponseDTO {
    String mode;
    @JsonProperty(value = "num_start")
    int numStart;
    @JsonProperty(value = "need_num")
    int needNum;
    @JsonProperty(value = "num_end")
    int numEnd;
    long timestamp;
    @JsonProperty(value = "sh_data")
    List<ShVO> shData;

    public void setShData(List<ShVO> shData) {
        this.shData = shData;
    }

    public List<ShVO> getShData() {
        return shData;
    }

    public String getTimestamp() {
        return String.valueOf(timestamp);
    }
}
