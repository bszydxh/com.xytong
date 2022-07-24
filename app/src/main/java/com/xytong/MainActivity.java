package com.xytong;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.xytong.adapter.RootFragmentPagerAdapter;
import com.xytong.databinding.ActivityMainBinding;
import com.xytong.fragment.ForumFragment;
import com.xytong.fragment.MoralFragment;
import com.xytong.fragment.RunErrandsFragment;
import com.xytong.fragment.SecondhandFragment;
import com.xytong.image.ImageGetter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 rootViewPager;
    private RootFragmentPagerAdapter rootFragmentPagerAdapter;
    private DrawerLayout drawer;
    private boolean webViewIsFocused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//声明onCreate,方法继承之前的状态
        //为activity_main.xml绑定视图,先定义一个类,之后赋值
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());//赋值阶段,inflate为调用生成的绑定类中包含的静态方法。这将为要使用的活动创建一个绑定类的实例。
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        setSupportActionBar(binding.appBarMain.underBar.toolbar);//设置toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        drawer = binding.drawerLayout;//定义DrawerLayout变量drawer,将主视图的drawer赋值到该变量
        NavigationView navigationView = binding.navView;
        ImageGetter.setAvatarViewBitmap(navigationView.getHeaderView(0).findViewById(R.id.drawer_user_avatar),
                "https://s1.ax1x.com/2022/04/16/Lt5zjA.png");
        ImageGetter.setAvatarViewBitmap(
                binding.appBarMain.underBar.toolbarUserAvatar,
                "https://s1.ax1x.com/2022/04/16/Lt5zjA.png");
        binding.appBarMain.underBar.toolbarUserAvatar.setOnClickListener(view -> drawer.openDrawer(GravityCompat.START));
        //对右下方悬浮按键绑定监听事件
        binding.appBarMain.fab.setOnClickListener(view -> {//@override注释后对编译器会对重写的方法进行检查
            Snackbar.make(view, "todo", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();//设置点击事件
        });
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
        rootFragmentPagerAdapter.addFragment(new RunErrandsFragment());
        rootFragmentPagerAdapter.addFragment(new MoralFragment());
        rootFragmentPagerAdapter.addFragment(new SecondhandFragment());
        rootFragmentPagerAdapter.addFragment(new ForumFragment());
        rootViewPager = binding.appBarMain.underBar.pager;
        rootViewPager.setAdapter(rootFragmentPagerAdapter);
        rootViewPager.setUserInputEnabled(false);
        BottomNavigationView bottomNavigationView = binding.appBarMain.underBar.btmNav;
        //导航栏绑定根ViewPager2适配器事件
        bottomNavigationView.setOnItemSelectedListener(item -> {
            String nav_name = item.getTitle().toString();
            switch (nav_name) {
                case "跑腿":
                    rootViewPager.setCurrentItem(0, false);
                    binding.appBarMain.fab.setVisibility(View.VISIBLE);
                    webViewIsFocused = false;
                    break;
                case "德育":
                    rootViewPager.setCurrentItem(1, false);
                    binding.appBarMain.fab.setVisibility(View.INVISIBLE);
                    webViewIsFocused = true;
                    break;
                case "二手":
                    rootViewPager.setCurrentItem(2, false);
                    binding.appBarMain.fab.setVisibility(View.VISIBLE);
                    webViewIsFocused = false;
                    break;
                case "论坛":
                    rootViewPager.setCurrentItem(3, false);
                    binding.appBarMain.fab.setVisibility(View.VISIBLE);
                    webViewIsFocused = false;
                    break;
            }
            return true;
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
            moralFragment.webViewBack();//强转类型
        } else {
            super.onBackPressed();
        }
    }
}