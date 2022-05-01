package com.xytong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);//进入渐变动画
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        binding.loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);//退出渐变动画
    }
}
