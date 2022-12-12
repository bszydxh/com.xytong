package com.xytong.viewModel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xytong.model.vo.CommentVO;
import com.xytong.service.DataDownloader;

import java.util.ArrayList;
import java.util.List;

public class CommentDataViewModel extends AndroidViewModel {
    //TODO 给我枝棱起来！！！！！
    private MutableLiveData<List<CommentVO>> dataList;
    String module;
    Long cid;

    public CommentDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void setDataList(List<CommentVO> dataListIndex) {
        dataList.postValue(dataListIndex);
    }

    public LiveData<List<CommentVO>> getDataList() {
        if (dataList == null) {
            dataList = new MutableLiveData<>();
            List<CommentVO> commentVOList = new ArrayList<>();
            dataList.setValue(commentVOList);
        }
        return dataList;
    }

    public void init(String module, Long cid) {
        this.module = module;
        this.cid = cid;
    }

    public void loadMoreData() {
        new Thread(() -> {
            if (module == null || cid == null) {
                Log.e("loadCommentData", "get error:need init!");
                return;
            }
            List<CommentVO> commentList = dataList.getValue();
            if (commentList == null) {
                return;
            }
            int listSize = commentList.size();
            List<CommentVO> obtainedDataList =
                    DataDownloader.getCommentDataList(
                            getApplication().getApplicationContext(),
                            cid,
                            module,
                            "newest",
                            listSize,
                            listSize + 10);
            if (obtainedDataList != null) {
                commentList.addAll(obtainedDataList);
                dataList.postValue(commentList);
            }
        }).start();
    }

    public void refreshData() {
        new Thread(() -> {
            if (module == null || cid == null) {
                Log.e("loadCommentData", "get error:need init!");
                return;
            }
            List<CommentVO> commentList = dataList.getValue();
            List<CommentVO> obtainedDataList =
                    DataDownloader.getCommentDataList(
                            getApplication().getApplicationContext(),
                            cid,
                            module,
                            "newest",
                            0,
                            10);
            if (commentList != null && obtainedDataList != null) {
                commentList.clear();
                commentList.addAll(obtainedDataList);
                dataList.postValue(commentList);
            }
        }).start();
    }

}