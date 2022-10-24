package com.xytong.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.xytong.R;
import com.xytong.activity.PublishActivity;
import com.xytong.activity.ShActivity;
import com.xytong.activity.UserActivity;
import com.xytong.adapter.ShRecyclerAdapter;
import com.xytong.databinding.FragmentShBinding;
import com.xytong.model.vo.ShVO;
import com.xytong.model.vo.UserVO;
import com.xytong.utils.ViewCreateUtils;
import com.xytong.viewModel.ShDataViewModel;

import java.util.List;

public class ShFragment extends Fragment {
    FragmentShBinding binding;
    ShRecyclerAdapter shRecyclerAdapter;
    ShDataViewModel model;
    CircularProgressIndicator circularProgressIndicator;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.i("ActivityResultLauncher", "shActivity back");
                    }
                });
        binding = FragmentShBinding.inflate(getLayoutInflater());
        model = new ViewModelProvider(this).get(ShDataViewModel.class);
        circularProgressIndicator = binding.shProgress;
        RefreshLayout shRefreshLayout = binding.shRefreshLayout;
        shRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        shRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));
        shRefreshLayout.setOnRefreshListener(refreshLayout -> {
            model.refreshData();
            refreshLayout.finishRefresh(2000);
        });
        shRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            model.loadMoreData();
            refreshLayout.finishLoadMore(2000);
        });
        shRefreshLayout.setEnableAutoLoadMore(true);
        RecyclerView shRecyclerView = binding.shRecyclerView;
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        shRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        LiveData<List<ShVO>> liveData = model.getDataList();
        liveData.observe(getViewLifecycleOwner(), dataList -> {
            if (shRecyclerView.getAdapter() == null) {
                Log.i("setAdapter", "ok");
                circularProgressIndicator.setVisibility(View.GONE);
                shRecyclerAdapter = new ShRecyclerAdapter(dataList);
                shRecyclerAdapter.setOnItemClickListener(new ShRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onUserClick(View view, UserVO userVO) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", userVO);
                        Intent intent = new Intent(view.getContext(), UserActivity.class);
                        intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
                        view.getContext().startActivity(intent);
                    }

                    @Override
                    public void onTitleClick(View view, int position, ShVO reDataIndex) {
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
                Log.i("dataChange", "data num:" + shRecyclerAdapter.getItemCount());
                RefreshLayout mShRefreshLayout = binding.shRefreshLayout;
                mShRefreshLayout.finishRefresh();
                mShRefreshLayout.finishLoadMore();
                shRecyclerAdapter.notifyDataSetChanged();
            }
        });
        shRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 获取当前滚动到的条目位置
                    int index = staggeredGridLayoutManager.findFirstVisibleItemPositions(new int[2])[0];
                    if (index == 0) {
                        ViewCreateUtils.setImage(
                                binding.shFab,
                                R.drawable.ic_baseline_add_24,
                                R.color.white
                        );
                    } else {
                        ViewCreateUtils.setImage(
                                binding.shFab,
                                R.drawable.ic_baseline_vertical_align_top_24,
                                R.color.white
                        );
                    }
                }
            }
        });
        binding.shFab.setOnClickListener(v -> {
            int index = staggeredGridLayoutManager.findFirstVisibleItemPositions(new int[2])[0];
            if (index == 0) {
                v.getContext().startActivity(new Intent(v.getContext(), PublishActivity.class));
            } else {
                shRecyclerView.smoothScrollToPosition(0);
            }
        });
        return binding.getRoot();
    }

}
