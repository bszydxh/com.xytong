package com.xytong.data.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.data.CommentData;
import com.xytong.downloader.DataDownloader;

import java.util.ArrayList;
import java.util.List;

public class CommentDataViewModel extends AndroidViewModel {

    private MutableLiveData<List<CommentData>> dataList;

    public CommentDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void setDataList(List<CommentData> dataListIndex) {
        dataList.postValue(dataListIndex);
    }

    public LiveData<List<CommentData>> getDataList() {
        if (dataList == null) {
            Log.e(this.getClass().getName()+".getDataList()", "get data");
            List<CommentData> commentList = new ArrayList<>();
            dataList = new MutableLiveData<>();
            List<CommentData> obtainedDataList = DataDownloader.getCommentDataList("newest", 0, 10);
            if (obtainedDataList != null) {
                commentList.addAll(obtainedDataList);
            }
            dataList.postValue(commentList);
        }
        return dataList;
    }

    public boolean loadMoreData() {
        List<CommentData> commentList = dataList.getValue();
        List<CommentData> obtainedDataList = DataDownloader.getCommentDataList("newest", 0, 10);
        if (commentList != null && obtainedDataList != null) {
            commentList.addAll(obtainedDataList);
            dataList.postValue(commentList);
            return true;
        } else {
            return false;
        }
    }

    public boolean refreshData() {
        List<CommentData> commentList = dataList.getValue();
        List<CommentData> obtainedDataList = DataDownloader.getCommentDataList("newest", 0, 10);
        if (commentList != null && obtainedDataList != null) {
            commentList.clear();
            commentList.addAll(obtainedDataList);
            dataList.postValue(commentList);
            return true;
        } else {
            return false;
        }
    }
}