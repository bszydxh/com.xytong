package com.xytong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.xytong.ForumActivity;
import com.xytong.adapter.ForumRecyclerAdapter;
import com.xytong.data.ForumData;
import com.xytong.data.viewModel.ForumDataViewModel;
import com.xytong.databinding.FragmentForumBinding;

public class ForumFragment extends Fragment {
    FragmentForumBinding binding;
    ForumRecyclerAdapter forumRecyclerAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentForumBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ForumDataViewModel forumDataViewModel = new ViewModelProvider(this).get(ForumDataViewModel.class);
        forumDataViewModel.getDataList().observe(getViewLifecycleOwner(), dataList -> {
        });

        RefreshLayout forumRefreshLayout = binding.forumRefreshLayout;
        forumRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        forumRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));
        forumRecyclerAdapter = new ForumRecyclerAdapter(forumDataViewModel.getDataList().getValue());
        forumRefreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(2000);
            if (forumDataViewModel.refreshData()) {
                refreshLayout.finishRefresh(true);
                Toast.makeText(refreshLayout.getLayout().getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                forumRecyclerAdapter.notifyDataSetChanged();
            }
            else{
                refreshLayout.finishRefresh(false);
                Toast.makeText(refreshLayout.getLayout().getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
            }
        });
        forumRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.finishLoadMore(2000);
            if (forumDataViewModel.loadMoreData()) {
                forumRecyclerAdapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore(true);
            }
            else{
                refreshLayout.finishLoadMore(false);
            }
        });
        RecyclerView forumRecyclerView = binding.forumRecyclerView;
        forumRecyclerView.setAdapter(forumRecyclerAdapter);
        LinearLayoutManager forumLinearLayoutManager = new LinearLayoutManager(this.requireContext());
        forumRecyclerView.setLayoutManager(forumLinearLayoutManager);
        forumLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        forumRecyclerAdapter.setOnItemClickListener(new ForumRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onTitleClick(View view, int position, ForumData forumDataIndex) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("forumData", forumDataIndex);
                Intent intent = new Intent(view.getContext(), ForumActivity.class);
                intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
                startActivity(intent);
            }

            @Override
            public void onTitleLongClick(View view, int position) {
                // TODO: 2022/4/29
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {

    }
}
