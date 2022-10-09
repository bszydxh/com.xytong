package com.xytong.utils;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.xytong.dao.SettingDao;
import com.xytong.model.dto.*;
import com.xytong.model.vo.CommentVO;
import com.xytong.model.vo.ForumVO;
import com.xytong.model.vo.ReVO;
import com.xytong.model.vo.ShVO;
import com.xytong.utils.poster.Poster;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataDownloader {
    public static List<ForumVO> getForumDataList(Context context, @NonNull String mode, int start, int end) {
        int need_num = end - start + 1;
        ForumPostDTO forumPostDTO = new ForumPostDTO();
        forumPostDTO.setModule("forums");
        forumPostDTO.setMode(mode);
        forumPostDTO.setNumStart(start);
        forumPostDTO.setNumEnd(end);
        forumPostDTO.setNeedNum(need_num);
        forumPostDTO.setTimestamp(System.currentTimeMillis());
        ForumResponseDTO forumResponseDTO = Poster.jacksonPost(SettingDao.getForumUrl(context), forumPostDTO, ForumResponseDTO.class);
        if (forumResponseDTO == null) {
            Log.e("DataDownloader.getForumDataList()","获取失败");
            return new ArrayList<>();
        }
        return forumResponseDTO.getForumData();
    }

    public static List<ReVO> getReDataList(Context context, @NonNull String mode, int start, int end) {
        int need_num = end - start + 1;
        RePostDTO rePostDTO = new RePostDTO();
        rePostDTO.setModule("run_errands");
        rePostDTO.setMode(mode);
        rePostDTO.setNumStart(start);
        rePostDTO.setNumEnd(end);
        rePostDTO.setNeedNum(need_num);
        rePostDTO.setTimestamp(System.currentTimeMillis());
        ReResponseDTO reResponseDTO = Poster.jacksonPost(SettingDao.getReUrl(context), rePostDTO, ReResponseDTO.class);
        if (reResponseDTO == null) {
            Log.e("DataDownloader.getReDataList()","获取失败");
            return new ArrayList<>();
        }
        return reResponseDTO.getReData();

    }

    public static List<ShVO> getShDataList(Context context, @NonNull String mode, int start, int end) {
        int need_num = end - start + 1;
        ShPostDTO shPostDTO = new ShPostDTO();
        shPostDTO.setModule("secondhand");
        shPostDTO.setMode(mode);
        shPostDTO.setNumStart(start);
        shPostDTO.setNumEnd(end);
        shPostDTO.setNeedNum(need_num);
        shPostDTO.setTimestamp(System.currentTimeMillis());
        ShResponseDTO shResponseDTO = Poster.jacksonPost(SettingDao.getShUrl(context), shPostDTO, ShResponseDTO.class);
        if (shResponseDTO == null) {
            Log.e("DataDownloader.getShDataList()","获取失败");
            return new ArrayList<>();
        }
        return shResponseDTO.getShData();
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
