package com.xytong.utils;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.xytong.dao.ListTimeDao;
import com.xytong.dao.SettingDao;
import com.xytong.model.dto.comment.CommentGetRequestDTO;
import com.xytong.model.dto.comment.CommentGetResponseDTO;
import com.xytong.model.dto.forum.ForumGetRequestDTO;
import com.xytong.model.dto.forum.ForumGetResponseDTO;
import com.xytong.model.dto.re.ReGetRequestDTO;
import com.xytong.model.dto.re.ReGetResponseDTO;
import com.xytong.model.dto.sh.ShGetRequestDTO;
import com.xytong.model.dto.sh.ShGetResponseDTO;
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
    public static final String FORUM_MODULE_NAME = "forums";
    public static final String SH_MODULE_NAME = "secondhand";
    public static final String RE_MODULE_NAME = "run_errands";

    public static List<ForumVO> getForumDataList(Context context, @NonNull String mode, int start, int end) {
        int need_num = end - start + 1;
        ForumGetRequestDTO forumGetRequestDTO = new ForumGetRequestDTO();
        forumGetRequestDTO.setModule(FORUM_MODULE_NAME);

        forumGetRequestDTO.setMode(mode);
        forumGetRequestDTO.setNumStart(start);
        forumGetRequestDTO.setNumEnd(end);
        forumGetRequestDTO.setNeedNum(need_num);
        if (start == 0) {
            ListTimeDao.setForumTime(context, System.currentTimeMillis());
        }
        forumGetRequestDTO.setTimestamp(ListTimeDao.getForumTime(context));
        ForumGetResponseDTO forumGetResponseDTO = Poster.jacksonPost(
                SettingDao.getUrl(context, SettingDao.FORUM_URL_NAME, SettingDao.FORUM_URL_RES) + "/get",
                forumGetRequestDTO,
                ForumGetResponseDTO.class);
        if (forumGetResponseDTO == null) {
            Log.e("DataDownloader.getForumDataList()", "获取失败");
            return new ArrayList<>();
        }
        return forumGetResponseDTO.getForumData();
    }

    public static List<ReVO> getReDataList(Context context, @NonNull String mode, int start, int end) {
        int need_num = end - start + 1;
        ReGetRequestDTO reGetRequestDTO = new ReGetRequestDTO();
        reGetRequestDTO.setModule(RE_MODULE_NAME);
        reGetRequestDTO.setMode(mode);
        reGetRequestDTO.setNumStart(start);
        reGetRequestDTO.setNumEnd(end);
        reGetRequestDTO.setNeedNum(need_num);
        reGetRequestDTO.setTimestamp(ListTimeDao.getReTime(context));
        ReGetResponseDTO reGetResponseDTO = Poster.jacksonPost(
                SettingDao.getUrl(context, SettingDao.RE_URL_NAME, SettingDao.RE_URL_RES) + "/get",
                reGetRequestDTO,
                ReGetResponseDTO.class);
        if (reGetResponseDTO == null) {
            Log.e("DataDownloader.getReDataList()", "获取失败");
            return new ArrayList<>();
        }
        return reGetResponseDTO.getReData();

    }

    public static List<ShVO> getShDataList(Context context, @NonNull String mode, int start, int end) {
        int need_num = end - start + 1;
        ShGetRequestDTO shGetRequestDTO = new ShGetRequestDTO();
        shGetRequestDTO.setModule(SH_MODULE_NAME);
        shGetRequestDTO.setMode(mode);
        shGetRequestDTO.setNumStart(start);
        shGetRequestDTO.setNumEnd(end);
        shGetRequestDTO.setNeedNum(need_num);
        shGetRequestDTO.setTimestamp(ListTimeDao.getShTime(context));
        ShGetResponseDTO shGetResponseDTO = Poster.jacksonPost(
                SettingDao.getUrl(context, SettingDao.SH_URL_NAME, SettingDao.SH_URL_RES) + "/get",
                shGetRequestDTO,
                ShGetResponseDTO.class);
        if (shGetResponseDTO == null) {
            Log.e("DataDownloader.getShDataList()", "获取失败");
            return new ArrayList<>();
        }
        return shGetResponseDTO.getShData();
    }
    public static List<CommentVO> getCommentDataList(Context context, Long cid, String module, @NonNull String mode, int start, int end) {
        int need_num = end - start + 1;
        CommentGetRequestDTO commentGetRequestDTO = new CommentGetRequestDTO();
        commentGetRequestDTO.setModule(module);
        commentGetRequestDTO.setCid(cid);
        commentGetRequestDTO.setMode(mode);
        commentGetRequestDTO.setNumStart(start);
        commentGetRequestDTO.setNumEnd(end);
        commentGetRequestDTO.setNeedNum(need_num);
        commentGetRequestDTO.setTimestamp(ListTimeDao.getShTime(context));
        CommentGetResponseDTO shGetResponseDTO = Poster.jacksonPost(
                SettingDao.getUrl(context, SettingDao.COMMENT_URL_NAME, SettingDao.COMMENT_URL_RES) + "/get",
                commentGetRequestDTO,
                CommentGetResponseDTO.class);
        if (shGetResponseDTO == null) {
            Log.e("DataDownloader.getCommentDataList()", "获取失败");
            return new ArrayList<>();
        }
        return shGetResponseDTO.getData();
    }
}
