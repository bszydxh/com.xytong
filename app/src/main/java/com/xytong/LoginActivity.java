package com.xytong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xytong.adapter.LoginPagerAdapter;
import com.xytong.databinding.ActivityLoginBinding;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private EditText username_edit_text;
    private EditText password_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        binding.loginBack.setOnClickListener(v -> finish());
        ViewPager viewPager = binding.loginPager;
        ArrayList<View> loginListView = new ArrayList<>();
        LayoutInflater layoutInflater = getLayoutInflater();
        loginListView.add(layoutInflater.inflate(R.layout.login, null, false));
        loginListView.add(layoutInflater.inflate(R.layout.logon, null, false));
        LoginPagerAdapter loginPagerAdapter = new LoginPagerAdapter(loginListView);
        viewPager.setAdapter(loginPagerAdapter);
        TabLayout tabLayout = binding.loginTab;
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
    }
}
