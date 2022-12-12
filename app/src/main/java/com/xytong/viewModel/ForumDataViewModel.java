package com.xytong.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xytong.model.vo.ForumVO;
import com.xytong.service.DataDownloader;

import java.util.ArrayList;
import java.util.List;

public class ForumDataViewModel extends AndroidViewModel {

    private MutableLiveData<List<ForumVO>> dataList;

    public ForumDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void setDataList(List<ForumVO> dataListIndex) {
        dataList.postValue(dataListIndex);
    }

    public LiveData<List<ForumVO>> getDataList() {
        if (dataList == null) {
            dataList = new MutableLiveData<>();
            List<ForumVO> forumList = new ArrayList<>();
            dataList.setValue(forumList);
        }
        return dataList;
    }

    public void loadMoreData() {
        new Thread(() -> {
            List<ForumVO> forumList = getDataList().getValue();
            List<ForumVO> obtainedDataList;
            if (forumList == null) {
                return;
            }
            int listSize = forumList.size();
            obtainedDataList = DataDownloader.getForumDataList(getApplication().getApplicationContext(), "newest", listSize, listSize + 10);
            if (obtainedDataList != null) {
                forumList.addAll(obtainedDataList);
                dataList.postValue(forumList);
            }
        }).start();
    }

    public void refreshData() {
        new Thread(() -> {
            List<ForumVO> forumList = dataList.getValue();
            List<ForumVO> obtainedDataList;
            obtainedDataList = DataDownloader.getForumDataList(getApplication().getApplicationContext(), "newest", 0, 10);
            if (forumList != null && obtainedDataList != null) {
                forumList.clear();
                forumList.addAll(obtainedDataList);
                dataList.postValue(forumList);
            }
        }).start();
    }
}