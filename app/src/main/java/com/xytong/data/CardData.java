package com.xytong.data;

public class CardData {
    private String user_name;
    private String user_avatar_url;
    private String title;
    private String text;

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getUser_avatar_url() {
        return user_avatar_url;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_avatar_url(String user_avatar_url) {
        this.user_avatar_url = user_avatar_url;
    }
}
