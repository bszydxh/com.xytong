package com.xytong.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xytong.model.vo.ReVO;
import lombok.Data;

import java.util.List;

@Data
public class ReResponseDTO {
    String mode;
    @JsonProperty(value = "num_start")
    int numStart;
    @JsonProperty(value = "need_num")
    int needNum;
    @JsonProperty(value = "num_end")
    int numEnd;
    long timestamp;
    @JsonProperty(value = "re_data")
    List<ReVO> reData;

    public void setReData(List<ReVO> reData) {
        this.reData = reData;
    }

    public List<ReVO> getReData() {
        return reData;
    }

    public String getTimestamp() {
        return String.valueOf(timestamp);
    }
}
