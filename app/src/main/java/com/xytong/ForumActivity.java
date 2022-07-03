package com.xytong;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.xytong.data.ForumData;
import com.xytong.databinding.ActivityForumBinding;
import com.xytong.image.ImageGetter;

public class ForumActivity extends AppCompatActivity {
    private ActivityForumBinding binding;
    Integer Liked = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);//进入渐变动画
        super.onCreate(savedInstanceState);
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        Bundle bundle_back = getIntent().getExtras();
        ForumData forumData = (ForumData) bundle_back.getSerializable("forumData");
        ImageGetter.setAvatarViewBitmap(binding.cardForumIndex.cardForumUserAvatar, forumData.getUserAvatarUrl());
        binding.cardForumIndex.cardForumUserName.setText(forumData.getUserName());
        binding.cardForumIndex.cardForumTitle.setText(forumData.getTitle());
        binding.cardForumIndex.cardForumText.setText(forumData.getText());
        String likesNum = forumData.getLikes().toString();
        String commentsNum = forumData.getComments().toString();
        String forwardingNum = forumData.getForwarding().toString();
        binding.cardForumIndex.cardForumLikes.setText(likesNum);
        binding.cardForumIndex.cardForumComments.setText(commentsNum);
        binding.cardForumIndex.cardForumForwarding.setText(forwardingNum);
        binding.cardForumIndex.cardForumLikesLayout.setOnClickListener(v -> {
                if (Liked == 0) {
                    int likes = forumData.getLikes() + 1;
                    String likesString = Integer.toString(likes);
                    binding.cardForumIndex.cardForumLikes.setText(likesString);
                    Liked = 1;
                    Drawable bmpDrawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_thumb_up_24);
                    Drawable.ConstantState state = bmpDrawable.getConstantState();
                    Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
                    DrawableCompat.setTint(wrap, ContextCompat.getColor(this, R.color.sky_blue));
                    binding.cardForumIndex.cardForumLikesImage.setImageDrawable(wrap);
                } else {
                    int likes = forumData.getLikes();
                    String likesString = Integer.toString(likes);
                    binding.cardForumIndex.cardForumLikes.setText(likesString);
                    Liked = 0;
                    Drawable bmpDrawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_thumb_up_24);
                    Drawable.ConstantState state = bmpDrawable.getConstantState();
                    Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
                    DrawableCompat.setTint(wrap, ContextCompat.getColor(this, R.color.dark_gray));
                    binding.cardForumIndex.cardForumLikesImage.setImageDrawable(wrap);
                }


        });
        binding.cardForumIndex.cardForumForwardingLayout.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            // 比如发送文本形式的数据内容
            // 指定发送的内容
            sendIntent.putExtra(Intent.EXTRA_TEXT, forumData.getTitle() + "\n"
                    + forumData.getText() + "\n用户:" +
                    forumData.getUserName() +
                    "\n---来自校园通客户端");
            // 指定发送内容的类型
            sendIntent.setType("text/plain");
            ContextCompat.startActivity(this, Intent.createChooser(sendIntent, "将内容分享至"), null);
        });
        setContentView(binding.getRoot());//binding中cardForumRoot()方法是对binding根视图的引用,也相当于创建视图
        binding.forumBack.setOnClickListener(v -> finish());

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);//退出渐变动画
    }
}
