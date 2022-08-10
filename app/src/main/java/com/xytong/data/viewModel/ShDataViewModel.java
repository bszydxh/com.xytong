package com.xytong.data.viewModel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.data.ShData;
import com.xytong.downloader.DataDownloader;
import com.xytong.sqlite.MySQL;

import java.util.ArrayList;
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
            Log.i(this.getClass().getName(), "get data");
            MySQL sql = null;
            try {
                sql = new MySQL(getApplication().getApplicationContext());
            } catch (RuntimeException e) {
                Toast.makeText(getApplication().getApplicationContext(), "file error,check the log!", Toast.LENGTH_SHORT).show();
            }
            List<ShData> shList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                if (sql != null) {
                    shList.add(sql.read_secondhand_data());
                }
            }
            if (sql != null) {
                sql.closeDatabase();
            }
            dataList = new MutableLiveData<>();
            List<ShData> obtainedDataList = DataDownloader.getShDataList("newest", 0, 10);
            if (obtainedDataList != null) {
                shList.addAll(obtainedDataList);
            }
            dataList.postValue(shList);
        }
        return dataList;
    }

    public boolean loadMoreData() {
        List<ShData> shList = dataList.getValue();
        List<ShData> obtainedDataList = DataDownloader.getShDataList("newest", 0, 10);
        if (shList != null && obtainedDataList != null) {
            shList.addAll(obtainedDataList);
            dataList.postValue(shList);
            return true;
        } else {
            return false;
        }
    }

    public boolean refreshData() {
        List<ShData> shList = dataList.getValue();
        List<ShData> obtainedDataList = DataDownloader.getShDataList("newest", 0, 10);
        if (shList != null && obtainedDataList != null) {
            shList.clear();
            shList.addAll(obtainedDataList);
            dataList.postValue(shList);
            return true;
        } else {
            return false;
        }
    }
}