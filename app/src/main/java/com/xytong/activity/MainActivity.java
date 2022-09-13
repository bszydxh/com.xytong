package com.xytong.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.xytong.R;
import com.xytong.dao.UserDao;
import com.xytong.databinding.ActivityMainBinding;
import com.xytong.fragment.ForumFragment;
import com.xytong.fragment.MoralFragment;
import com.xytong.fragment.ReFragment;
import com.xytong.fragment.ShFragment;
import com.xytong.utils.Access;
import com.xytong.utils.ImageGetter;
import com.xytong.adapter.RootFragmentPagerAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 rootViewPager;
    private RootFragmentPagerAdapter rootFragmentPagerAdapter;
    private DrawerLayout drawer;
    private boolean webViewIsFocused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//声明onCreate,方法继承之前的状态
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());//赋值阶段,inflate为调用生成的绑定类中包含的静态方法。这将为要使用的活动创建一个绑定类的实例。
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        setSupportActionBar(binding.underBar.toolbar);//设置toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        //数据初始化
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        drawer = binding.drawerLayout;//定义DrawerLayout变量drawer,将主视图的drawer赋值到该变量
        NavigationView navigationView = binding.navView;
        binding.underBar.toolbar.setOnClickListener(view -> drawer.openDrawer(GravityCompat.START));
        //对右下方悬浮按键绑定监听事件
        View nav_header_view = binding.navView.getHeaderView(0);
        nav_header_view.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        Menu navMenu = binding.navView.getMenu();
        MenuItem settingItem = navMenu.findItem(R.id.nav_setting);
        MenuItem aboutItem = navMenu.findItem(R.id.nav_about);
        settingItem.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
            return true;
        });
        aboutItem.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        });
        /////////////////////////////////////////////////////////////////////
        rootFragmentPagerAdapter = new RootFragmentPagerAdapter(
                getSupportFragmentManager(), getLifecycle()
        );
        rootFragmentPagerAdapter.addFragment(new ReFragment());
        rootFragmentPagerAdapter.addFragment(new MoralFragment());
        rootFragmentPagerAdapter.addFragment(new ShFragment());
        rootFragmentPagerAdapter.addFragment(new ForumFragment());
        rootViewPager = binding.underBar.pager;
        rootViewPager.setAdapter(rootFragmentPagerAdapter);
        rootViewPager.setUserInputEnabled(false);
        BottomNavigationView bottomNavigationView = binding.underBar.btmNav;
        //导航栏绑定根ViewPager2适配器事件
        bottomNavigationView.setOnItemSelectedListener(item -> {
            String nav_name = item.getTitle().toString();
            switch (nav_name) {
                case "跑腿":
                    rootViewPager.setCurrentItem(0, false);
                    webViewIsFocused = false;
                    break;
                case "德育":
                    rootViewPager.setCurrentItem(1, false);
                    webViewIsFocused = true;
                    break;
                case "二手":
                    rootViewPager.setCurrentItem(2, false);
                    webViewIsFocused = false;
                    break;
                case "论坛":
                    rootViewPager.setCurrentItem(3, false);
                    webViewIsFocused = false;
                    break;
            }
            return true;
        });
        ImageGetter.setAvatarViewBitmap(navigationView.getHeaderView(0).findViewById(R.id.drawer_user_avatar),
                "");
        ImageGetter.setAvatarViewBitmap(
                binding.underBar.toolbarUserAvatar,
                "");
        //此处进行异步用户鉴权，确保用户登录状态（过期没）
        Access.getTokenForStart(this, new Access.TokenListener() {
            @Override
            public void onStart(Context context) {
                Toast.makeText(context, "开始鉴权", Toast.LENGTH_SHORT).show();
                Log.i("access", "开始鉴权");
            }

            @Override
            public void onDone(Context context, String token) {
                Toast.makeText(context, "鉴权完成", Toast.LENGTH_SHORT).show();
                UserDao.setToken(context, token);
                ImageGetter.setAvatarViewBitmap(navigationView.getHeaderView(0).findViewById(R.id.drawer_user_avatar),
                        "https://s1.ax1x.com/2022/04/16/Lt5zjA.png");
                ImageGetter.setAvatarViewBitmap(
                        binding.underBar.toolbarUserAvatar,
                        "https://s1.ax1x.com/2022/04/16/Lt5zjA.png");
                Log.i("access", "鉴权完成");
            }

            @Override
            public void onError(Context context) {
                Toast.makeText(context, "鉴权失败", Toast.LENGTH_SHORT).show();
                Log.i("access", "鉴权失败");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (webViewIsFocused) {
            MoralFragment moralFragment = (MoralFragment) rootFragmentPagerAdapter.getItem(1);
            if (!moralFragment.webViewBack()) {//返回不了就退出
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }
}