package com.xytong.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DataDownloader {
    public static List<ForumData> getForumData(String mode, int start, int end) {
        List<ForumData> data = new ArrayList<>();
        new Thread(() -> {
            //get的方式提交就是url拼接的方式
            String path = "http://rap2api.taobao.org/app/mock/data/2255088?scope=response";
            try {
                URL url = new URL(path);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                //获得结果码
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    //请求成功 获得返回的流
                    InputStream inputStream = connection.getInputStream();
                    int length = connection.getContentLength();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, length);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    for (int result = bufferedInputStream.read(); result != -1; result = bufferedInputStream.read()) {
                        byteArrayOutputStream.write((byte) result);
                    }
                    String result = byteArrayOutputStream.toString();
                    //Log.e("DataDownloader",result);
                    try {
                        JSONObject root = new JSONObject(result);
                        JSONArray forum_data_array = root.getJSONArray("forum_data");
                        for (int i = 0; i < forum_data_array.length(); i++)
                        {
                            ForumData forumData = new ForumData();
                            JSONObject forum_data = forum_data_array.getJSONObject(i);
                            forumData.setUserAvatarUrl(forum_data.getString("user_avatar"));
                            forumData.setUserName(forum_data.getString("user_name"));
                            forumData.setTitle(forum_data.getString("title"));
                            forumData.setText(forum_data.getString("text"));
                            forumData.setLikes(forum_data.getInt("likes"));
                            forumData.setComments(forum_data.getInt("comments"));
                            forumData.setForwarding(forum_data.getInt("forwarding"));
                            data.add(forumData);
                        }
                    } catch (JSONException e) {
                        Log.e("DataDownloader:getForumData","json error");
                        e.printStackTrace();
                    }
                } else {
                    //请求失败
                }
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        ).start();
        return data;
    }

}
