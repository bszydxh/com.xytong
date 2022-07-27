package com.xytong.data.viewModel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.data.ForumData;
import com.xytong.downloader.DataDownloader;
import com.xytong.sqlite.MySQL;

import java.util.ArrayList;
import java.util.List;

public class ForumDataViewModel extends AndroidViewModel {

    private MutableLiveData<List<ForumData>> dataList;

    public ForumDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void setDataList(List<ForumData> dataListIndex) {
        dataList.setValue(dataListIndex);
    }

    public LiveData<List<ForumData>> getDataList() {
        if (dataList == null) {
            Log.e(this.getClass().getName()+".getDataList()", "get data");
            MySQL sql = null;
            try {
                sql = new MySQL(getApplication().getApplicationContext());
            } catch (RuntimeException e) {
                Toast.makeText(getApplication().getApplicationContext(), "file error,check the log!", Toast.LENGTH_SHORT).show();
            }
            List<ForumData> forumList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                if (sql != null) {
                    forumList.add(sql.read_forum_data());
                }
            }
            dataList = new MutableLiveData<>();
            List<ForumData> obtainedDataList = DataDownloader.getForumDataList("newest", 0, 10);
            if (obtainedDataList != null) {
                forumList.addAll(obtainedDataList);
            }
            dataList.setValue(forumList);
        }
        return dataList;
    }

    public boolean loadMoreData() {
        List<ForumData> forumList = dataList.getValue();
        List<ForumData> obtainedDataList = DataDownloader.getForumDataList("newest", 0, 10);
        if (forumList != null && obtainedDataList != null) {
            forumList.addAll(obtainedDataList);
            dataList.setValue(forumList);
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
            dataList.setValue(forumList);
            return true;
        } else {
            return false;
        }
    }
}