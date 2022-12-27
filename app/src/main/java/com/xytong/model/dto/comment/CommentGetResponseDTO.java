package com.xytong.model.dto.comment;

import android.util.Log;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xytong.model.vo.CommentVO;
import lombok.*;

import java.util.List;

@Data
public class CommentGetResponseDTO {
    String mode;
    @JsonProperty("num_start")
    Integer numStart;
    @JsonProperty("need_num")
    Integer needNum;
    @JsonProperty("num_end")
    Integer numEnd;
    Long timestamp;
    @JsonProperty("comment_data")
    List<CommentVO> data;

    public void setNumStart(Integer numStart) {
        if (numStart == null) {
            Log.w("ForumGetResponseDTO", "NumStart null warning! Check the server database");
            this.numStart = 0;
        } else {
            this.numStart = numStart;
        }
    }

    public void setNumEnd(Integer numEnd) {
        if (numEnd == null) {
            Log.w("ForumGetResponseDTO", "NumEnd null warning! Check the server database");
            this.numEnd = 0;
        } else {
            this.numEnd = numEnd;
        }
    }

    public void setNeedNum(Integer needNum) {
        if (needNum == null) {
            Log.w("ForumGetResponseDTO", "NeedNum null warning! Check the server database");
            this.needNum = 0;
        } else {
            this.needNum = needNum;
        }
    }
}
