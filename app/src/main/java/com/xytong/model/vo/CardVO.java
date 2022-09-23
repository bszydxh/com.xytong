package com.xytong.model.vo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@Entity
public class CardVO implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id = -1;//主键
    @Ignore
    private String uid;//用户id，TODO
    @Ignore
    private String cid;//卡片消息id，TODO
    @ColumnInfo(name = "user_name")
    @JsonProperty(value = "user_name")
    private String userName = "null";
    @JsonProperty(value = "user_avatar")
    @ColumnInfo(name = "user_avatar")
    private String userAvatarUrl = null;
    @ColumnInfo
    private String title = "(没有标题)";
    @ColumnInfo
    private String text = "";
    @ColumnInfo
    private Long timestamp = 0L;
    ////////////////////////////////////////////////////////

    public int getId() {
        return id;
    }

    @Deprecated
    public String getCid() {
        return cid;
    }//TODO

    @Deprecated
    public String getUid() {
        return uid;
    }//TODO

    public String getText() {
        if (text == null) {
            return null;
        } else {
            return text.trim();
        }
    }

    public String getTitle() {
        if (title == null) {
            return null;
        } else {
            return title.trim();
        }
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public String getUserName() {
        if (userName == null) {
            return null;
        } else {
            return userName.trim();
        }
    }

    public Long getTimestamp() {
        return timestamp;
    }

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

    public void setId(int id) {
        this.id = id;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
