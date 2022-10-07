package com.xytong.utils;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xytong.dao.SettingDao;
import com.xytong.model.vo.*;
import com.xytong.model.vo.ForumVO;
import com.xytong.model.vo.ReVO;
import com.xytong.model.dto.*;
import com.xytong.utils.poster.Poster;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataDownloader {
    public static List<ForumVO> getForumDataList(Context context, @NonNull String mode, int start, int end) {
        List<ForumVO> data = new ArrayList<>();
        int need_num = end - start + 1;
        ObjectMapper postMapper = new ObjectMapper();
        ForumPostDTO forumPostDTO = new ForumPostDTO();
        forumPostDTO.setModule("forums");
        forumPostDTO.setMode(mode);
        forumPostDTO.setNumStart(start);
        forumPostDTO.setNumEnd(end);
        forumPostDTO.setNeedNum(need_num);
        forumPostDTO.setTimestamp(System.currentTimeMillis());
        try {
            Log.i("DataDownloader.getReData()", "post ok");
            Poster<List<ForumVO>> poster = new Poster<>(SettingDao.getForumUrl(context),
                    postMapper.writeValueAsString(forumPostDTO));
            poster.setHttpListener(result -> {
                List<ForumVO> data_init = new ArrayList<>();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ForumRequestDTO forumRequestDTO = objectMapper.readValue(result, ForumRequestDTO.class);
                    data_init.addAll(forumRequestDTO.getForumData());
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

    public static List<ReVO> getReDataList(Context context, @NonNull String mode, int start, int end) {
        List<ReVO> data = new ArrayList<>();
        int need_num = end - start + 1;
        ObjectMapper postMapper = new ObjectMapper();
        RePostDTO rePostDTO = new RePostDTO();
        rePostDTO.setModule("run_errands");
        rePostDTO.setMode(mode);
        rePostDTO.setNumStart(start);
        rePostDTO.setNumEnd(end);
        rePostDTO.setNeedNum(need_num);
        rePostDTO.setTimestamp(System.currentTimeMillis());
        try {
            Log.i("DataDownloader.getReData()", "post ok");
            Poster<List<ReVO>> poster = new Poster<>(SettingDao.getReUrl(context),
                    postMapper.writeValueAsString(rePostDTO));
            poster.setHttpListener(result -> {
                List<ReVO> data_init = new ArrayList<>();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ReRequestDTO reRequestDTO = objectMapper.readValue(result, ReRequestDTO.class);
                    data_init.addAll(reRequestDTO.getReData());
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

    public static List<ShVO> getShDataList(Context context, @NonNull String mode, int start, int end) {
        List<ShVO> data = new ArrayList<>();
        int need_num = end - start + 1;
        ObjectMapper postMapper = new ObjectMapper();
        ShPostDTO rePostJson = new ShPostDTO();
        rePostJson.setModule("secondhand");
        rePostJson.setMode(mode);
        rePostJson.setNumStart(start);
        rePostJson.setNumEnd(end);
        rePostJson.setNeedNum(need_num);
        rePostJson.setTimestamp(System.currentTimeMillis());
        try {
            Log.i("DataDownloader.getShData()", "post ok");
            Poster<List<ShVO>> poster = new Poster<>(SettingDao.getShUrl(context),
                    postMapper.writeValueAsString(rePostJson));
            poster.setHttpListener(result -> {
                List<ShVO> data_init = new ArrayList<>();
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ShRequestDTO reRequestJson = objectMapper.readValue(result, ShRequestDTO.class);
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

    public static List<CommentVO> getCommentDataList(Context context, @NonNull String mode, int start, int end) {
        List<CommentVO> data = new ArrayList<>();
        if ("newest".equals(mode)) {
            int need_num = end - start + 1;
            String text = "{\n" +
                    "  \"module\": \"comment\",\n" +
                    "  \"mode\": \"newest\",\n" +
                    "  \"need_num\": " + need_num + ",\n" +
                    "  \"num_start\": " + start + ",\n" +
                    "  \"num_end\": " + end + ",\n" +
                    "  \"timestamp\": 1650098900\n" +
                    "}";
            Poster<List<CommentVO>> poster = new Poster<>(SettingDao.getCommentUrl(context), text);
            poster.setHttpListener(result -> {
                List<CommentVO> data_init = new ArrayList<>();
                try {
                    JSONObject root = new JSONObject(result);
                    JSONArray comment_data_array = root.getJSONArray("comment_data");
                    for (int i = 0; i < comment_data_array.length(); i++) {
                        CommentVO commentData = new CommentVO();
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
        }
        return data;
    }
}
