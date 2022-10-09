package com.xytong.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xytong.model.vo.ForumVO;
import lombok.Data;

import java.util.List;

@Data
public class ForumResponseDTO {
    String mode;
    @JsonProperty(value = "num_start")
    int numStart;
    @JsonProperty(value = "need_num")
    int needNum;
    @JsonProperty(value = "num_end")
    int numEnd;
    long timestamp;
    @JsonProperty(value = "forum_data")
    List<ForumVO> forumData;

    public void setForumData(List<ForumVO> forumData) {
        this.forumData = forumData;
    }

    public List<ForumVO> getForumData() {
        return forumData;
    }

    public String getTimestamp() {
        return String.valueOf(timestamp);
    }
}
