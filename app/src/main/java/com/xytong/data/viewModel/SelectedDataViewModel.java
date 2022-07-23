package com.xytong.data.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.xytong.data.CardData;

public class SelectedDataViewModel extends ViewModel {
    private MutableLiveData<CardData> DataList;

    public MutableLiveData<CardData> getDataList() {
        if (DataList == null) {
            loadDataList();
        }
        return DataList;
    }

    public LiveData<CardData> getUsers() {
        if (DataList == null) {
            DataList = new MutableLiveData<>();
            loadDataList();
        }
        return DataList;
    }

    private void loadDataList() {

    }
}
