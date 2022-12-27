package com.xytong.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.xytong.R;
import com.xytong.databinding.ActivityPublishBinding;
import com.xytong.utils.DataSender;
import com.xytong.utils.ViewCreateUtils;

import java.math.BigDecimal;
import java.util.Objects;

public class PublishActivity extends AppCompatActivity {
    ActivityPublishBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewCreateUtils.setBlackStatusBar(this);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        binding = ActivityPublishBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        Bundle bundle_back = getIntent().getExtras();
        String moduleName = bundle_back.getString("name");
        if (Objects.equals(moduleName, "forum")) {
            binding.publishText.setText("论坛发帖子");
            binding.publishSend.setOnClickListener(v -> {
                DataSender.sendForum(
                        this,
                        binding.cardPublishCommentTitle.getText().toString(),
                        binding.cardPublishCommentText.getText().toString()
                );
                setResult(Activity.RESULT_OK);
                finish();
            });
        } else if (Objects.equals(moduleName, "sh")) {
            binding.publishText.setText("二手发布物品");
            binding.publishSend.setOnClickListener(v -> {
                DataSender.sendSh(
                        this,
                        binding.cardPublishCommentTitle.getText().toString(),
                        binding.cardPublishCommentText.getText().toString(),
                        new BigDecimal("0.00")
                );
                setResult(Activity.RESULT_OK);
                finish();
            });
        } else if (Objects.equals(moduleName, "re")) {
            binding.publishText.setText("跑腿发布任务");
            binding.publishSend.setOnClickListener(v -> {
                DataSender.sendRe(
                        this,
                        binding.cardPublishCommentTitle.getText().toString(),
                        binding.cardPublishCommentText.getText().toString(),
                        new BigDecimal("0.00")
                );
                setResult(Activity.RESULT_OK);
                finish();
            });
        }

        binding.publishBack.setOnClickListener(v -> finish());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
    }
}
