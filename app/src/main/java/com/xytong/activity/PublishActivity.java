package com.xytong.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.xytong.R;
import com.xytong.databinding.ActivityPublishBinding;
import com.xytong.utils.ViewCreatedHelper;

public class PublishActivity extends AppCompatActivity {
    ActivityPublishBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewCreatedHelper.setBlackStatusBar(this);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        binding = ActivityPublishBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        binding.publishBack.setOnClickListener(v -> finish());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
    }
}
