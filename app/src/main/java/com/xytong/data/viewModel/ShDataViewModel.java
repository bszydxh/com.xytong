package com.xytong.data.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.xytong.data.ShData;

import java.util.List;

public class ShDataViewModel extends ViewModel {
    ShDataGetter ShDataGetter;

    public interface ShDataGetter {
        List<ShData> onGet();
    }

    public void setShDataGetter(ShDataGetter ShDataGetter) {
        this.ShDataGetter = ShDataGetter;
    }

    private MutableLiveData<List<ShData>> dataList;

    public LiveData<List<ShData>> getDataList() {
        if (dataList == null) {
            dataList = new MutableLiveData<List<ShData>>();
            loaDataList();
        }
        return dataList;
    }

    private void loaDataList() {
        MutableLiveData<List<ShData>> newDataList = new MutableLiveData<>();
        newDataList.setValue(ShDataGetter.onGet());
    }
}