package com.xytong;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.xytong.databinding.ActivityForumBinding;

public class ForumActivity extends AppCompatActivity {
    private ActivityForumBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);//进入渐变动画
        super.onCreate(savedInstanceState);
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        binding.forumBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);//退出渐变动画
    }
}
