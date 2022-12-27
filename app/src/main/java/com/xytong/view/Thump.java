package com.xytong.view;

import android.widget.ImageView;
import android.widget.TextView;
import com.xytong.R;
import com.xytong.utils.ViewCreateUtils;

public class Thump<T extends LikeThump> {

    public void setupThump(T data, ImageView imageView, TextView textView) {
        {
            int likes = data.getLikes();
            String likesString = Integer.toString(data.isLiked() ? likes + 1 : likes);
            ThumpSetter(data, imageView, textView, likesString);
        }
    }

    public void changeThump(T data, ImageView imageView, TextView textView) {
        int likes = data.getLikes();
        String likesString = Integer.toString(data.isLiked() ? likes : likes + 1);
        data.setLiked(!data.isLiked());
        ThumpSetter(data, imageView, textView, likesString);

    }

    private void ThumpSetter(T data, ImageView imageView, TextView textView, String likesString) {
        textView.setText(likesString);
        ViewCreateUtils.setImage(
                imageView,
                R.drawable.ic_baseline_thumb_up_24,
                data.isLiked() ? R.color.sky_blue : R.color.dark_gray
        );
    }
}
