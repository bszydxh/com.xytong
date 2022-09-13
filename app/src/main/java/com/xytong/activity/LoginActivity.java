package com.xytong.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xytong.R;
import com.xytong.adapter.LoginPagerAdapter;
import com.xytong.model.entity.UserData;
import com.xytong.databinding.ActivityLoginBinding;
import com.xytong.databinding.PageLoginBinding;
import com.xytong.databinding.PageLogonBinding;
import com.xytong.utils.Access;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
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
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        PageLoginBinding loginBinding = PageLoginBinding.inflate(getLayoutInflater());
        PageLogonBinding logonBinding = PageLogonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayList<View> loginListView = new ArrayList<>();
        loginBinding.loginButton.setOnClickListener(v -> {
            Editable username_edit_text = loginBinding.loginUsername.getText();
            Editable password_edit_text = loginBinding.loginPassword.getText();
            String username = username_edit_text == null ? "" : username_edit_text.toString();
            String pwd = password_edit_text == null ? "" : password_edit_text.toString();

            Access.login(this, pwd, username, new Access.StatusListener() {
                @Override
                public void onStart(Context context) {
                }

                @Override
                public void onDone(Context context) {
                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Context context) {
                    Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                }
            });
        });
        logonBinding.logonButton.setOnClickListener(v -> {
            Editable username_edit_text = loginBinding.loginUsername.getText();
            Editable password_edit_text = loginBinding.loginPassword.getText();
            String username = username_edit_text == null ? "" : username_edit_text.toString();
            String pwd = password_edit_text == null ? "" : password_edit_text.toString();
            UserData userData = new UserData();
            Access.logon(this, userData, pwd, new Access.StatusListener() {
                @Override
                public void onStart(Context context) {

                }

                @Override
                public void onDone(Context context) {

                }

                @Override
                public void onError(Context context) {

                }
            });
        });
        loginListView.add(loginBinding.getRoot());
        loginListView.add(logonBinding.getRoot());
        binding.loginBack.setOnClickListener(v -> finish());
        ViewPager viewPager = binding.loginPager;
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
