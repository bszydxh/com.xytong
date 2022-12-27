package com.xytong.fragment;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.xytong.R;
import com.xytong.activity.ForumActivity;
import com.xytong.activity.PublishActivity;
import com.xytong.activity.UserActivity;
import com.xytong.adapter.ForumRecyclerAdapter;
import com.xytong.databinding.FragmentForumBinding;
import com.xytong.model.vo.ForumVO;
import com.xytong.model.vo.UserVO;
import com.xytong.utils.ViewCreateUtils;
import com.xytong.viewModel.ForumDataViewModel;

import java.util.List;

public class ForumFragment extends Fragment {
    FragmentForumBinding binding;
    ForumRecyclerAdapter forumRecyclerAdapter;
    ForumDataViewModel model;
    CircularProgressIndicator circularProgressIndicator;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForumBinding.inflate(getLayoutInflater());
        model = new ViewModelProvider(this).get(ForumDataViewModel.class);//应在主线程加载
        circularProgressIndicator = binding.forumProgress;
        LiveData<List<ForumVO>> liveData = model.getDataList();
        RecyclerView forumRecyclerView = binding.forumRecyclerView;
        RefreshLayout forumRefreshLayout = binding.forumRefreshLayout;
        LinearLayoutManager forumLinearLayoutManager = new LinearLayoutManager(this.requireContext());
        ActivityResultLauncher<Intent> forumActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            ForumVO data = (ForumVO) intent.getExtras().getSerializable("forumData");
                            List<ForumVO> dataList = model.getDataList().getValue();
                            int pos = intent.getExtras().getInt("pos");
                            if (dataList != null) {
                                dataList.remove(pos);
                                dataList.add(pos, data);
                            }
                            model.setDataList(dataList);
                        }
                    }
                });
        ActivityResultLauncher<Intent> publishActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        forumRefreshLayout.autoRefresh();
                    }
                });
        forumRecyclerAdapter = new ForumRecyclerAdapter(liveData.getValue());
        forumRecyclerAdapter.setOnItemClickListener(new ForumRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onUserClick(View view, UserVO userVO) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("userData", userVO);
                Intent intent = new Intent(view.getContext(), UserActivity.class);
                intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
                view.getContext().startActivity(intent);
            }

            @Override
            public void onTitleClick(View view, int position, ForumVO forumDataIndex) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("forumData", forumDataIndex);
                bundle.putInt("pos", position);
                Intent intent = new Intent(view.getContext(), ForumActivity.class);
                intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
                forumActivityResultLauncher.launch(intent);
            }

            @Override
            public void onTitleLongClick(View view, int position) {
                // TODO
            }
        });
        forumRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 获取当前滚动到的条目位置
                    int index = forumLinearLayoutManager.findFirstVisibleItemPosition();
                    if (index == 0) {
                        ViewCreateUtils.setImage(
                                binding.forumFab,
                                R.drawable.ic_baseline_add_24,
                                R.color.white
                        );
                    } else {
                        ViewCreateUtils.setImage(
                                binding.forumFab,
                                R.drawable.ic_baseline_vertical_align_top_24,
                                R.color.white
                        );
                    }
                }
            }
        });
        forumRecyclerView.setAdapter(forumRecyclerAdapter);
        forumRecyclerView.setLayoutManager(forumLinearLayoutManager);
        forumLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        forumRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        forumRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));
        forumRefreshLayout.setOnRefreshListener(refreshLayout ->
        {
            model.refreshData();
            refreshLayout.finishRefresh(2000);
        });
        forumRefreshLayout.setOnLoadMoreListener(refreshLayout ->
        {
            model.loadMoreData();
            refreshLayout.finishLoadMore(2000);
        });
        forumRefreshLayout.setEnableAutoLoadMore(true);
        liveData.observe(getViewLifecycleOwner(), dataList -> {
            if (forumRecyclerView.getAdapter() != null) {
                if (dataList.size() > 0) {
                    circularProgressIndicator.setVisibility(View.GONE);
                }
                Log.i("dataChange", "data num:" + forumRecyclerAdapter.getItemCount());
                RefreshLayout mReRefreshLayout = binding.forumRefreshLayout;
                mReRefreshLayout.finishRefresh();
                mReRefreshLayout.finishLoadMore();
                forumRecyclerAdapter.notifyDataSetChanged();
            }
        });
        binding.forumFab.setOnClickListener(v -> {
            int index = forumLinearLayoutManager.findFirstVisibleItemPosition();
            if (index <= 0) {
                Bundle bundle = new Bundle();
                bundle.putString("name", "forum");
                Intent intent = new Intent(v.getContext(), PublishActivity.class);
                intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
                publishActivityResultLauncher.launch(intent);
            } else {
                forumRecyclerView.smoothScrollToPosition(0);
            }
        });
        model.refreshData();
        return binding.getRoot();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {

    }
}
