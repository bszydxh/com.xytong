package com.xytong.utils;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.xytong.model.vo.UserVO;

public class ViewCreatedHelper {
    public static void setBlackStatusBar(Context context) {
        Window window = ((androidx.core.app.ComponentActivity) context).getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public static void setupUserDataViewGroup(ImageView imageView, TextView name, TextView signature, UserVO userVO) {
        ImageGetter.setAvatarViewBitmap(imageView,
                userVO.getUserAvatarUrl());
        name.setText(userVO.getName());
        signature.setText(userVO.getSignature());
    }
}
