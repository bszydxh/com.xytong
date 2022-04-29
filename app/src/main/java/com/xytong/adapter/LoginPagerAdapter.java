package com.xytong.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class LoginPagerAdapter extends PagerAdapter {
    private ArrayList<View> viewLists;

    public LoginPagerAdapter() {
    }

    public LoginPagerAdapter(ArrayList<View> viewLists) {
        super();
        this.viewLists = viewLists;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        return viewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) {
            return "登录";
        }else{
            return "注册";
        }
    }
}
