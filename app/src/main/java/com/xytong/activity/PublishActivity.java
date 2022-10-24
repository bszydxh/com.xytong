package com.xytong.activity;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.xytong.R;
import com.xytong.dao.UserDao;
import com.xytong.databinding.ActivityPublishBinding;
import com.xytong.model.dto.ForumAddPostDTO;
import com.xytong.model.dto.ForumAddResponseDTO;
import com.xytong.utils.ViewCreateUtils;
import com.xytong.utils.poster.Poster;

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
        binding.publishSend.setOnClickListener(v -> {
            ForumAddPostDTO forumAddPostDTO = new ForumAddPostDTO();
            forumAddPostDTO.setText(binding.cardPublishCommentText.getText().toString());
            forumAddPostDTO.setTitle(binding.cardPublishCommentTitle.getText().toString());
            forumAddPostDTO.setToken(UserDao.getToken(this));
            forumAddPostDTO.setUsername(UserDao.getUser(this).getName());
            forumAddPostDTO.setModule("forums");
            forumAddPostDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));
            ForumAddResponseDTO forumAddResponseDTO = Poster.jacksonPost("http://192.168.3.41/forums/v1/add", forumAddPostDTO, ForumAddResponseDTO.class);
            if (forumAddResponseDTO == null) {
                Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
                return;
            }
            if (Objects.equals(forumAddResponseDTO.getMode(), "add ok")) {
                Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();
            }
        });
        binding.publishBack.setOnClickListener(v -> finish());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
    }
}
