package com.xytong.downloader;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xytong.data.CommentData;
import com.xytong.data.ForumData;
import com.xytong.data.ReData;
import com.xytong.data.ShData;
import com.xytong.data.SharedPreferences.SettingSP;
import com.xytong.data.json.ForumPostJson;
import com.xytong.data.json.ForumRequestJson;
import com.xytong.data.json.RePostJson;
import com.xytong.data.json.ReRequestJson;
import com.xytong.data.json.ShPostJson;
import com.xytong.data.json.ShRequestJson;
import com.xytong.io.Poster;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataDownloader {
    public static List<ForumData> getForumDataList(Context context, @NonNull String mode, int start, int end) {
        List<ForumData> data = new ArrayList<>();
        int need_num = end - start + 1;
        ObjectMapper postMapper = new ObjectMapper();
        ForumPostJson forumPostJson = new ForumPostJson();
        forumPostJson.setModule("forums");
        forumPostJson.setMode(mode);
        forumPostJson.setNumStart(start);
        forumPostJson.setNumEnd(end);
        forumPostJson.setNeedNum(need_num);
        forumPostJson.setTimestamp(System.currentTimeMillis());
        try {
            Log.i("DataDownloader.getReData()", "post ok");
            Poster<List<ForumData>> poster = new Poster<>(SettingSP.getForumUrl(context),
                    postMapper.writeValueAsString(forumPostJson));
            poster.setHttpListener(result -> {
                List<ForumData> data_init = new ArrayList<>();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ForumRequestJson forumRequestJson = objectMapper.readValue(result, ForumRequestJson.class);
                    data_init.addAll(forumRequestJson.getForumData());
                    Log.i("DataDownloader.getForumData()", "get ok");
                } catch (Exception e) {
                    Log.e("DataDownloader.getForumData()", "error");
                    e.printStackTrace();
                }
                return data_init;//异步完成数据传递
            });
            data = poster.post();//由于该方法被包裹在新线程进行，该线程会等待网络进程
        } catch (JsonProcessingException e) {
            Log.e("DataDownloader.getForumData()", "post error");
            e.printStackTrace();

        }
        return data;
    }
    public static List<ReData> getReDataList(Context context, @NonNull String mode, int start, int end) {
        List<ReData> data = new ArrayList<>();
        int need_num = end - start + 1;
        ObjectMapper postMapper = new ObjectMapper();
        RePostJson rePostJson = new RePostJson();
        rePostJson.setModule("run_errands");
        rePostJson.setMode(mode);
        rePostJson.setNumStart(start);
        rePostJson.setNumEnd(end);
        rePostJson.setNeedNum(need_num);
        rePostJson.setTimestamp(System.currentTimeMillis());
        try {
            Log.i("DataDownloader.getReData()", "post ok");
            Poster<List<ReData>> poster = new Poster<>(SettingSP.getReUrl(context),
                    postMapper.writeValueAsString(rePostJson));
            poster.setHttpListener(result -> {
                List<ReData> data_init = new ArrayList<>();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ReRequestJson reRequestJson = objectMapper.readValue(result, ReRequestJson.class);
                    data_init.addAll(reRequestJson.getReData());
                    Log.i("DataDownloader.getReData()", "get ok");
                } catch (Exception e) {
                    Log.e("DataDownloader.getReData()", "error");
                    e.printStackTrace();
                }
                return data_init;//异步完成数据传递
            });
            data = poster.post();//由于该方法被包裹在新线程进行，该线程会等待网络进程
        } catch (JsonProcessingException e) {
            Log.e("DataDownloader.getReData()", "post error");
            e.printStackTrace();

        }
        return data;
    }

    public static List<ShData> getShDataList(Context context, @NonNull String mode, int start, int end) {
        List<ShData> data = new ArrayList<>();
        int need_num = end - start + 1;
        ObjectMapper postMapper = new ObjectMapper();
        ShPostJson rePostJson = new ShPostJson();
        rePostJson.setModule("secondhand");
        rePostJson.setMode(mode);
        rePostJson.setNumStart(start);
        rePostJson.setNumEnd(end);
        rePostJson.setNeedNum(need_num);
        rePostJson.setTimestamp(System.currentTimeMillis());
        try {
            Log.i("DataDownloader.getShData()", "post ok");
            Poster<List<ShData>> poster = new Poster<>(SettingSP.getShUrl(context),
                    postMapper.writeValueAsString(rePostJson));
            poster.setHttpListener(result -> {
                List<ShData> data_init = new ArrayList<>();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ShRequestJson reRequestJson = objectMapper.readValue(result, ShRequestJson.class);
                    data_init.addAll(reRequestJson.getShData());
                    Log.i("DataDownloader.getShData()", "get ok");
                } catch (Exception e) {
                    Log.e("DataDownloader.getShData()", "error");
                    e.printStackTrace();
                }
                return data_init;//异步完成数据传递
            });
            data = poster.post();//由于该方法被包裹在新线程进行，该线程会等待网络进程
        } catch (JsonProcessingException e) {
            Log.e("DataDownloader.getShData()", "post error");
            e.printStackTrace();

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
