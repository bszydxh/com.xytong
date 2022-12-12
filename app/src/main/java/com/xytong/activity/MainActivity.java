package com.xytong.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.xytong.R;
import com.xytong.adapter.RootFragmentPagerAdapter;
import com.xytong.dao.SettingDao;
import com.xytong.dao.UserDao;
import com.xytong.databinding.ActivityMainBinding;
import com.xytong.fragment.ForumFragment;
import com.xytong.fragment.MoralFragment;
import com.xytong.fragment.ReFragment;
import com.xytong.fragment.ShFragment;
import com.xytong.model.vo.UserVO;
import com.xytong.utils.AccessUtils;
import com.xytong.utils.ViewCreateUtils;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 rootViewPager;
    private RootFragmentPagerAdapter rootFragmentPagerAdapter;
    private DrawerLayout drawer;
    private boolean webViewIsFocused = false;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//声明onCreate,方法继承之前的状态
        binding = ActivityMainBinding.inflate(getLayoutInflater());//赋值阶段,inflate为调用生成的绑定类中包含的静态方法。这将为要使用的活动创建一个绑定类的实例。
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        setSupportActionBar(binding.underBar.toolbar);//设置toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        //数据初始化
        ViewCreateUtils.setBlackStatusBar(this);
        drawer = binding.drawerLayout;//定义DrawerLayout变量drawer,将主视图的drawer赋值到该变量
        NavigationView navigationView = binding.navView;
        binding.underBar.toolbar.setOnClickListener(view -> drawer.openDrawer(GravityCompat.START));
        //对右下方悬浮按键绑定监听事件
        View nav_header_view = binding.navView.getHeaderView(0);
        SettingDao.setUrl(this, SettingDao.COMMENT_URL_NAME, getText(SettingDao.COMMENT_URL_RES).toString());
        SettingDao.setUrl(this, SettingDao.RE_URL_NAME,getText(SettingDao.RE_URL_RES).toString() );
        SettingDao.setUrl(this, SettingDao.FORUM_URL_NAME, getText(SettingDao.FORUM_URL_RES).toString());
        SettingDao.setUrl(this, SettingDao.SH_URL_NAME, getText(SettingDao.SH_URL_RES).toString());
        SettingDao.setUrl(this, SettingDao.ACCESS_URL_NAME,getText(SettingDao.ACCESS_URL_RES).toString() );
        SettingDao.setUrl(this, SettingDao.USER_URL_NAME,getText(SettingDao.USER_URL_RES).toString() );
        SettingDao.setUrl(this, SettingDao.CAPTCHA_URL_NAME, getText(SettingDao.CAPTCHA_URL_RES).toString());
        ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.i("MainActivity", "back");
                        if (!Objects.equals(UserDao.getUser(this), new UserVO())) {
                            navigationView.getHeaderView(0).findViewById(R.id.nav_layout).setBackgroundColor(getResources().getColor(R.color.white_blue1));
                        } else {
                            navigationView.getHeaderView(0).findViewById(R.id.nav_layout).setBackgroundColor(getResources().getColor(R.color.dark_gray));
                        }
                        ViewCreateUtils.setupUserDataViewGroup(
                                navigationView.getHeaderView(0).findViewById(R.id.drawer_user_avatar),
                                navigationView.getHeaderView(0).findViewById(R.id.drawer_user_name),
                                navigationView.getHeaderView(0).findViewById(R.id.drawer_user_signature),
                                UserDao.getUser(this)
                        );
                        ViewCreateUtils.setupUserDataViewGroup(
                                binding.underBar.toolbarUserAvatar,
                                binding.underBar.toolbarUserName,
                                binding.underBar.toolbarUserSignature,
                                UserDao.getUser(this)
                        );
                    }
                });
        nav_header_view.setOnClickListener(view -> {
            if (!Objects.equals(UserDao.getUser(this), new UserVO())) {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("是否退出登录？")//设置标题
                        .setNegativeButton("再想想", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                        })
                        .setPositiveButton("退出", (dialog1, which) -> {
                            UserDao.setUser(this, new UserVO());
                            UserDao.setToken(this, "");
                            UserDao.setPwd(this, "");
                            if (!Objects.equals(UserDao.getUser(this), new UserVO())) {
                                navigationView.getHeaderView(0).findViewById(R.id.nav_layout).setBackgroundColor(getResources().getColor(R.color.white_blue1, getTheme()));
                            } else {
                                navigationView.getHeaderView(0).findViewById(R.id.nav_layout).setBackgroundColor(getResources().getColor(R.color.dark_gray, getTheme()));
                            }
                            ViewCreateUtils.setupUserDataViewGroup(
                                    navigationView.getHeaderView(0).findViewById(R.id.drawer_user_avatar),
                                    navigationView.getHeaderView(0).findViewById(R.id.drawer_user_name),
                                    navigationView.getHeaderView(0).findViewById(R.id.drawer_user_signature),
                                    UserDao.getUser(this)
                            );
                            ViewCreateUtils.setupUserDataViewGroup(
                                    binding.underBar.toolbarUserAvatar,
                                    binding.underBar.toolbarUserName,
                                    binding.underBar.toolbarUserSignature,
                                    UserDao.getUser(this)
                            );
                        }).create();
                dialog.show();//显示对话框
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                mStartForResult.launch(intent);
            }

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
        rootViewPager.setOffscreenPageLimit(5);
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
        if (!Objects.equals(UserDao.getUser(this), new UserVO())) {
            navigationView.getHeaderView(0).findViewById(R.id.nav_layout).setBackgroundColor(getResources().getColor(R.color.white_blue1));
        } else {
            navigationView.getHeaderView(0).findViewById(R.id.nav_layout).setBackgroundColor(getResources().getColor(R.color.dark_gray));
        }
        ViewCreateUtils.setupUserDataViewGroup(
                navigationView.getHeaderView(0).findViewById(R.id.drawer_user_avatar),
                navigationView.getHeaderView(0).findViewById(R.id.drawer_user_name),
                navigationView.getHeaderView(0).findViewById(R.id.drawer_user_signature),
                UserDao.getUser(this)
        );
        ViewCreateUtils.setupUserDataViewGroup(
                binding.underBar.toolbarUserAvatar,
                binding.underBar.toolbarUserName,
                binding.underBar.toolbarUserSignature,
                UserDao.getUser(this)
        );
        //此处进行异步用户鉴权，确保用户登录状态（过期没）
        AccessUtils.getTokenForStart(this, new AccessUtils.UserDataListener() {
            @Override
            public void onStart(Context context) {
                Log.i("access", "开始鉴权");
            }

            @Override
            public void onDone(Context context, UserVO userVO) {
                ViewCreateUtils.setupUserDataViewGroup(
                        navigationView.getHeaderView(0).findViewById(R.id.drawer_user_avatar),
                        navigationView.getHeaderView(0).findViewById(R.id.drawer_user_name),
                        navigationView.getHeaderView(0).findViewById(R.id.drawer_user_signature),
                        UserDao.getUser(context)
                );
                ViewCreateUtils.setupUserDataViewGroup(
                        binding.underBar.toolbarUserAvatar,
                        binding.underBar.toolbarUserName,
                        binding.underBar.toolbarUserSignature,
                        UserDao.getUser(context)
                );
                Log.i("access", "鉴权完成");
            }

            @Override
            public void onError(Context context, int errorFlag) {
                Log.i("access", "鉴权失败");
            }
        });
        Log.e("UserDataCheck", UserDao.getUser(this).toString());
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