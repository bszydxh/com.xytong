package com.xytong;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.xytong.data.ForumData;
import com.xytong.databinding.ActivityForumBinding;
import com.xytong.image.ImageGetter;

public class ForumActivity extends AppCompatActivity {
    private ActivityForumBinding binding;
    ForumData forumData;
    int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        super.onCreate(savedInstanceState);
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        Bundle bundle_back = getIntent().getExtras();
        position = bundle_back.getInt("pos");
        forumData = (ForumData) bundle_back.getSerializable("forumData");
        ImageGetter.setAvatarViewBitmap(binding.cardForumIndex.cardForumUserAvatar, forumData.getUserAvatarUrl());
        binding.cardForumIndex.cardForumUserName.setText(forumData.getUserName());
        binding.cardForumIndex.cardForumTitle.setText(forumData.getTitle());
        binding.cardForumIndex.cardForumText.setText(forumData.getText());
        if (forumData.isLiked()) {
            int likes = forumData.getLikes() + 1;
            String likesString = Integer.toString(likes);
            binding.cardForumIndex.cardForumLikes.setText(likesString);
            try {
                Drawable bmpDrawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_thumb_up_24);
                Drawable.ConstantState state = bmpDrawable.getConstantState();
                Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
                DrawableCompat.setTint(wrap, ContextCompat.getColor(this, R.color.sky_blue));
                binding.cardForumIndex.cardForumLikesImage.setImageDrawable(wrap);
            } catch (NullPointerException e) {
                Log.e(this.getClass().getName(), "change image error");
            }

        } else {
            int likes = forumData.getLikes();
            String likesString = Integer.toString(likes);
            binding.cardForumIndex.cardForumLikes.setText(likesString);
            Drawable bmpDrawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_thumb_up_24);
            Drawable.ConstantState state = bmpDrawable.getConstantState();
            Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
            DrawableCompat.setTint(wrap, ContextCompat.getColor(this, R.color.dark_gray));
            binding.cardForumIndex.cardForumLikesImage.setImageDrawable(wrap);
        }
        String commentsNum = forumData.getComments().toString();
        String forwardingNum = forumData.getForwarding().toString();
        binding.cardForumIndex.cardForumComments.setText(commentsNum);
        binding.cardForumIndex.cardForumForwarding.setText(forwardingNum);
        binding.cardForumIndex.cardForumLikesLayout.setOnClickListener(v -> {
            if (forumData.isLiked()) {
                int likes = forumData.getLikes();
                String likesString = Integer.toString(likes);
                binding.cardForumIndex.cardForumLikes.setText(likesString);
                forumData.setLiked(false);

            } else {
                int likes = forumData.getLikes() + 1;
                String likesString = Integer.toString(likes);
                binding.cardForumIndex.cardForumLikes.setText(likesString);
                forumData.setLiked(true);
            }
            Drawable bmpDrawable = ContextCompat.getDrawable(this, R.drawable.ic_baseline_thumb_up_24);
            Drawable.ConstantState state = bmpDrawable.getConstantState();
            Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
            DrawableCompat.setTint(wrap, ContextCompat.getColor(this,
                    forumData.isLiked() ? R.color.sky_blue : R.color.dark_gray));
            binding.cardForumIndex.cardForumLikesImage.setImageDrawable(wrap);
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
        Bundle bundle = new Bundle();
        bundle.putSerializable("forumData", forumData);
        bundle.putInt("pos", position);
        Intent intent = new Intent();
        intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
        setResult(Activity.RESULT_OK, intent);
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//退出渐变动画
    }

}
