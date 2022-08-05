package com.xytong.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.xytong.databinding.FragmentMoralBinding;

public class MoralFragment extends Fragment {
    FragmentMoralBinding binding;
    WebView webView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMoralBinding.inflate(getLayoutInflater());
        webView = binding.webView;
        WebSettings webSettings = webView.getSettings();
        //设置自适应屏幕
        webSettings.setJavaScriptEnabled(true);//设置支持js
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
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        return binding.getRoot();
    }

    public void webViewBack()
    {
        webView.goBack();
    }
}
