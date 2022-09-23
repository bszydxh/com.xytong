package com.xytong.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.model.vo.ForumVO;
import com.xytong.dao.SettingDao;
import com.xytong.utils.DataDownloader;
import com.xytong.utils.CoreDataBaseGetter;

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
            new Thread(() -> {
                Log.i(this.getClass().getName() + ".getDataList()", "get data");
                List<ForumVO> forumList;
                if (SettingDao.isDemonstrateMode(getApplication())) {//是否打开演示模式
                    forumList =
                            CoreDataBaseGetter.getInstance(getApplication().getApplicationContext())
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
            List<ForumVO> forumList = dataList.getValue();
            List<ForumVO> obtainedDataList;
            if (SettingDao.isDemonstrateMode(getApplication())) {
                obtainedDataList =
                        CoreDataBaseGetter.getInstance(getApplication().getApplicationContext())
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
            List<ForumVO> forumList = dataList.getValue();
            List<ForumVO> obtainedDataList;
            if (SettingDao.isDemonstrateMode(getApplication())) {
                obtainedDataList =
                        CoreDataBaseGetter.getInstance(getApplication().getApplicationContext())
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