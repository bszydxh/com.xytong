package com.xytong.data;


import java.io.Serializable;

public class CardData implements Serializable {
    private String userName = "null";
    private String userAvatarUrl = null;
    private String title = "null";
    private String text = "null";
    private Long timestamp = 0L;

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getDate() {
        long startTime = timestamp * 1000;
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

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }
}
