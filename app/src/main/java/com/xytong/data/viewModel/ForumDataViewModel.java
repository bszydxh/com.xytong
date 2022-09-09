package com.xytong.data.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.data.ForumData;
import com.xytong.data.sharedPreferences.SettingSP;
import com.xytong.downloader.DataDownloader;
import com.xytong.sql.MySQL;

import java.util.List;

public class ForumDataViewModel extends AndroidViewModel {

    private MutableLiveData<List<ForumData>> dataList;

    public ForumDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void setDataList(List<ForumData> dataListIndex) {
        dataList.postValue(dataListIndex);
    }

    public LiveData<List<ForumData>> getDataList() {
        if (dataList == null) {
            dataList = new MutableLiveData<>();
            new Thread(() -> {
                Log.i(this.getClass().getName() + ".getDataList()", "get data");
                List<ForumData> forumList;
                if (SettingSP.isDemonstrateMode(getApplication())) {//是否打开演示模式
                    forumList =
                            MySQL.getInstance(getApplication().getApplicationContext())
                                    .getCoreDataBase()
                                    .getForumDataDao()
                                    .getAllForum();
                } else {
                    forumList = DataDownloader.getForumDataList(getApplication().getApplicationContext(), "newest", 0, 10);
                }
                dataList.postValue(forumList);
            }).start();
        }
        return dataList;
    }

    public void loadMoreData() {
        new Thread(() -> {
            List<ForumData> forumList = dataList.getValue();
            List<ForumData> obtainedDataList;
            if (SettingSP.isDemonstrateMode(getApplication())) {
                obtainedDataList =
                        MySQL.getInstance(getApplication().getApplicationContext())
                                .getCoreDataBase()
                                .getForumDataDao()
                                .getAllForum();
            } else {
                obtainedDataList = DataDownloader.getForumDataList(getApplication().getApplicationContext(), "newest", 0, 10);
            }
            if (forumList != null && obtainedDataList != null) {
                forumList.addAll(obtainedDataList);
                dataList.postValue(forumList);
            }
        }).start();
    }

    public void refreshData() {
        new Thread(() -> {
            List<ForumData> forumList = dataList.getValue();
            List<ForumData> obtainedDataList;
            if (SettingSP.isDemonstrateMode(getApplication())) {
                obtainedDataList =
                        MySQL.getInstance(getApplication().getApplicationContext())
                                .getCoreDataBase()
                                .getForumDataDao()
                                .getAllForum();
            } else {
                obtainedDataList = DataDownloader.getForumDataList(getApplication().getApplicationContext(), "newest", 0, 10);
            }
            if (forumList != null && obtainedDataList != null) {
                forumList.clear();
                forumList.addAll(obtainedDataList);
                dataList.postValue(forumList);
            }
        }).start();
    }
}