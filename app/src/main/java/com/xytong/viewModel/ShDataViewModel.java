package com.xytong.viewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xytong.model.vo.ShVO;
import com.xytong.service.DataDownloader;

import java.util.ArrayList;
import java.util.List;

public class ShDataViewModel extends AndroidViewModel {
    private MutableLiveData<List<ShVO>> dataList;

    public ShDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void setDataList(List<ShVO> dataListIndex) {
        dataList.postValue(dataListIndex);
    }

    public LiveData<List<ShVO>> getDataList() {
        if (dataList == null) {
            dataList = new MutableLiveData<>();
            List<ShVO> shList = new ArrayList<>();
            dataList.setValue(shList);
        }
        return dataList;
    }

    public void loadMoreData() {
        new Thread(() -> {
            List<ShVO> shList = getDataList().getValue();
            List<ShVO> obtainedDataList;
            if (shList == null) {
                return;
            }
            int listSize = shList.size();
            obtainedDataList = DataDownloader.getShDataList(getApplication().getApplicationContext(), "newest", listSize, listSize + 10);
            if (obtainedDataList != null) {
                shList.addAll(obtainedDataList);
                dataList.postValue(shList);
            }
        }).start();
    }

    public void refreshData() {
        new Thread(() -> {
            List<ShVO> shList = dataList.getValue();
            List<ShVO> obtainedDataList;
            obtainedDataList = DataDownloader.getShDataList(getApplication().getApplicationContext(), "newest", 0, 10);
            if (shList != null && obtainedDataList != null) {
                shList.clear();
                shList.addAll(obtainedDataList);
                dataList.postValue(shList);
            }
        }).start();
    }
}
