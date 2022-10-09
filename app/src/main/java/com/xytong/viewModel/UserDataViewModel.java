package com.xytong.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.xytong.model.dto.UserResponseDTO;
import com.xytong.model.vo.UserVO;
import com.xytong.utils.UserDownloader;

public class UserDataViewModel extends AndroidViewModel {
    private MutableLiveData<UserVO> userLiveData;

    public UserDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void setUserData(UserVO userLiveDataIndex) {
        userLiveData.postValue(userLiveDataIndex);
    }

    public LiveData<UserVO> getUserData(String username) {
        if (userLiveData == null) {
            userLiveData = new MutableLiveData<>();
            Log.i(this.getClass().getName() + ".getUserData()", "get data");
            new Thread(() -> {
                UserResponseDTO userResponseDTO = UserDownloader.getUser(getApplication(), username);
                UserVO userVO = UserVO.init(userResponseDTO);
                userLiveData.postValue(userVO);
            }).start();
        }
        return userLiveData;
    }
}
