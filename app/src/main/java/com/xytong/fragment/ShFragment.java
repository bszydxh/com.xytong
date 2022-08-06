package com.xytong.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.xytong.ShActivity;
import com.xytong.adapter.ShRecyclerAdapter;
import com.xytong.data.ShData;
import com.xytong.data.viewModel.ShDataViewModel;
import com.xytong.databinding.FragmentSecondhandBinding;

import java.util.List;

public class ShFragment extends Fragment {
    FragmentSecondhandBinding binding;
    ShRecyclerAdapter shRecyclerAdapter;
    ShDataViewModel model;
    CircularProgressIndicator circularProgressIndicator;
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Log.i("ActivityResultLauncher","shActivity back");
                }
            });
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSecondhandBinding.inflate(getLayoutInflater());
        circularProgressIndicator = binding.shProgress;
        RefreshLayout shRefreshLayout = binding.shRefreshLayout;
        shRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        shRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));
        shRefreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(2000);
            new Thread(() -> {
                if (model.refreshData()) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishRefresh(false);
                }
            }).start();
        });
        shRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.finishLoadMore(2000);
            new Thread(() -> {
                if (model.loadMoreData()) {
                    refreshLayout.finishLoadMore(true);
                } else {
                    refreshLayout.finishLoadMore(false);
                }
            }).start();
        });
        shRefreshLayout.setEnableAutoLoadMore(true);
        RecyclerView shRecyclerView = binding.shRecyclerView;
        LinearLayoutManager shLinearLayoutManager = new LinearLayoutManager(this.requireContext());
        shRecyclerView.setLayoutManager(shLinearLayoutManager);
        shLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        new Thread(() -> {
            model = new ViewModelProvider(this).get(ShDataViewModel.class);
            LiveData<List<ShData>> liveData = model.getDataList();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                liveData.observe(getViewLifecycleOwner(), dataList -> {
                    if (shRecyclerView.getAdapter() == null) {
                        Log.e("setAdapter", "ok");
                        circularProgressIndicator.setVisibility(View.GONE);
                        shRecyclerAdapter = new ShRecyclerAdapter(dataList);
                        shRecyclerAdapter.setOnItemClickListener(new ShRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onTitleClick(View view, int position, ShData reDataIndex) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("shData", reDataIndex);
                                bundle.putInt("pos", position);
                                Intent intent = new Intent(view.getContext(), ShActivity.class);
                                intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
                                mStartForResult.launch(intent);
                            }

                            @Override
                            public void onTitleLongClick(View view, int position) {
                                // TODO
                            }
                        });
                        shRecyclerView.setAdapter(shRecyclerAdapter);
                    } else {
                        Log.e("dataChange", "data num:" + shRecyclerAdapter.getItemCount());
                        shRecyclerAdapter.notifyDataSetChanged();
                    }
                });
            });

        }).start();
        return binding.getRoot();
    }

}
