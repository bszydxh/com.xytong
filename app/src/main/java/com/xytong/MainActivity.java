package com.xytong;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xytong.adapter.ForumRecyclerAdapter;
import com.xytong.adapter.RootPagerAdapter;
import com.xytong.adapter.ShRecyclerAdapter;
import com.xytong.data.ForumData;
import com.xytong.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;//为activity_main.xml绑定视图,先定义一个类,之后赋值
    private NavigationView navigationView;
    private ViewPager viewPager;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//声明onCreate,方法继承之前的状态
        binding = ActivityMainBinding.inflate(getLayoutInflater());//赋值阶段,inflate为调用生成的绑定类中包含的静态方法。这将为要使用的活动创建一个绑定类的实例。
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        setSupportActionBar(binding.appBarMain.underBar.toolbar);//设置toolbar
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {//对右下方悬浮按键绑定监听事件
            @Override
            public void onClick(View view) {//@override注释后对编译器会对重写的方法进行检查
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)//snackbar类似于toast,它们在移动设备的屏幕底部和较大设备的左下方显示一条简短消息
                        .setAction("Action", null).show();//设置点击事件
            }
        });
        View nav_header_view = binding.navView.getHeaderView(0);
        nav_header_view.setOnClickListener(new View.OnClickListener() {//对右下方悬浮按键绑定监听事件
            @Override
            public void onClick(View view) {//@override注释后对编译器会对重写的方法进行检查
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;//定义DrawerLayout变量drawer,将主视图的drawer赋值到该变量
        navigationView = binding.navView;//跟上面同理//nav就是drawer的一个子视图
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

        viewPager = binding.appBarMain.underBar.pager;
        ArrayList<View> aListView = new ArrayList<View>();
        LayoutInflater layoutInflater = getLayoutInflater();
        aListView.add(layoutInflater.inflate(R.layout.run_errands, null, false));
        aListView.add(layoutInflater.inflate(R.layout.moral, null, false));
        aListView.add(layoutInflater.inflate(R.layout.secondhand, null, false));
        aListView.add(layoutInflater.inflate(R.layout.forum, null, false));
        RootPagerAdapter viewPagerAdapter = new RootPagerAdapter(aListView);
        viewPager.setAdapter(viewPagerAdapter);
        /////////////////////////////////////////////////////////////////////
        //第一页
        /////////////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////////////////////////////////////
        //第二页
        /////////////////////////////////////////////////////////////////////
        webView = aListView.get(1).findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//设置支持js
        //设置自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        ///////
        webSettings.setSupportZoom(true);//支持缩放
        webSettings.setLoadsImagesAutomatically(true);//设置自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setAllowFileAccess(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("https://www.people.com.cn/");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                view.loadUrl(url);
                return true;
            }
        });
        /////////////////////////////////////////////////////////////////////
        //第三页
        /////////////////////////////////////////////////////////////////////
        RefreshLayout shRefreshLayout = aListView.get(2).findViewById(R.id.shRefreshLayout);
        shRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        shRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        List<String> shList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            shList.add(i + "");
        }
        ShRecyclerAdapter shRecyclerAdapter = new ShRecyclerAdapter(shList);
        shRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
            }
        });
        shRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                Toast.makeText(MainActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        RecyclerView shRecyclerView = aListView.get(2).findViewById(R.id.shRecyclerView);

        shRecyclerView.setAdapter(shRecyclerAdapter);
        LinearLayoutManager shlinearLayoutManager = new LinearLayoutManager(this);
        shRecyclerView.setLayoutManager(shlinearLayoutManager);
        shlinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        /////////////////////////////////////////////////////////////////////
        //第四页
        /////////////////////////////////////////////////////////////////////
        RefreshLayout forumRefreshLayout = aListView.get(3).findViewById(R.id.forumRefreshLayout);
        forumRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        forumRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        List<ForumData> forumList = new ArrayList<>();
        ForumData forumData = new ForumData();
        forumData.setUser_avatar_url("https://s1.ax1x.com/2022/04/16/Lt5zjA.png");
        for (int i = 0; i < 15; i++) {
            forumList.add(forumData);
        }
        ForumRecyclerAdapter forumRecyclerAdapter = new ForumRecyclerAdapter(forumList);
        final int[] count = {1};
        forumRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                //Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                forumList.add(3, forumData);
                forumRecyclerAdapter.notifyItemInserted(3);
                count[0]++;
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
            }
        });
        forumRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                for (int i = 0; i < 15; i++) {
                    forumList.add(forumList.size(), forumData);
                    forumRecyclerAdapter.notifyItemInserted(forumList.size());
                }
                refreshlayout.finishLoadMore(10/*,false*/);//传入false表示加载失败
                //Toast.makeText(MainActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView forumRecyclerView = aListView.get(3).findViewById(R.id.forumRecyclerView);


        forumRecyclerView.setAdapter(forumRecyclerAdapter);
        LinearLayoutManager forumLinearLayoutManager = new LinearLayoutManager(this);
        forumRecyclerView.setLayoutManager(forumLinearLayoutManager);
        forumLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        /////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////
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
                                webView.onPause();
                                break;
                            case 1:
                                bottomnavigation.setSelectedItemId(R.id.moral);
                                webView.onResume();
                                break;
                            case 2:
                                bottomnavigation.setSelectedItemId(R.id.secondhand);
                                webView.onPause();
                                break;
                            case 3:
                                bottomnavigation.setSelectedItemId(R.id.forums);
                                webView.onPause();
                                break;
                        }
                    }
                }
        );

    }

    @Override
    protected void onPause() {
        super.onPause();
        //webView.pauseTimers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //webView.resumeTimers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//创建一个菜单
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

// attach adapter to viewpager
//    @Override
//    public boolean onSupportNavigateUp() {//重写三条横的实现,即响应事件
//       NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}