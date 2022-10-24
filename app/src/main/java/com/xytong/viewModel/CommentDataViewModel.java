package com.xytong.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.model.vo.CommentVO;
import com.xytong.utils.DataDownloader;

import java.util.ArrayList;
import java.util.List;

public class CommentDataViewModel extends AndroidViewModel {
//TODO!!!!!
    private MutableLiveData<List<CommentVO>> dataList;

    public CommentDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void setDataList(List<CommentVO> dataListIndex) {
        dataList.postValue(dataListIndex);
    }

    public LiveData<List<CommentVO>> getDataList() {
        if (dataList == null) {
            Log.i(this.getClass().getName() + ".getDataList()", "get data");
            List<CommentVO> commentList = new ArrayList<>();
            dataList = new MutableLiveData<>();
            List<CommentVO> obtainedDataList = DataDownloader.getCommentDataList(getApplication().getApplicationContext(),"newest", 0, 10);
            if (obtainedDataList != null) {
                commentList.addAll(obtainedDataList);
            }
            dataList.postValue(commentList);
        }
        return dataList;
    }

    public boolean loadMoreData() {
        List<CommentVO> commentList = dataList.getValue();
        List<CommentVO> obtainedDataList = DataDownloader.getCommentDataList(getApplication().getApplicationContext(),"newest", 0, 10);
        if (commentList != null && obtainedDataList != null) {
            commentList.addAll(obtainedDataList);
            dataList.postValue(commentList);
            return true;
        } else {
            return false;
        }
    }

    public boolean refreshData() {
        List<CommentVO> commentList = dataList.getValue();
        List<CommentVO> obtainedDataList = DataDownloader.getCommentDataList(getApplication().getApplicationContext(),"newest", 0, 10);
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