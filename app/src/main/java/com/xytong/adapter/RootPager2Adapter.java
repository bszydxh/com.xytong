package com.xytong.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xytong.R;

import java.util.ArrayList;

public class RootPager2Adapter extends RecyclerView.Adapter<RootPager2Adapter.ViewHolder> {
    private ArrayList<View> viewLists;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout root;

        public ViewHolder(View view) {
            super(view);
            root = view.findViewById(R.id.pager_default);
        }

        public LinearLayout getLayoutRoot() {
            return root;
        }
    }
    public RootPager2Adapter(ArrayList<View> viewLists)
    {
        this.viewLists = viewLists;
    }
    @NonNull
    @Override
    public RootPager2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.pager_null, viewGroup, false);
        return new RootPager2Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RootPager2Adapter.ViewHolder viewHolder, final int position) {
        if(viewHolder.getLayoutRoot().getChildAt(0) != null) {
            viewHolder.getLayoutRoot().removeView(viewLists.get(position));
        }
        viewHolder.getLayoutRoot().addView(viewLists.get(position));

    }

    @Override
    public int getItemCount() {
        return viewLists.size();
    }
}