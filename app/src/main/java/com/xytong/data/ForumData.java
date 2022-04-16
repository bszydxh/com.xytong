package com.xytong.data;

public class ForumData extends CardData {
    private int likes = 0;
    private int comments = 0;
    private int forwarding = 0;

    public int getComments() {
        return comments;
    }

    public int getForwarding() {
        return forwarding;
    }

    public int getLikes() {
        return likes;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void setForwarding(int forwarding) {
        this.forwarding = forwarding;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
