package com.xytong.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xytong.model.vo.ReVO;
import com.xytong.utils.DataDownloader;

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
            List<ReVO> reList = new ArrayList<>();
            dataList.setValue(reList);
        }
        return dataList;
    }

    public void loadMoreData() {
        new Thread(() -> {
            List<ReVO> reList = getDataList().getValue();
            List<ReVO> obtainedDataList;
            if (reList == null) {
                return;
            }
            int listSize = reList.size();
            obtainedDataList =
                    DataDownloader.getReDataList(
                            getApplication().getApplicationContext(),
                            "newest",
                            listSize,
                            listSize + 10);
            if (obtainedDataList != null) {
                reList.addAll(obtainedDataList);
                dataList.postValue(reList);
            }
        }).start();
    }

    public void refreshData() {
        new Thread(() -> {
            List<ReVO> reList = dataList.getValue();
            List<ReVO> obtainedDataList;
            obtainedDataList =
                    DataDownloader.getReDataList(
                            getApplication().getApplicationContext(),
                            "newest",
                            0,
                            10);
            if (reList != null && obtainedDataList != null) {
                reList.clear();
                reList.addAll(obtainedDataList);
                dataList.postValue(reList);
            }
        }).start();
    }
}