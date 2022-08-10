package com.xytong.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.xytong.R;
import com.xytong.ReActivity;
import com.xytong.adapter.ReRecyclerAdapter;
import com.xytong.data.ReData;
import com.xytong.data.viewModel.ReDataViewModel;
import com.xytong.databinding.FragmentReBinding;

import java.util.List;

public class ReFragment extends Fragment {
    FragmentReBinding binding;
    ReDataViewModel model;
    ReRecyclerAdapter reRecyclerAdapter;
    CircularProgressIndicator circularProgressIndicator;
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Log.i("ActivityResultLauncher","reActivity back");
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReBinding.inflate(getLayoutInflater());
        circularProgressIndicator = binding.reProgress;
        RefreshLayout reRefreshLayout = binding.reRefreshLayout;
        reRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        reRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));
        reRefreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(2000);
            new Thread(() -> {
                refreshLayout.finishRefresh(model.refreshData());
            }).start();
        });
        reRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.finishLoadMore(2000);
            new Thread(() -> {
                refreshLayout.finishLoadMore(model.loadMoreData());
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
                        Log.i("setAdapter", "ok");
                        circularProgressIndicator.setVisibility(View.GONE);
                        reRecyclerAdapter = new ReRecyclerAdapter(model.getDataList().getValue());
                        reRecyclerAdapter.setOnItemClickListener(new ReRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onBannerClick(View view) {
                                Toast.makeText(view.getContext(), "banner click!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onTitleClick(View view, int position, ReData reDataIndex) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("reData", reDataIndex);
                                bundle.putInt("pos", position);
                                Intent intent = new Intent(view.getContext(), ReActivity.class);
                                intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
                                mStartForResult.launch(intent);
                            }

                            @Override
                            public void onTitleLongClick(View view, int position) {
                                // TODO
                            }
                        });
                        reRecyclerView.setAdapter(reRecyclerAdapter);
                    } else {
                        Log.i("dataChange", "data num:" + reRecyclerAdapter.getItemCount());
                        reRecyclerAdapter.notifyDataSetChanged();
                    }
                });
            });

        }).start();
        reRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 获取当前滚动到的条目位置
                    int index = reLinearLayoutManager.findFirstVisibleItemPosition();
                    if (index == 0) {
                        Drawable bmpDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_add_24);
                        Drawable.ConstantState state = bmpDrawable.getConstantState();
                        Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
                        DrawableCompat.setTint(wrap, ContextCompat.getColor(getContext(), R.color.white));
                        binding.reFab.setImageDrawable(wrap);
                    } else {
                        Drawable bmpDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_vertical_align_top_24);
                        Drawable.ConstantState state = bmpDrawable.getConstantState();
                        Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
                        DrawableCompat.setTint(wrap, ContextCompat.getColor(getContext(), R.color.white));
                        binding.reFab.setImageDrawable(wrap);
                    }
                }
            }
        });
        binding.reFab.setOnClickListener(v -> {
            reRecyclerView.smoothScrollToPosition(0);
        });
        return binding.getRoot();
    }
}
