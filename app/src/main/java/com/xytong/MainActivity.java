package com.xytong;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.xytong.databinding.ActivityMainBinding;
import com.xytong.databinding.AppBarMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;//为activity_main.xml绑定视图,先定义一个类,之后赋值

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//声明onCreate,方法继承之前的状态

        binding = ActivityMainBinding.inflate(getLayoutInflater());//赋值阶段,inflate为调用生成的绑定类中包含的静态方法。这将为要使用的活动创建一个绑定类的实例。
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用
        setSupportActionBar(binding.appBarMain.toolbar);//设置toolbar
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {//对右下方悬浮按键绑定监听事件
            @Override
            public void onClick(View view) {//@override注释后对编译器会对重写的方法进行检查
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)//snackbar类似于toast,它们在移动设备的屏幕底部和较大设备的左下方显示一条简短消息
                        .setAction("Action", null).show();//设置点击事件
            }
        });
        binding.appBarMain.toolbar.setOnClickListener(new View.OnClickListener() {
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
        Toast toast = Toast.makeText(MainActivity.this, "this is a toast", Toast.LENGTH_LONG);
        toast.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//创建一个菜单
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {//重写三条横的实现,即响应事件
//       NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}