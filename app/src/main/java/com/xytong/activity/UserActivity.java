package com.xytong.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xytong.R;
import com.xytong.adapter.RootFragmentPagerAdapter;
import com.xytong.service.ImageController;
import com.xytong.databinding.ActivityUserBinding;
import com.xytong.fragment.ForumFragment;
import com.xytong.fragment.ReFragment;
import com.xytong.fragment.ShFragment;
import com.xytong.model.vo.UserVO;
import com.xytong.utils.ViewCreateUtils;
import com.xytong.viewModel.UserDataViewModel;

public class UserActivity extends AppCompatActivity {
    ActivityUserBinding binding;

    UserDataViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewCreateUtils.setBlackStatusBar(this);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        Bundle bundle_back = getIntent().getExtras();
        UserVO userVO = (UserVO) bundle_back.getSerializable("userData");
        if (userVO == null) {
            userVO = new UserVO();
            userVO.setName("未知用户");
        }
        model = new ViewModelProvider(this).get(UserDataViewModel.class);
        LiveData<UserVO> liveData = model.getUserData(userVO.getName());
        liveData.observe(this, userLiveData -> {
            if(userLiveData==null)
            {
                return;
            }
            binding.userSignature.setText(userLiveData.getSignature());
        });

        binding.userName.setText(userVO.getName());
        ImageController.setAvatarViewBitmap(binding.userAvatar, userVO.getUserAvatarUrl());
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
            switch (position) {
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
