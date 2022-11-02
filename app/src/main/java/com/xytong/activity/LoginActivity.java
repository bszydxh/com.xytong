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
import com.xytong.databinding.PageSignupBinding;
import com.xytong.model.dto.captcha.CaptchaSendRequestDTO;
import com.xytong.model.dto.captcha.CaptchaSendResponseDTO;
import com.xytong.model.vo.UserVO;
import com.xytong.utils.AccessUtils;
import com.xytong.utils.ViewCreateUtils;
import com.xytong.utils.poster.Poster;

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
        PageSignupBinding signupBinding = PageSignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        signupBinding.button.setClickable(false);
        signupBinding.button.setBackgroundColor(getResources().getColor(R.color.dark_gray, getTheme()));
        signupBinding.progress.setVisibility(View.INVISIBLE);
        loginBinding.progress.setVisibility(View.INVISIBLE);

        AccessUtils.StatusListener loginListener = new AccessUtils.StatusListener() {
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
                if (errorFlag == AccessUtils.USERNAME_OR_PASSWORD_ERROR) {
                    Toast.makeText(context, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                } else if (errorFlag == AccessUtils.SERVER_ERROR) {
                    Toast.makeText(context, "未连接网络", Toast.LENGTH_SHORT).show();
                }
                loginBinding.progress.setVisibility(View.INVISIBLE);
                loginBinding.button.setVisibility(View.VISIBLE);
            }
        };
        ArrayList<View> loginListView = new ArrayList<>();
        loginBinding.button.setOnClickListener(v -> {
            Editable username_edit_text = loginBinding.username.getText();
            Editable password_edit_text = loginBinding.password.getText();
            String username = username_edit_text == null ? "" : username_edit_text.toString();
            String pwd = password_edit_text == null ? "" : password_edit_text.toString();
            Log.i("login", "username:" + username + "\npassword:" + pwd);
            AccessUtils.login(this, username, pwd, loginListener);
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
                Editable password_edit_text = signupBinding.password.getText();
                Editable password_check_edit_text = signupBinding.passwordCheck.getText();
                String pwd = password_edit_text == null ? "" : password_edit_text.toString();
                String pwd_check = password_check_edit_text == null ? "" : password_check_edit_text.toString();
                if (!Objects.equals(pwd, pwd_check)) {
                    signupBinding.logonPasswordCheckLayout.setError("两次密码输入不一致！");
                    signupBinding.button.setClickable(false);
                    signupBinding.button.setBackgroundColor(getResources().getColor(R.color.dark_gray, getTheme()));
                } else {
                    signupBinding.logonPasswordCheckLayout.setErrorEnabled(false);
                    signupBinding.button.setClickable(true);
                    signupBinding.button.setBackgroundColor(getResources().getColor(R.color.sky_blue, getTheme()));
                }
            }
        };
        signupBinding.passwordCheck.addTextChangedListener(pwdWatcher);
        signupBinding.password.addTextChangedListener(pwdWatcher);
        signupBinding.logonPasswordCheckLayout.setOnClickListener(v ->
        {
        });
        signupBinding.captchaLayout.setEndIconOnClickListener(v ->
        {
            Editable email_edit_text = signupBinding.email.getText();
            String email = email_edit_text == null ? "" : email_edit_text.toString();
            CaptchaSendRequestDTO captchaSendRequestDTO = new CaptchaSendRequestDTO();
            captchaSendRequestDTO.setEmail(email);
            captchaSendRequestDTO.setTimestamp(System.currentTimeMillis());
            new Thread(() -> Poster.jacksonPost(
                    "http:xytong.top:7426/captcha/v1/send",
                    captchaSendRequestDTO,
                    CaptchaSendResponseDTO.class
            )).start();
            Toast.makeText(this, "验证码发送成功", Toast.LENGTH_SHORT).show();
        });
        signupBinding.button.setOnClickListener(v ->
        {
            Editable username_edit_text = signupBinding.username.getText();
            Editable password_edit_text = signupBinding.password.getText();
            Editable email_edit_text = signupBinding.email.getText();
            Editable captcha_edit_text = signupBinding.captcha.getText();
            Editable password_check_edit_text = signupBinding.passwordCheck.getText();
            String username = username_edit_text == null ? "" : username_edit_text.toString();
            String pwd = password_edit_text == null ? "" : password_edit_text.toString();
            String email = email_edit_text == null ? "" : email_edit_text.toString();
            String pwd_check = password_check_edit_text == null ? "" : password_check_edit_text.toString();
            String captcha = captcha_edit_text == null ? "" : captcha_edit_text.toString();
            UserVO userVO = new UserVO();
            userVO.setName(username);
            userVO.setEmail(email);
            AccessUtils.signup(this, userVO, captcha, pwd, new AccessUtils.StatusListener() {
                @Override
                public void onStart(Context context) {
                    signupBinding.progress.setVisibility(View.VISIBLE);
                    signupBinding.button.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onDone(Context context) {
                    Toast.makeText(context, "注册成功，已为您自动登录", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(Context context, int errorFlag) {
                    Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show();
                    signupBinding.progress.setVisibility(View.INVISIBLE);
                    signupBinding.button.setVisibility(View.VISIBLE);
                }
            });
        });
        loginListView.add(loginBinding.getRoot());
        loginListView.add(signupBinding.getRoot());
        binding.loginBack.setOnClickListener(v -> finish());
        ViewPager viewPager = binding.loginPager;
        LoginPagerAdapter loginPagerAdapter = new LoginPagerAdapter(loginListView);
        viewPager.setAdapter(loginPagerAdapter);
        TabLayout tabLayout = binding.loginTab;
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void finish() {
        setResult(Activity.RESULT_OK);
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
    }

}
