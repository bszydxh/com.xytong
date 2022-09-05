package com.xytong.downloader;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.xytong.data.CommentData;
import com.xytong.data.ForumData;
import com.xytong.data.ReData;
import com.xytong.data.ShData;
import com.xytong.data.SharedPreferences.SettingSP;
import com.xytong.io.Poster;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataDownloader {
    public static List<ForumData> getForumDataList(Context context, @NonNull String mode, int start, int end) {
        List<ForumData> data = new ArrayList<>();
        switch (mode) {
            case "newest": {
                int need_num = end - start + 1;
                String text = "{\n" +
                        "  \"module\": \"forum\",\n" +
                        "  \"mode\": \"newest\",\n" +
                        "  \"need_num\": " + need_num + ",\n" +
                        "  \"num_start\": " + start + ",\n" +
                        "  \"num_end\": " + end + ",\n" +
                        "  \"timestamp\": "+System.currentTimeMillis()+"\n" +
                        "}";
                Poster<List<ForumData>> poster = new Poster<>(SettingSP.getForumUrl(context), text);
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
                        }
                        Log.i("DataDownloader.getForumData()", "get ok");
                    } catch (Exception e) {
                        Log.e("DataDownloader.getForumData()", "error");
                        e.printStackTrace();
                    }
                    return data_init;//异步完成数据传递
                });
                data = poster.post();//由于该方法被包裹在新线程进行，该线程会等待网络进程
                break;
            }
        }
        return data;
    }

    public static List<ReData> getReDataList(Context context, @NonNull String mode, int start, int end) {
        List<ReData> data = new ArrayList<>();
        switch (mode) {
            case "newest": {
                int need_num = end - start + 1;
                String text = "{\n" +
                        "  \"module\": \"run_errands\",\n" +
                        "  \"mode\": \"newest\",\n" +
                        "  \"need_num\": " + need_num + ",\n" +
                        "  \"num_start\": " + start + ",\n" +
                        "  \"num_end\": " + end + ",\n" +
                        "  \"timestamp\": 1650098900\n" +
                        "}";
                Poster<List<ReData>> poster = new Poster<>(SettingSP.getReUrl(context), text);
                poster.setHttpListener(result -> {
                    List<ReData> data_init = new ArrayList<>();
                    try {
                        JSONObject root = new JSONObject(result);
                        JSONArray re_data_array = root.getJSONArray("re_data");
                        for (int i = 0; i < re_data_array.length(); i++) {
                            ReData reData = new ReData();
                            JSONObject re_data = re_data_array.getJSONObject(i);
                            reData.setUserAvatarUrl(re_data.getString("user_avatar"));
                            reData.setUserName(re_data.getString("user_name"));
                            reData.setTitle(re_data.getString("title"));
                            reData.setText(re_data.getString("text"));
                            reData.setPrice(re_data.getString("price"));
                            reData.setTimestamp(Long.valueOf(re_data.getString("timestamp")));
                            data_init.add(reData);
                        }
                        Log.i("DataDownloader:getForumData", "get ok");
                    } catch (Exception e) {
                        Log.e("DataDownloader:getForumData", "error");
                        e.printStackTrace();
                    }
                    return data_init;
                });
                data = poster.post();
                break;
            }
        }
        return data;
    }

    public static List<ShData> getShDataList(Context context, @NonNull String mode, int start, int end) {
        List<ShData> data = new ArrayList<>();
        switch (mode) {
            case "newest": {
                int need_num = end - start + 1;
                String text = "{\n" +
                        "  \"module\": \"secondhand\",\n" +
                        "  \"mode\": \"newest\",\n" +
                        "  \"need_num\": " + need_num + ",\n" +
                        "  \"num_start\": " + start + ",\n" +
                        "  \"num_end\": " + end + ",\n" +
                        "  \"timestamp\": 1650098900\n" +
                        "}";
                Poster<List<ShData>> poster = new Poster<>(SettingSP.getShUrl(context), text);
                poster.setHttpListener(result -> {
                    List<ShData> data_init = new ArrayList<>();
                    try {
                        JSONObject root = new JSONObject(result);
                        JSONArray sh_data_array = root.getJSONArray("sh_data");
                        for (int i = 0; i < sh_data_array.length(); i++) {
                            ShData shData = new ShData();
                            JSONObject sh_data = sh_data_array.getJSONObject(i);
                            shData.setUserAvatarUrl(sh_data.getString("user_avatar"));
                            shData.setUserName(sh_data.getString("user_name"));
                            shData.setTitle(sh_data.getString("title"));
                            shData.setText(sh_data.getString("text"));
                            shData.setPrice(sh_data.getString("price"));
                            shData.setTimestamp(Long.valueOf(sh_data.getString("timestamp")));
                            data_init.add(shData);
                        }
                        Log.i("DataDownloader:getForumData", "get ok");
                    } catch (Exception e) {
                        Log.e("DataDownloader:getForumData", "error");
                        e.printStackTrace();
                    }
                    return data_init;
                });
                data = poster.post();
                break;
            }
        }
        return data;
    }

    public static List<CommentData> getCommentDataList(Context context, @NonNull String mode, int start, int end) {
        List<CommentData> data = new ArrayList<>();
        switch (mode) {
            case "newest": {
                int need_num = end - start + 1;
                String text = "{\n" +
                        "  \"module\": \"comment\",\n" +
                        "  \"mode\": \"newest\",\n" +
                        "  \"need_num\": " + need_num + ",\n" +
                        "  \"num_start\": " + start + ",\n" +
                        "  \"num_end\": " + end + ",\n" +
                        "  \"timestamp\": 1650098900\n" +
                        "}";
                Poster<List<CommentData>> poster = new Poster<>(SettingSP.getCommentUrl(context), text);
                poster.setHttpListener(result -> {
                    List<CommentData> data_init = new ArrayList<>();
                    try {
                        JSONObject root = new JSONObject(result);
                        JSONArray comment_data_array = root.getJSONArray("comment_data");
                        for (int i = 0; i < comment_data_array.length(); i++) {
                            CommentData commentData = new CommentData();
                            JSONObject comment_data = comment_data_array.getJSONObject(i);
                            commentData.setUserAvatarUrl(comment_data.getString("user_avatar"));
                            commentData.setUserName(comment_data.getString("user_name"));
                            commentData.setText(comment_data.getString("text"));
                            commentData.setLikes(comment_data.getInt("likes"));
                            commentData.setTimestamp(Long.valueOf(comment_data.getString("timestamp")));
                            data_init.add(commentData);
                        }
                        Log.i("DataDownloader.getCommentData()", "get ok");
                    } catch (Exception e) {
                        Log.e("DataDownloader.getCommentData()", "error");
                        e.printStackTrace();
                    }
                    return data_init;//www,我错了，主线程在等你！宝贝回家！
                });
                data = poster.post();
                break;
            }
        }
        return data;
    }
}
