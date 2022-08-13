package com.xytong.data.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.data.ForumData;
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
            Log.i(this.getClass().getName() + ".getDataList()", "get data");
            List<ForumData> forumList =
                    MySQL.getInstance(getApplication().getApplicationContext())
                            .getCoreDataBase()
                            .getForumDataDao()
                            .getAllForum();
            dataList = new MutableLiveData<>();
            List<ForumData> obtainedDataList = DataDownloader.getForumDataList("newest", 0, 10);
            if (obtainedDataList != null) {
                forumList.addAll(obtainedDataList);
            }
            dataList.postValue(forumList);
        }
        return dataList;
    }

    public boolean loadMoreData() {
        List<ForumData> forumList = dataList.getValue();
        List<ForumData> obtainedDataList = DataDownloader.getForumDataList("newest", 0, 10);
        if (forumList != null && obtainedDataList != null) {
            forumList.addAll(obtainedDataList);
            dataList.postValue(forumList);
            return true;
        } else {
            return false;
        }
    }

    public boolean refreshData() {
        List<ForumData> forumList = dataList.getValue();
        List<ForumData> obtainedDataList = DataDownloader.getForumDataList("newest", 0, 10);
        if (forumList != null && obtainedDataList != null) {
            forumList.clear();
            forumList.addAll(obtainedDataList);
            dataList.postValue(forumList);
            return true;
        } else {
            return false;
        }
    }
}