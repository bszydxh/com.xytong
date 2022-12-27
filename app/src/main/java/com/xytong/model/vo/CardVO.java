package com.xytong.model.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;


@Data
public class CardVO implements Serializable {
    private Long cid;//卡片消息id
    @JsonProperty("user_name")
    private String userName = "null";
    @JsonProperty("user_avatar")
    private String userAvatarUrl = null;
    private String title = "(没有标题)";
    private String text = "";
    private Long timestamp = 0L;

    public String getDate() {
        long startTime;
        if (timestamp < 1000000000000L) {
            startTime = timestamp * 1000;//判断时间戳类型
        } else {
            startTime = timestamp;
        }
        long endTime = System.currentTimeMillis();  //获取毫秒数
        long timeDifference = endTime - startTime;
        long second = timeDifference / 1000;    //计算秒
        if (startTime == 0) {
            return "null";
        }
        if (second < 60) {
            return second + " 秒前";
        } else {
            long minute = second / 60;
            if (minute < 60) {
                return minute + "分钟前";
            } else {
                long hour = minute / 60;
                if (hour < 24) {
                    return hour + "小时前";
                } else {
                    long day = hour / 24;
                    if (day < 30) {
                        return day + "天前";
                    } else {
                        long month = day / 30;
                        if (month < 12) {
                            return month + "月前";
                        } else {
                            long year = day / 365;
                            return year + "年前";
                        }
                    }

                }
            }
        }

    }

}
