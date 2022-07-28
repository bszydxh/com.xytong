package com.xytong.data.viewModel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.data.ReData;
import com.xytong.downloader.DataDownloader;
import com.xytong.sqlite.MySQL;

import java.util.ArrayList;
import java.util.List;

public class ReDataViewModel extends AndroidViewModel {
    private MutableLiveData<List<ReData>> dataList;

    public ReDataViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<ReData>> getDataList() {
        if (dataList == null) {
            Log.e(this.getClass().getName(), "get data");
            MySQL sql = null;
            try {
                sql = new MySQL(getApplication().getApplicationContext());
            } catch (RuntimeException e) {
                Toast.makeText(getApplication().getApplicationContext(), "file error,check the log!", Toast.LENGTH_SHORT).show();
            }
            List<ReData> reList = new ArrayList<>();
            reList.add(new ReData());
            for (int i = 0; i < 5; i++) {
                if (sql != null) {
                    reList.add(sql.read_run_errands_data());
                }
            }
            if (sql != null) {
                sql.closeDatabase();
            }
            dataList = new MutableLiveData<>();
            List<ReData> obtainedDataList = DataDownloader.getReDataList("newest", 0, 10);
            if (obtainedDataList != null) {
                reList.addAll(obtainedDataList);
            }
            dataList.postValue(reList);
        }
        return dataList;
    }

    public boolean loadMoreData() {
        List<ReData> reList = dataList.getValue();
        List<ReData> obtainedDataList = DataDownloader.getReDataList("newest", 0, 10);
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
        List<ReData> obtainedDataList = DataDownloader.getReDataList("newest", 0, 10);
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