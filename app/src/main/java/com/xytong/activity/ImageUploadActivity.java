package com.xytong.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.xytong.R;
import com.xytong.dao.UserDao;
import com.xytong.databinding.ActivityImageUploadBinding;
import com.xytong.model.vo.UserVO;
import com.xytong.utils.ImageUtils;
import com.xytong.utils.ViewCreateUtils;

import java.io.File;
import java.util.Objects;

public class ImageUploadActivity extends AppCompatActivity {
    private ActivityImageUploadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewCreateUtils.setBlackStatusBar(this);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        super.onCreate(savedInstanceState);
        binding = ActivityImageUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        binding.imageUploadBack.setOnClickListener(v -> finish());
        ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() != Activity.RESULT_OK) {
                        return;
                    }
                    Intent data = result.getData();
                    if (data != null) {
                        Uri currentUri = data.getData();
                        ImageUtils.setImageBitmapNoCache(binding.imageUploadImage, currentUri.toString());
                        File file = new File(currentUri.getPath());

                    }
                });
        binding.imageUploadImage.setOnClickListener(v -> {
            Intent intent = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
                intent.setType("image/*");
            } else {
            }
            mStartForResult.launch(intent);
        });

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
    }
}
