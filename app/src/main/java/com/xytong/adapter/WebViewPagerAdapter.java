package com.xytong.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class WebViewPagerAdapter extends PagerAdapter {
    private final ArrayList<View> viewLists;
    private View currentView;
    private final List<String> menuLists;

    public WebViewPagerAdapter(ArrayList<View> viewLists, List<String> menuLists) {
        super();
        this.menuLists = menuLists;
        this.viewLists = viewLists;
    }

    @Override
    public int getCount() {
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void addItem(View view) {
        viewLists.add(view);
    }

    @NonNull
    @Override
    public View instantiateItem(ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        return viewLists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView(viewLists.get(position));
    }

    public ArrayList<View> getViewLists() {
        return viewLists;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        currentView = (View) object;
    }

    public View getPrimaryItem() {
        return currentView;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return menuLists.get(position);
    }

}
