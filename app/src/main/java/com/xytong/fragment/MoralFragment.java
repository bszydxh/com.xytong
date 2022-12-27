package com.xytong.fragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.xytong.R;
import com.xytong.adapter.WebViewPagerAdapter;
import com.xytong.databinding.FragmentMoralBinding;
import com.xytong.databinding.PageWebviewBinding;

import java.util.ArrayList;
import java.util.List;

public class MoralFragment extends Fragment {
    FragmentMoralBinding binding;
    WebView webView;
    WebViewPagerAdapter webViewPagerAdapter;


    @SuppressLint("SetJavaScriptEnabled")
    private View getWebView(String url, boolean add) {
        PageWebviewBinding pageWebviewBinding = PageWebviewBinding.inflate(getLayoutInflater());
        WebView webView = pageWebviewBinding.webView;
        WebSettings webSettings = webView.getSettings();
        //设置自适应屏幕
        webSettings.setJavaScriptEnabled(true);//设置支持js
        webSettings.setAllowFileAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        if (add) {
            webView.loadUrl(url);
        }
        WebChromeClient webChromeClient = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) { // 显示加载进度，自选
                LinearProgressIndicator progressView = binding.progressBar;
                progressView.setProgress(progress);
                progressView.setVisibility((progress > 0 && progress < 100) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                callback.invoke(origin, true, false); // 页面有请求位置的时候需要
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return true;
            }
        };

        webView.setWebChromeClient(webChromeClient);
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
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                switch (error.getPrimaryError()) {
                    case SslError.SSL_INVALID: // 校验过程遇到了bug
                    case SslError.SSL_UNTRUSTED: // 证书有问题
                        handler.proceed();
                    default:
                        handler.cancel();
                }
            }
        });
        return pageWebviewBinding.getRoot();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoralBinding.inflate(getLayoutInflater());
        ViewPager viewPager = binding.webPager;
        ArrayList<View> viewListView = new ArrayList<>();
        List<String> menuList = new ArrayList<>();
        menuList.add("人民网");
        menuList.add("新华网");
        menuList.add("党史学习");
        menuList.add("学习强国");
        menuList.add("求是");
        menuList.add("大学生党校");
        webViewPagerAdapter = new WebViewPagerAdapter(viewListView, menuList);
        webViewPagerAdapter.addItem(getWebView("http://m.people.cn/", true));
        for (int i = 0; i < 5; i++) {
            webViewPagerAdapter.addItem(getWebView("", false));
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                WebView webViewIndex = webViewPagerAdapter.getViewLists().get(position).findViewById(R.id.web_view);
                switch (position) {
                    case 0: {
                        if (webViewIndex.getUrl() == null) {
                            webViewIndex.loadUrl("http://m.people.cn/");
                        }
                        break;
                    }
                    case 1: {
                        if (webViewIndex.getUrl() == null) {
                            webViewIndex.loadUrl("http://m.news.cn/");
                        }
                        break;
                    }
                    case 2: {
                        if (webViewIndex.getUrl() == null) {
                            webViewIndex.loadUrl("http://www.12371.cn/dsxx/");
                        }
                        break;
                    }
                    case 3: {
                        if (webViewIndex.getUrl() == null) {
                            webViewIndex.loadUrl("http://www.xuexi.cn/");
                        }
                        break;
                    }
                    case 4: {
                        if (webViewIndex.getUrl() == null) {
                            webViewIndex.loadUrl("http://m.qstheory.cn");
                        }
                        break;
                    }
                    case 5: {
                        if (webViewIndex.getUrl() == null) {
                            webViewIndex.loadUrl("http://www.uucps.edu.cn/");
                        }
                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(webViewPagerAdapter);
        TabLayout tabLayout = binding.tab;
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(viewPager);
        binding.moralFab.setOnClickListener(v -> {
            webView = webViewPagerAdapter.getPrimaryItem().findViewById(R.id.web_view);
            webView.reload();
        });
        return binding.getRoot();
    }

    public boolean webViewBack() {
        webView = webViewPagerAdapter.getPrimaryItem().findViewById(R.id.web_view);
        if (webView.canGoBack()) {
            webView.goBack();
            return true;//代表返回成功，反之返回失败
        } else {
            return false;
        }

    }
}
