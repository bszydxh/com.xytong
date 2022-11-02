package com.xytong.model.dto.forum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ForumGetRequestDTO {
    String module;
    String mode;
    @JsonProperty(value = "num_start")
    Integer numStart;
    @JsonProperty(value = "need_num")
    Integer needNum;
    @JsonProperty(value = "num_end")
    Integer numEnd;
    Long timestamp;
}
