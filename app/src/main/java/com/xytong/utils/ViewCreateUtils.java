package com.xytong.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import com.xytong.service.ImageController;
import com.xytong.model.vo.UserVO;

public class ViewCreateUtils {
    public static void setBlackStatusBar(Context context) {
        Window window = ((androidx.core.app.ComponentActivity) context).getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public static void setupUserDataViewGroup(ImageView imageView, TextView name, TextView signature, UserVO userVO) {
        ImageController.setAvatarViewBitmap(imageView,
                userVO.getUserAvatarUrl());
        name.setText(userVO.getName());
        signature.setText(userVO.getSignature());
    }

    public static void setImage(@NonNull ImageView imageView, @DrawableRes int ResId, @ColorRes int colorId) {
        Context context = imageView.getContext();
        Drawable bmpDrawable = ContextCompat.getDrawable(context, ResId);
        if (bmpDrawable != null) {
            Drawable.ConstantState state = bmpDrawable.getConstantState();
            Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
            DrawableCompat.setTint(wrap, ContextCompat.getColor(context, colorId));
            imageView.setImageDrawable(wrap);
        }
    }
    public static void setImage(@NonNull ImageView imageView, @DrawableRes int ResId) {
        Context context = imageView.getContext();
        Drawable bmpDrawable = ContextCompat.getDrawable(context, ResId);
        if (bmpDrawable != null) {
            Drawable.ConstantState state = bmpDrawable.getConstantState();
            Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
            imageView.setImageDrawable(wrap);
        }
    }
    public static void setBackground(@NonNull ImageView imageView, @DrawableRes int ResId, @ColorRes int colorId) {
        Context context = imageView.getContext();
        Drawable bmpDrawable = ContextCompat.getDrawable(context, ResId);
        if (bmpDrawable != null) {
            Drawable.ConstantState state = bmpDrawable.getConstantState();
            Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
            DrawableCompat.setTint(wrap, ContextCompat.getColor(context, colorId));
            imageView.setBackground(wrap);
        }
    }
}
