package com.xytong.model.dto.re;

import android.util.Log;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xytong.model.vo.ReVO;
import lombok.Data;

import java.util.List;

@Data
public class ReGetResponseDTO {
    String mode;
    @JsonProperty(value = "num_start")
    Integer numStart;
    @JsonProperty(value = "need_num")
    Integer needNum;
    @JsonProperty(value = "num_end")
    Integer numEnd;
    Long timestamp;
    @JsonProperty(value = "re_data")
    List<ReVO> reData;

    public void setNumStart(Integer numStart) {
        if (numStart == null) {
            Log.w("ReGetResponseDTO", "NumStart null warning! Check the server database");
            this.numStart = 0;
        } else {
            this.numStart = numStart;
        }
    }

    public void setNumEnd(Integer numEnd) {
        if (numEnd == null) {
            Log.w("ReGetResponseDTO", "NumEnd null warning! Check the server database");
            this.numEnd = 0;
        } else {
            this.numEnd = numEnd;
        }
    }

    public void setNeedNum(Integer needNum) {
        if (needNum == null) {
            Log.w("ReGetResponseDTO", "NeedNum null warning! Check the server database");
            this.needNum = 0;
        } else {
            this.needNum = needNum;
        }
    }
}
