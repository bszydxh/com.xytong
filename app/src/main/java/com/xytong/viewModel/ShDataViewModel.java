package com.xytong.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.model.entity.ShData;
import com.xytong.dao.SettingDao;
import com.xytong.utils.DataDownloader;
import com.xytong.utils.CoreDataBaseGetter;

import java.util.List;

public class ShDataViewModel extends AndroidViewModel {
    private MutableLiveData<List<ShData>> dataList;

    public ShDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void setDataList(List<ShData> dataListIndex) {
        dataList.postValue(dataListIndex);
    }

    public LiveData<List<ShData>> getDataList() {
        if (dataList == null) {
            dataList = new MutableLiveData<>();
            new Thread(() -> {
                Log.i(this.getClass().getName(), "get data");
                List<ShData> shList;
                if (SettingDao.isDemonstrateMode(getApplication())) {//是否打开演示模式
                    shList = CoreDataBaseGetter.getInstance(getApplication().getApplicationContext())
                            .getCoreDataBase()
                            .getShDataDao()
                            .getAllSh();

                } else {
                    shList = DataDownloader.getShDataList(getApplication().getApplicationContext(), "newest", 0, 10);
                }
                dataList.postValue(shList);
            }).start();
        }
        return dataList;
    }

    public void loadMoreData() {
        new Thread(() -> {
            List<ShData> shList = dataList.getValue();
            List<ShData> obtainedDataList;
            if (SettingDao.isDemonstrateMode(getApplication())) {//是否打开演示模式
                obtainedDataList = CoreDataBaseGetter.getInstance(getApplication().getApplicationContext())
                        .getCoreDataBase()
                        .getShDataDao()
                        .getAllSh();

            } else {
                obtainedDataList = DataDownloader.getShDataList(getApplication().getApplicationContext(), "newest", 0, 10);
            }
            if (shList != null && obtainedDataList != null) {
                shList.addAll(obtainedDataList);
                dataList.postValue(shList);
            }
        }).start();
    }

    public void refreshData() {
        new Thread(() -> {
            List<ShData> shList = dataList.getValue();
            List<ShData> obtainedDataList;
            if (SettingDao.isDemonstrateMode(getApplication())) {//是否打开演示模式
                obtainedDataList = CoreDataBaseGetter.getInstance(getApplication().getApplicationContext())
                        .getCoreDataBase()
                        .getShDataDao()
                        .getAllSh();

            } else {
                obtainedDataList = DataDownloader.getShDataList(getApplication().getApplicationContext(), "newest", 0, 10);
            }
            if (shList != null && obtainedDataList != null) {
                shList.clear();
                shList.addAll(obtainedDataList);
                dataList.postValue(shList);
            }
        }).start();
    }
}
