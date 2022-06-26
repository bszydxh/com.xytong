package com.xytong;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xytong.adapter.ForumRecyclerAdapter;
import com.xytong.adapter.ReRecyclerAdapter;
import com.xytong.adapter.RootPager2Adapter;
import com.xytong.adapter.ShRecyclerAdapter;
import com.xytong.data.ForumData;
import com.xytong.data.ReData;
import com.xytong.data.ShData;
import com.xytong.databinding.ActivityMainBinding;
import com.xytong.sqlite.MySQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 rootViewPager;
    private WebView webView;
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
        drawer = binding.drawerLayout;//定义DrawerLayout变量drawer,将主视图的drawer赋值到该变量
        NavigationView navigationView = binding.navView;
        Glide.with(this)
                .load("https://s1.ax1x.com/2022/04/16/Lt5zjA.png")
                .into((ImageView) navigationView.getHeaderView(0).findViewById(R.id.drawer_user_avatar));
        Glide.with(this)
                .load("https://s1.ax1x.com/2022/04/16/Lt5zjA.png")
                .into((ImageView) binding.appBarMain.underBar.toolbarUserAvatar);
        //ImageGetter.setViewBitmap(navigationView.getHeaderView(0).findViewById(R.id.drawer_user_avatar), "https://s1.ax1x.com/2022/04/16/Lt5zjA.png");//被弃用的类
        //ImageGetter.setViewBitmap(binding.appBarMain.underBar.toolbarUserAvatar, "https://s1.ax1x.com/2022/04/16/Lt5zjA.png");
        binding.appBarMain.underBar.toolbarUserAvatar.setOnClickListener(view -> {
            drawer.openDrawer(GravityCompat.START);
        });
        //对右下方悬浮按键绑定监听事件
        binding.appBarMain.fab.setOnClickListener(view -> {//@override注释后对编译器会对重写的方法进行检查
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)//snackbar类似于toast,它们在移动设备的屏幕底部和较大设备的左下方显示一条简短消息
                    .setAction("Action", null).show();//设置点击事件
        });
        View nav_header_view = binding.navView.getHeaderView(0);
        nav_header_view.setOnClickListener(new View.OnClickListener() {//对右下方悬浮按键绑定监听事件
            @Override
            public void onClick(View view) {//@override注释后对编译器会对重写的方法进行检查
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //应用侧栏配置
        //三人行,home...
        //显示三条横用的qwq
        AppBarConfiguration mAppBarConfiguration = new AppBarConfiguration.Builder(//应用侧栏配置
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)//三人行,home...
                .setOpenableLayout(drawer)//显示三条横用的qwq
                .build();// 构造AppBarConfiguration实例
        /////////////////////////////////////////////////////////////////////
        ArrayList<View> aListView = new ArrayList<>();
        LayoutInflater layoutInflater = getLayoutInflater();
        aListView.add(layoutInflater.inflate(R.layout.fragment_run_errands, null, false));
        aListView.add(layoutInflater.inflate(R.layout.fragment_moral, null, false));
        aListView.add(layoutInflater.inflate(R.layout.fragment_secondhand, null, false));
        aListView.add(layoutInflater.inflate(R.layout.fragment_forum, null, false));
        RootPager2Adapter viewPager2Adapter = new RootPager2Adapter(aListView);
        rootViewPager = binding.appBarMain.underBar.pager;
        rootViewPager.setAdapter(viewPager2Adapter);
        /////////////////////////////////////////////////////////////////////
        //数据库配置
        //Toast.makeText(this,getApplicationContext().getExternalFilesDir("").getAbsolutePath()+"/mydb.db", Toast.LENGTH_SHORT).show();
        MySQL sql = null;
        try {
            sql = new MySQL(this);
        } catch (RuntimeException e) {
            Toast.makeText(this, "file error,check the log!", Toast.LENGTH_SHORT).show();
        }
        /////////////////////////////////////////////////////////////////////
        //第一页
        /////////////////////////////////////////////////////////////////////
        RefreshLayout reRefreshLayout = aListView.get(0).findViewById(R.id.reRefreshLayout);
        reRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        reRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        List<ReData> reList = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            ReData reData = new ReData();
            reList.add(sql.read_run_errands_data());
        }

        ReRecyclerAdapter reRecyclerAdapter = new ReRecyclerAdapter(reList);
        reRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
            }
        });
        reRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        RecyclerView reRecyclerView = aListView.get(0).findViewById(R.id.reRecyclerView);

        reRecyclerView.setAdapter(reRecyclerAdapter);
        LinearLayoutManager reLinearLayoutManager = new LinearLayoutManager(this);
        reRecyclerView.setLayoutManager(reLinearLayoutManager);
        reLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

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
        webView.loadUrl("http://m.people.cn/");
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
                if (URLUtil.isNetworkUrl(url)) {
                    return false;
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return true;
                //view.loadUrl(url);

            }
        });
        /////////////////////////////////////////////////////////////////////
        //第三页
        /////////////////////////////////////////////////////////////////////
        RefreshLayout shRefreshLayout = aListView.get(2).findViewById(R.id.shRefreshLayout);
        shRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        shRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        List<ShData> shList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            ShData shData = new ShData();
            shList.add(sql.read_secondhand_data());
        }
        ShRecyclerAdapter shRecyclerAdapter = new ShRecyclerAdapter(shList);
        shRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
            }
        });
        shRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        RecyclerView shRecyclerView = aListView.get(2).findViewById(R.id.shRecyclerView);

        shRecyclerView.setAdapter(shRecyclerAdapter);
        LinearLayoutManager shLinearLayoutManager = new LinearLayoutManager(this);
        shRecyclerView.setLayoutManager(shLinearLayoutManager);
        shLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        /////////////////////////////////////////////////////////////////////
        //第四页
        /////////////////////////////////////////////////////////////////////
        //DataDownloader.getForumData("newest",0,10);
        RefreshLayout forumRefreshLayout = aListView.get(3).findViewById(R.id.forumRefreshLayout);
        forumRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        forumRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        //List<ForumData> forumList = new ArrayList<>();
        List<ForumData> forumList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            forumList.add(sql.read_forum_data());
        }
        ForumRecyclerAdapter forumRecyclerAdapter = new ForumRecyclerAdapter(forumList);
        //forumList.addAll(DataDownloader.getForumDataList("newest", 10, 19));
        forumRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
