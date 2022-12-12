package com.xytong.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.xytong.R;
import com.xytong.databinding.ActivitySettingBinding;
import com.xytong.utils.ViewCreateUtils;


public class SettingActivity extends AppCompatActivity {
    private ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewCreateUtils.setBlackStatusBar(this);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        binding.settingBack.setOnClickListener(v -> finish());
        //功能屏蔽
//        binding.demonstrateModeSwitch.setChecked(SettingDao.isDemonstrateMode(this));
//        binding.demonstrateModeSwitch.setOnCheckedChangeListener((v, checked)
//                -> SettingDao.setDemonstrateMode(this, checked));
//        binding.changeUrl.setOnClickListener(v->{
//            UrlCreateDialog dialog= new UrlCreateDialog();
//            dialog.show(getSupportFragmentManager(),"urlChangeDialog");
//        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
    }
}