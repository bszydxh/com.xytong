package com.xytong.data.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.data.ReData;
import com.xytong.data.SharedPreferences.SettingSP;
import com.xytong.downloader.DataDownloader;
import com.xytong.sql.MySQL;

import java.util.ArrayList;
import java.util.List;

public class ReDataViewModel extends AndroidViewModel {
    private MutableLiveData<List<ReData>> dataList;

    public ReDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void setDataList(List<ReData> dataListIndex) {
        dataList.postValue(dataListIndex);
    }

    public LiveData<List<ReData>> getDataList() {
        if (dataList == null) {
            Log.i(this.getClass().getName(), "get data");
            List<ReData> reList = new ArrayList<>();
            reList.add(new ReData());
            if (SettingSP.isDemonstrateMode(getApplication())) {//是否打开演示模式
                reList.addAll(MySQL.getInstance(getApplication().getApplicationContext())
                        .getCoreDataBase()
                        .getReDataDao()
                        .getAllRe());
            } else {
                List<ReData> obtainedDataList = DataDownloader.getReDataList(getApplication().getApplicationContext(),"newest", 0, 10);
                if (obtainedDataList != null) {
                    reList.addAll(obtainedDataList);
                }
            }

            dataList = new MutableLiveData<>();
            dataList.postValue(reList);
        }
        return dataList;
    }

    public boolean loadMoreData() {
        List<ReData> reList = dataList.getValue();
        List<ReData> obtainedDataList;
        if (SettingSP.isDemonstrateMode(getApplication())) {//是否打开演示模式
            obtainedDataList = MySQL.getInstance(getApplication().getApplicationContext())
                    .getCoreDataBase()
                    .getReDataDao()
                    .getAllRe();
        } else {
            obtainedDataList = DataDownloader.getReDataList(getApplication().getApplicationContext(),"newest", 0, 10);
        }
        if (reList != null && obtainedDataList != null) {
            reList.addAll(obtainedDataList);
            dataList.postValue(reList);
            return true;
        } else {
            return false;
        }
    }

    public boolean refreshData() {
        List<ReData> reList = dataList.getValue();
        List<ReData> obtainedDataList;
        if (SettingSP.isDemonstrateMode(getApplication())) {//是否打开演示模式
            obtainedDataList = MySQL.getInstance(getApplication().getApplicationContext())
                    .getCoreDataBase()
                    .getReDataDao()
                    .getAllRe();
        } else {
            obtainedDataList = DataDownloader.getReDataList(getApplication().getApplicationContext(),"newest", 0, 10);
        }
        if (reList != null && obtainedDataList != null) {
            reList.clear();
            reList.add(new ReData());
            reList.addAll(obtainedDataList);
            dataList.postValue(reList);
            return true;
        } else {
            return false;
        }
    }
}