//                DataDownloader.getForumDataList("newest", forumList.size() - 1, forumList.size() + 9);
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
            }
        });
        forumRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                //forumList.addAll(DataDownloader.getForumDataList("newest", 10, 19));
                refreshlayout.finishLoadMore(10/*,false*/);//传入false表示加载失败
                //Toast.makeText(MainActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView forumRecyclerView = aListView.get(3).findViewById(R.id.forumRecyclerView);
        forumRecyclerView.setAdapter(forumRecyclerAdapter);
        LinearLayoutManager forumLinearLayoutManager = new LinearLayoutManager(this);
        forumRecyclerView.setLayoutManager(forumLinearLayoutManager);
        forumLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        forumRecyclerAdapter.setOnItemClickListener(new ForumRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onTitleClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), ForumActivity.class);
                startActivity(intent);
            }

            @Override
            public void onTitleLongClick(View view, int position) {
                // TODO: 2022/4/29  
            }
        });
        /////////////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////
        BottomNavigationView bottomNavigationView = binding.appBarMain.underBar.btmNav;
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        String nav_name = item.getTitle().toString();
                        switch (nav_name) {
                            case "跑腿":
                                rootViewPager.setCurrentItem(0,false);
                                break;
                            case "德育":
                                rootViewPager.setCurrentItem(1,false);
                                break;
                            case "二手":
                                rootViewPager.setCurrentItem(2,false);
                                break;
                            case "论坛":
                                rootViewPager.setCurrentItem(3,false);
                                break;
                        }
                        return true;
                    }
                }
        );
        rootViewPager.setUserInputEnabled(false);
        rootViewPager.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        switch (position) {
                            case 0:
                                bottomNavigationView.setSelectedItemId(R.id.run_errands);
                                webView.onPause();
                                webViewIsFocused = false;
                                break;
                            case 1:
                                bottomNavigationView.setSelectedItemId(R.id.moral);
                                webView.onResume();
                                webViewIsFocused = true;
                                break;
                            case 2:
                                bottomNavigationView.setSelectedItemId(R.id.secondhand);
                                webView.onPause();
                                webViewIsFocused = false;
                                break;
                            case 3:
                                bottomNavigationView.setSelectedItemId(R.id.forums);
                                webView.onPause();
                                webViewIsFocused = false;
                                break;
                        }
                    }
                }
        );

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
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {//创建一个菜单
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

}