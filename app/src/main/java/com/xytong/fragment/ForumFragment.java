package com.xytong.fragment;

import android.annotation.SuppressLint;
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
import com.xytong.ForumActivity;
import com.xytong.R;
import com.xytong.adapter.ForumRecyclerAdapter;
import com.xytong.data.ForumData;
import com.xytong.data.viewModel.ForumDataViewModel;
import com.xytong.databinding.FragmentForumBinding;

import java.util.List;

public class ForumFragment extends Fragment {
    FragmentForumBinding binding;
    ForumRecyclerAdapter forumRecyclerAdapter;
    ForumDataViewModel model;
    CircularProgressIndicator circularProgressIndicator;
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        ForumData data = (ForumData) intent.getExtras().getSerializable("forumData");
                        List<ForumData> dataList = model.getDataList().getValue();
                        int pos = intent.getExtras().getInt("pos");
                        if (dataList != null) {
                            dataList.remove(pos);
                            dataList.add(pos, data);
                        }
                        model.setDataList(dataList);
                    }
                }
            });

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentForumBinding.inflate(getLayoutInflater());
        circularProgressIndicator = binding.forumProgress;
        RecyclerView forumRecyclerView = binding.forumRecyclerView;
        LinearLayoutManager forumLinearLayoutManager = new LinearLayoutManager(this.requireContext());
        forumRecyclerView.setLayoutManager(forumLinearLayoutManager);
        forumLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RefreshLayout forumRefreshLayout = binding.forumRefreshLayout;
        forumRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        forumRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));

        forumRefreshLayout.setOnRefreshListener(refreshLayout ->
        {
            refreshLayout.finishRefresh(2000);
            new Thread(() -> refreshLayout.finishRefresh(model.refreshData())).start();
        });
        forumRefreshLayout.setOnLoadMoreListener(refreshLayout ->
        {
            refreshLayout.finishLoadMore(2000);
            new Thread(() -> refreshLayout.finishLoadMore(model.loadMoreData())).start();
        });
        forumRefreshLayout.setEnableAutoLoadMore(true);
        forumRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 获取当前滚动到的条目位置
                    int index = forumLinearLayoutManager.findFirstVisibleItemPosition();
                    if (index == 0) {
                        Drawable bmpDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_add_24);
                        Drawable.ConstantState state = bmpDrawable.getConstantState();
                        Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
                        DrawableCompat.setTint(wrap, ContextCompat.getColor(getContext(), R.color.white));
                        binding.forumFab.setImageDrawable(wrap);
                    } else {
                        Drawable bmpDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_baseline_vertical_align_top_24);
                        Drawable.ConstantState state = bmpDrawable.getConstantState();
                        Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
                        DrawableCompat.setTint(wrap, ContextCompat.getColor(getContext(), R.color.white));
                        binding.forumFab.setImageDrawable(wrap);
                    }
                }
            }
        });
        binding.forumFab.setOnClickListener(v -> {
            forumRecyclerView.smoothScrollToPosition(0);
        });
        new Thread(() -> {
            model = new ViewModelProvider(this).get(ForumDataViewModel.class);
            LiveData<List<ForumData>> liveData = model.getDataList();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> liveData.observe(getViewLifecycleOwner(), dataList -> {
                if (forumRecyclerView.getAdapter() == null) {
                    Log.i("setAdapter", "ok");
                    circularProgressIndicator.setVisibility(View.GONE);
                    forumRecyclerAdapter = new ForumRecyclerAdapter(dataList);
                    forumRecyclerAdapter.setOnItemClickListener(new ForumRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onTitleClick(View view, int position, ForumData forumDataIndex) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("forumData", forumDataIndex);
                            bundle.putInt("pos", position);
                            Intent intent = new Intent(view.getContext(), ForumActivity.class);
                            intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
                            mStartForResult.launch(intent);
                        }

                        @Override
                        public void onTitleLongClick(View view, int position) {
                            // TODO
                        }
                    });
                    forumRecyclerView.setAdapter(forumRecyclerAdapter);
                } else {
                    Log.i("dataChange", "data num:" + forumRecyclerAdapter.getItemCount());
                    forumRecyclerAdapter.notifyDataSetChanged();
                }
            }));
        }).start();
        return binding.getRoot();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {

    }
}
