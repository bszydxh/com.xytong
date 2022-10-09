package com.xytong.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.xytong.R;
import com.xytong.adapter.LoginPagerAdapter;
import com.xytong.databinding.ActivityLoginBinding;
import com.xytong.databinding.PageLoginBinding;
import com.xytong.databinding.PageLogonBinding;
import com.xytong.model.vo.UserVO;
import com.xytong.utils.AccessUtils;
import com.xytong.utils.ViewCreateUtils;

import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewCreateUtils.setBlackStatusBar(this);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        PageLoginBinding loginBinding = PageLoginBinding.inflate(getLayoutInflater());
        PageLogonBinding logonBinding = PageLogonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        logonBinding.progress.setVisibility(View.INVISIBLE);
        loginBinding.progress.setVisibility(View.INVISIBLE);
        ArrayList<View> loginListView = new ArrayList<>();
        loginBinding.button.setOnClickListener(v -> {
            Editable username_edit_text = loginBinding.username.getText();
            Editable password_edit_text = loginBinding.password.getText();
            String username = username_edit_text == null ? "" : username_edit_text.toString();
            String pwd = password_edit_text == null ? "" : password_edit_text.toString();
            Log.i("login", "username:" + username + "\npassword:" + pwd);
            AccessUtils.login(this, username, pwd, new AccessUtils.StatusListener() {
                @Override
                public void onStart(Context context) {
                    loginBinding.progress.setVisibility(View.VISIBLE);
                    loginBinding.button.setVisibility(View.GONE);
                }

                @Override
                public void onDone(Context context) {
                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(Context context, int errorFlag) {
                    if(errorFlag == AccessUtils.USERNAME_OR_PASSWORD_ERROR)
                    {
                        Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    } else if (errorFlag == AccessUtils.SERVER_ERROR) {
                        Toast.makeText(context, "未连接网络", Toast.LENGTH_SHORT).show();
                    }
                    loginBinding.progress.setVisibility(View.INVISIBLE);
                    loginBinding.button.setVisibility(View.VISIBLE);
                }
            });
        });
        var pwdWatcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Editable password_edit_text = logonBinding.password.getText();
                Editable password_check_edit_text = logonBinding.passwordCheck.getText();
                String pwd = password_edit_text == null ? "" : password_edit_text.toString();
                String pwd_check = password_check_edit_text == null ? "" : password_check_edit_text.toString();
                if (!Objects.equals(pwd, pwd_check)) {
                    logonBinding.logonPasswordCheckLayout.setError("两次密码输入不一致！");
                } else {
                    logonBinding.logonPasswordCheckLayout.setErrorEnabled(false);
                }
            }
        };
        logonBinding.passwordCheck.addTextChangedListener(pwdWatcher);
        logonBinding.password.addTextChangedListener(pwdWatcher);
        logonBinding.logonPasswordCheckLayout.setOnClickListener(v ->
        {
        });
        logonBinding.button.setOnClickListener(v ->
        {
            Editable username_edit_text = logonBinding.username.getText();
            Editable phone_edit_text = logonBinding.phone.getText();
            Editable password_edit_text = logonBinding.password.getText();
            Editable password_check_edit_text = logonBinding.passwordCheck.getText();
            String username = username_edit_text == null ? "" : username_edit_text.toString();
            String phone = phone_edit_text == null ? "" : phone_edit_text.toString();
            String pwd = password_edit_text == null ? "" : password_edit_text.toString();
            String pwd_check = password_check_edit_text == null ? "" : password_check_edit_text.toString();
            UserVO userVO = new UserVO();
            userVO.setName(username);
            userVO.setPhoneNumber(phone);
            AccessUtils.logon(this, userVO, pwd, new AccessUtils.StatusListener() {
                @Override
                public void onStart(Context context) {
                    logonBinding.progress.setVisibility(View.VISIBLE);
                    logonBinding.button.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onDone(Context context) {
                    Toast.makeText(context, "注册成功，已为您自动登录", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(Context context, int errorFlag) {
                    Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show();
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
        // Create the scenes


    }

    @Override
    public void finish() {
        setResult(Activity.RESULT_OK);
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
    }

}
