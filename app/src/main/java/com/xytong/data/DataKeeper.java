package com.xytong.data;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class DataKeeper<T> extends Application {
    //todo
    List<T> forumList = new ArrayList<>();

    public DataKeeper() {

    }

    public List<T> getForumList() {
        return forumList;
    }

    public void setForumList(List<T> forumList) {
        this.forumList = forumList;
    }

}
