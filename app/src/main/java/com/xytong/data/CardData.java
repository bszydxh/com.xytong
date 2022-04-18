package com.xytong.data;

public class CardData {
    private String userName = "null";
    private String userAvatarUrl = "http://127.0.0.1";
    private String title = "null";
    private String text = "null";

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
