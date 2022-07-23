package com.xytong.downloader;

import android.util.Log;

import androidx.annotation.NonNull;

import com.xytong.data.ForumData;
import com.xytong.data.ShData;
import com.xytong.io.Poster;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataDownloader {
    public static List<ForumData> getForumDataList(@NonNull String mode, int start, int end) {
        List<ForumData> data = new ArrayList<>();
        switch (mode) {
            case "newest": {
                int need_num = end - start + 1;
                String path = "http://rap2api.taobao.org/app/mock/data/2255088";
                String text = "{\n" +
                        "  \"module\": \"forum\",\n" +
                        "  \"mode\": \"newest\",\n" +
                        "  \"need_num\": " + need_num + ",\n" +
                        "  \"num_start\": " + start + ",\n" +
                        "  \"num_end\": " + end + ",\n" +
                        "  \"timestamp\": 1650098900\n" +
                        "}";
                Poster<List<ForumData>> poster = new Poster<>(path, text);
                poster.setHttpListener(result -> {
                    List<ForumData> data_init = new ArrayList<>();
                    try {
                        JSONObject root = new JSONObject(result);
                        JSONArray forum_data_array = root.getJSONArray("forum_data");
                        for (int i = 0; i < forum_data_array.length(); i++) {
                            ForumData forumData = new ForumData();
                            JSONObject forum_data = forum_data_array.getJSONObject(i);
                            forumData.setUserAvatarUrl(forum_data.getString("user_avatar"));
                            forumData.setUserName(forum_data.getString("user_name"));
                            forumData.setTitle(forum_data.getString("title"));
                            forumData.setText(forum_data.getString("text"));
                            forumData.setLikes(forum_data.getInt("likes"));
                            forumData.setTimestamp(Long.valueOf(forum_data.getString("timestamp")));
                            forumData.setComments(forum_data.getInt("comments"));
                            forumData.setForwarding(forum_data.getInt("forwarding"));
                            data_init.add(forumData);
                            Log.e("DataDownloader:getForumData", "get ok");
                        }
                    } catch (Exception e) {
                        Log.e("DataDownloader:getForumData", "error");
                        e.printStackTrace();
                    }
                    return data_init;//异步完成数据传递
                });
                data = poster.post();
                break;
            }
        }
        return data;
    }

    public static List<ShData> getShDataList(@NonNull String mode, int start, int end) {
        List<ShData> data = new ArrayList<>();
        return data;
    }
}
