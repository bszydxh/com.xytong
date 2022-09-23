package com.xytong.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RePostDTO {
    String module;
    String mode;
    @JsonProperty(value = "num_start")
    int numStart;
    @JsonProperty(value = "need_num")
    int needNum;
    @JsonProperty(value = "num_end")
    int numEnd;
    long timestamp;
}
