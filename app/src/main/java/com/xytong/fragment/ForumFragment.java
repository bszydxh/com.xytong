package com.xytong.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.xytong.ForumActivity;
import com.xytong.adapter.ForumRecyclerAdapter;
import com.xytong.data.ForumData;
import com.xytong.databinding.FragmentForumBinding;
import com.xytong.sqlite.MySQL;

import java.util.ArrayList;
import java.util.List;

public class ForumFragment extends Fragment {
    FragmentForumBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentForumBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        MySQL sql = null;
        try {
            sql = new MySQL(this.requireContext());
        } catch (RuntimeException e) {
            Toast.makeText(this.requireContext(), "file error,check the log!", Toast.LENGTH_SHORT).show();
        }
        super.onActivityCreated(savedInstanceState);
        RefreshLayout forumRefreshLayout = binding.forumRefreshLayout;
        forumRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        forumRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));
        //List<ForumData> forumList = new ArrayList<>();
        List<ForumData> forumList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (sql != null) {
                forumList.add(sql.read_forum_data());
            }
        }
        ForumRecyclerAdapter forumRecyclerAdapter = new ForumRecyclerAdapter(forumList);
        //forumList.addAll(DataDownloader.getForumDataList("newest", 10, 19));
        forumRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
//                DataDownloader.getForumDataList("newest", forumList.size() - 1, forumList.size() + 9);
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
            }
        });
        forumRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                //forumList.addAll(DataDownloader.getForumDataList("newest", 10, 19));
                refreshlayout.finishLoadMore(10/*,false*/);//传入false表示加载失败
                //Toast.makeText(MainActivity.this, "加载成功", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView forumRecyclerView = binding.forumRecyclerView;
        forumRecyclerView.setAdapter(forumRecyclerAdapter);
        LinearLayoutManager forumLinearLayoutManager = new LinearLayoutManager(this.requireContext());
        forumRecyclerView.setLayoutManager(forumLinearLayoutManager);
        forumLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        forumRecyclerAdapter.setOnItemClickListener(new ForumRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onTitleClick(View view, int position) {
                Intent intent = new Intent(view.getContext(), ForumActivity.class);
                startActivity(intent);
            }

            @Override
            public void onTitleLongClick(View view, int position) {
                // TODO: 2022/4/29
            }
        });
    }
}
