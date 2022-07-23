package com.xytong.data.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.xytong.data.ReData;

import java.util.List;

public class ReDataViewModel extends ViewModel {
    ReDataGetter ReDataGetter;

    public interface ReDataGetter {
        List<ReData> onGet();
    }

    public void setReDataGetter(ReDataGetter ReDataGetter) {
        this.ReDataGetter = ReDataGetter;
    }

    private MutableLiveData<List<ReData>> dataList;

    public LiveData<List<ReData>> getDataList() {
        if (dataList == null) {
            dataList = new MutableLiveData<List<ReData>>();
            loaDataList();
        }
        return dataList;
    }

    private void loaDataList() {
        MutableLiveData<List<ReData>> newDataList = new MutableLiveData<>();
        newDataList.setValue(ReDataGetter.onGet());
    }
}