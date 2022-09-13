package com.xytong.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xytong.model.entity.ForumData;

import java.util.List;

import lombok.Data;

@Data
public class ForumRequestJson {
    String mode;
    @JsonProperty(value = "num_start")
    int numStart;
    @JsonProperty(value = "need_num")
    int needNum;
    @JsonProperty(value = "num_end")
    int numEnd;
    long timestamp;
    @JsonProperty(value = "forum_data")
    List<ForumData> forumData;

    public void setForumData(List<ForumData> forumData) {
        this.forumData = forumData;
    }

    public List<ForumData> getForumData() {
        return forumData;
    }

    public String getTimestamp() {
        return String.valueOf(timestamp);
    }
}
