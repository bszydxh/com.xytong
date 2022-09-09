package com.xytong;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.xytong.adapter.RootFragmentPagerAdapter;
import com.xytong.data.UserData;
import com.xytong.databinding.ActivityUserBinding;
import com.xytong.fragment.ForumFragment;
import com.xytong.fragment.ReFragment;
import com.xytong.fragment.ShFragment;
import com.xytong.image.ImageGetter;

public class UserActivity extends AppCompatActivity {
    ActivityUserBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        Bundle bundle_back = getIntent().getExtras();
        UserData userData = (UserData) bundle_back.getSerializable("userData");
        if (userData == null) {
            userData = new UserData();
            userData.setName("未知用户");
        }
        binding.userName.setText(userData.getName());
        ImageGetter.setAvatarViewBitmap(binding.userAvatar, userData.getUserAvatarUrl());
        binding.userBack.setOnClickListener(v -> finish());
        RootFragmentPagerAdapter rootFragmentPagerAdapter;
        rootFragmentPagerAdapter = new RootFragmentPagerAdapter(
                getSupportFragmentManager(), getLifecycle()
        );
        rootFragmentPagerAdapter.addFragment(new ReFragment());
        rootFragmentPagerAdapter.addFragment(new ShFragment());
        rootFragmentPagerAdapter.addFragment(new ForumFragment());
        ViewPager2 rootViewPager = binding.pager;
        rootViewPager.setAdapter(rootFragmentPagerAdapter);
        TabLayoutMediator tab = new TabLayoutMediator(binding.tab, rootViewPager, (tab1, position) -> {
            switch (position){
                case 0:
                    tab1.setText("跑腿");
                    break;
                case 1:
                    tab1.setText("二手");
                    break;
                case 2:
                    tab1.setText("论坛");
                    break;
            }
        });
        tab.attach();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
    }
}
