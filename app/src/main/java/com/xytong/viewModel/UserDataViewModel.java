package com.xytong.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.model.vo.UserVO;

public class UserDataViewModel extends AndroidViewModel {
    private MutableLiveData<UserVO> dataList;

    public UserDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUserData(UserVO dataListIndex) {
        dataList.postValue(dataListIndex);
    }

    public LiveData<UserVO> getUserData() {
        if (dataList == null) {
            Log.i(this.getClass().getName() + ".getDataList()", "get data");
            UserVO userVO = new UserVO();
            dataList.postValue(userVO);
        }
        return dataList;
    }
}
