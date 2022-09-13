package com.xytong.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.model.entity.UserData;

public class UserDataViewModel extends AndroidViewModel {
    private MutableLiveData<UserData> dataList;

    public UserDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUserData(UserData dataListIndex) {
        dataList.postValue(dataListIndex);
    }

    public LiveData<UserData> getUserData() {
        if (dataList == null) {
            Log.i(this.getClass().getName() + ".getDataList()", "get data");
            UserData userData = new UserData();
            dataList.postValue(userData);
        }
        return dataList;
    }
}
