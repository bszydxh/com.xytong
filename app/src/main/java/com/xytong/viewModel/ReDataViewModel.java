package com.xytong.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.model.vo.ReVO;
import com.xytong.dao.SettingDao;
import com.xytong.utils.DataDownloader;
import com.xytong.utils.CoreDataBaseGetter;

import java.util.ArrayList;
import java.util.List;

public class ReDataViewModel extends AndroidViewModel {
    private MutableLiveData<List<ReVO>> dataList;

    public ReDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void setDataList(List<ReVO> dataListIndex) {
        dataList.postValue(dataListIndex);
    }

    public LiveData<List<ReVO>> getDataList() {
        if (dataList == null) {
            dataList = new MutableLiveData<>();
            new Thread(() -> {
                Log.i(this.getClass().getName(), "get data");
                List<ReVO> reList = new ArrayList<>();
                reList.add(new ReVO());
                if (SettingDao.isDemonstrateMode(getApplication())) {//是否打开演示模式
                    reList.addAll(CoreDataBaseGetter.getInstance(getApplication().getApplicationContext())
                            .getCoreDataBase()
                            .getReDataDao()
                            .getAllRe());
                } else {
                    List<ReVO> obtainedDataList = DataDownloader.getReDataList(getApplication().getApplicationContext(), "newest", 0, 10);
                    if (obtainedDataList != null) {
                        reList.addAll(obtainedDataList);
                    }
                }
                dataList.postValue(reList);
            }).start();
        }
        return dataList;
    }

    public void loadMoreData() {
        new Thread(() -> {
            List<ReVO> reList = getDataList().getValue();
            List<ReVO> obtainedDataList;
            if (SettingDao.isDemonstrateMode(getApplication())) {//是否打开演示模式
                obtainedDataList = CoreDataBaseGetter.getInstance(getApplication().getApplicationContext())
                        .getCoreDataBase()
                        .getReDataDao()
                        .getAllRe();
            } else {
                obtainedDataList = DataDownloader.getReDataList(getApplication().getApplicationContext(), "newest", 0, 10);
            }
            if (reList != null && obtainedDataList != null) {
                reList.addAll(obtainedDataList);
                dataList.postValue(reList);
            }
        }).start();
    }

    public void refreshData() {
        new Thread(() -> {
            List<ReVO> reList = getDataList().getValue();
            List<ReVO> obtainedDataList;
            if (SettingDao.isDemonstrateMode(getApplication())) {//是否打开演示模式
                obtainedDataList = CoreDataBaseGetter.getInstance(getApplication().getApplicationContext())
                        .getCoreDataBase()
                        .getReDataDao()
                        .getAllRe();
            } else {
                obtainedDataList = DataDownloader.getReDataList(getApplication().getApplicationContext(), "newest", 0, 10);
            }
            if (reList != null && obtainedDataList != null) {
                reList.clear();
                reList.add(new ReVO());
                reList.addAll(obtainedDataList);
                dataList.postValue(reList);
            }
        }).start();
    }
}