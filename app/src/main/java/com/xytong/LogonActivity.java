package com.xytong;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xytong.databinding.ActivityLogonBinding;

public class LogonActivity extends AppCompatActivity {
    private ActivityLogonBinding binding;
    private EditText username_edit_text;
    private EditText password_edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);//进入渐变动画
        super.onCreate(savedInstanceState);
        binding = ActivityLogonBinding.inflate(getLayoutInflater());
        username_edit_text = binding.logonUsername;
        password_edit_text = binding.logonPassword;
        setContentView(binding.getRoot());//binding中getRoot()方法是对binding根视图的引用,也相当于创建视图
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //请求服务器得到结果

                Toast.makeText(LogonActivity.this, "sign in!" + username_edit_text.getText()+"||"
                        + password_edit_text.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LogonActivity.this, "sign up!" + username_edit_text.getText()+"||"
                        + password_edit_text.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        binding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LogonActivity.this, "forget password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fragment_fade_enter, R.anim.fragment_fade_exit);//退出渐变动画
    }
}
