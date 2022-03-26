package com.xytong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.xytong.databinding.ActivityMainBinding;
import com.xytong.databinding.AppBarMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;//为activity_main.xml绑定视图,先定义一个类,之后赋值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//声明onCreate,方法继承之前的状态

        binding = ActivityMainBinding.inflate(getLayoutInflater());//赋值阶段,inflate为调用生成的绑定类中包含的静态方法。这将为要使用的活动创建一个绑定类的实例。
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用
        setSupportActionBar(binding.appBarMain.underBar.toolbar);//设置toolbar
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {//对右下方悬浮按键绑定监听事件
            @Override
            public void onClick(View view) {//@override注释后对编译器会对重写的方法进行检查
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)//snackbar类似于toast,它们在移动设备的屏幕底部和较大设备的左下方显示一条简短消息
                        .setAction("Action", null).show();//设置点击事件
            }
        });
        binding.appBarMain.underBar.toolbar.setOnClickListener(new View.OnClickListener() {
            int tap = 0;

            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this, "tap" + tap, Toast.LENGTH_SHORT);
                tap++;
                toast.show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;//定义DrawerLayout变量drawer,将主视图的drawer赋值到该变量
        NavigationView navigationView = binding.navView;//跟上面同理//nav就是drawer的一个子视图
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(//应用侧栏配置
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)//三人行,home...
                .setOpenableLayout(drawer)//显示三条横用的qwq
                .build();// 构造AppBarConfiguration实例
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        //构建新的NavController对象,实际上是找到已有的赋给该对象
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);//nav导航控制器,显示标题用
//        NavigationUI.setupWithNavController(navigationView, navController);//绑定三个按键的导航

        ViewPager viewPager = binding.appBarMain.underBar.pager;
        ArrayList<View> aListView = new ArrayList<View>();
        LayoutInflater layoutInflater = getLayoutInflater();
        aListView.add(layoutInflater.inflate(R.layout.run_errands, null, false));
        aListView.add(layoutInflater.inflate(R.layout.moral, null, false));
        aListView.add(layoutInflater.inflate(R.layout.secondhand, null, false));
        aListView.add(layoutInflater.inflate(R.layout.forums, null, false));
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(aListView);
        viewPager.setAdapter(myPagerAdapter);
        BottomNavigationView bottomnavigation = binding.appBarMain.underBar.btmNav;
        bottomnavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        String nav_name = item.getTitle().toString();
                        int a = 0;
                        switch (nav_name) {
                            case "跑腿":
                                viewPager.setCurrentItem(0);
                                break;
                            case "德育":
                                viewPager.setCurrentItem(1);
                                break;
                            case "二手":
                                viewPager.setCurrentItem(2);
                                break;
                            case "论坛":
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return true;
                    }
                }
        );
        viewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffset2) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        switch (position) {
                            case 0:
                                bottomnavigation.setSelectedItemId(R.id.run_errands);
                                break;
                            case 1:
                                bottomnavigation.setSelectedItemId(R.id.moral);
                                break;
                            case 2:
                                bottomnavigation.setSelectedItemId(R.id.secondhand);
                                break;
                            case 3:
                                bottomnavigation.setSelectedItemId(R.id.forums);
                                break;
                        }
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//创建一个菜单
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


// insert page ids
//adapter2.insertViewId(R.id.run_errands_page);
//adapter2.insertViewId(R.id.secondhand_page);
//adapter2.insertViewId(R.id.moral_page);
//adapter2.insertViewId(R.id.forums_page);
// attach adapter to viewpager
//    @Override
//    public boolean onSupportNavigateUp() {//重写三条横的实现,即响应事件
//       NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}