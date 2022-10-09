package com.xytong.model.vo;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 可缓存的vo设计，交由room进行缓存
 */
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

    public CardVO() {
    }
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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CardVO)) return false;
        final CardVO other = (CardVO) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$uid = this.getUid();
        final Object other$uid = other.getUid();
        if (this$uid == null ? other$uid != null : !this$uid.equals(other$uid)) return false;
        final Object this$cid = this.getCid();
        final Object other$cid = other.getCid();
        if (this$cid == null ? other$cid != null : !this$cid.equals(other$cid)) return false;
        final Object this$userName = this.getUserName();
        final Object other$userName = other.getUserName();
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        final Object this$userAvatarUrl = this.getUserAvatarUrl();
        final Object other$userAvatarUrl = other.getUserAvatarUrl();
        if (this$userAvatarUrl == null ? other$userAvatarUrl != null : !this$userAvatarUrl.equals(other$userAvatarUrl))
            return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$text = this.getText();
        final Object other$text = other.getText();
        if (this$text == null ? other$text != null : !this$text.equals(other$text)) return false;
        final Object this$timestamp = this.getTimestamp();
        final Object other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !this$timestamp.equals(other$timestamp)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CardVO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        final Object $uid = this.getUid();
        result = result * PRIME + ($uid == null ? 43 : $uid.hashCode());
        final Object $cid = this.getCid();
        result = result * PRIME + ($cid == null ? 43 : $cid.hashCode());
        final Object $userName = this.getUserName();
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $userAvatarUrl = this.getUserAvatarUrl();
        result = result * PRIME + ($userAvatarUrl == null ? 43 : $userAvatarUrl.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $text = this.getText();
        result = result * PRIME + ($text == null ? 43 : $text.hashCode());
        final Object $timestamp = this.getTimestamp();
        result = result * PRIME + ($timestamp == null ? 43 : $timestamp.hashCode());
        return result;
    }

    public String toString() {
        return "CardVO(id=" + this.getId() + ", uid=" + this.getUid() + ", cid=" + this.getCid() + ", userName=" + this.getUserName() + ", userAvatarUrl=" + this.getUserAvatarUrl() + ", title=" + this.getTitle() + ", text=" + this.getText() + ", timestamp=" + this.getTimestamp() + ")";
    }
}
