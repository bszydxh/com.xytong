package com.xytong.ui;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.xytong.R;

public class Thump<T extends LikeThump> {

    public void setupThump(T data, ImageView imageView, TextView textView) {
        {
            int likes = data.getLikes();
            String likesString = Integer.toString(data.isLiked() ? likes + 1 : likes);
            textView.setText(likesString);
            Drawable bmpDrawable = ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_baseline_thumb_up_24);
            Drawable.ConstantState state = bmpDrawable.getConstantState();
            Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
            DrawableCompat.setTint(wrap, ContextCompat.getColor(imageView.getContext(),
                    data.isLiked() ? R.color.sky_blue : R.color.dark_gray));
            imageView.setImageDrawable(wrap);
        }
    }
    public void changeThump(T data, ImageView imageView, TextView textView)
    {
        int likes = data.getLikes();
        String likesString = Integer.toString(data.isLiked() ? likes : likes + 1);
        data.setLiked(!data.isLiked());
        textView.setText(likesString);
        Drawable bmpDrawable = ContextCompat.getDrawable(imageView.getContext(), R.drawable.ic_baseline_thumb_up_24);
        Drawable.ConstantState state = bmpDrawable.getConstantState();
        Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
        DrawableCompat.setTint(wrap, ContextCompat.getColor(imageView.getContext(),
                data.isLiked() ? R.color.sky_blue : R.color.dark_gray));
        imageView.setImageDrawable(wrap);
    }
}
