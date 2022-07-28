package com.xytong.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.xytong.adapter.ReRecyclerAdapter;
import com.xytong.data.ReData;
import com.xytong.data.viewModel.ReDataViewModel;
import com.xytong.databinding.FragmentRunErrandsBinding;

import java.util.List;

public class RunErrandsFragment extends Fragment {
    FragmentRunErrandsBinding binding;
    ReDataViewModel model;
    ReRecyclerAdapter reRecyclerAdapter;
    CircularProgressIndicator circularProgressIndicator;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRunErrandsBinding.inflate(getLayoutInflater());
        circularProgressIndicator = binding.reProgress;
        RefreshLayout reRefreshLayout = binding.reRefreshLayout;
        reRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        reRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));
        reRefreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(2000);
            new Thread(() -> {
                if (model.refreshData()) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishRefresh(false);
                }
            }).start();
        });
        reRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.finishLoadMore(2000);
            new Thread(() -> {
                if (model.loadMoreData()) {
                    refreshLayout.finishLoadMore(true);
                } else {
                    refreshLayout.finishLoadMore(false);
                }
            }).start();
        });
        reRefreshLayout.setEnableAutoLoadMore(true);
        RecyclerView reRecyclerView = binding.reRecyclerView;
        LinearLayoutManager reLinearLayoutManager = new LinearLayoutManager(this.requireContext());
        reRecyclerView.setLayoutManager(reLinearLayoutManager);
        reLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        new Thread(() -> {
            model = new ViewModelProvider(this).get(ReDataViewModel.class);
            LiveData<List<ReData>> liveData = model.getDataList();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                liveData.observe(getViewLifecycleOwner(), dataList -> {
                    if (reRecyclerView.getAdapter() == null) {
                        Log.e("setAdapter", "ok");
                        circularProgressIndicator.setVisibility(View.GONE);
                        reRecyclerAdapter = new ReRecyclerAdapter(model.getDataList().getValue());
                        reRecyclerView.setAdapter(reRecyclerAdapter);
                    } else {
                        Log.e("dataChange", "data num:" + reRecyclerAdapter.getItemCount());
                        reRecyclerAdapter.notifyDataSetChanged();
                    }
                });
            });

        }).start();
        return binding.getRoot();
    }
}
