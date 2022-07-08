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
import com.xytong.data.DataKeeper;
import com.xytong.data.ForumData;
import com.xytong.databinding.FragmentForumBinding;
import com.xytong.downloader.DataDownloader;
import com.xytong.sqlite.MySQL;

import java.util.ArrayList;
import java.util.List;

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
        MySQL sql = null;
        try {
            sql = new MySQL(this.requireContext());
        } catch (RuntimeException e) {
            Toast.makeText(this.requireContext(), "file error,check the log!", Toast.LENGTH_SHORT).show();
        }
        RefreshLayout forumRefreshLayout = binding.forumRefreshLayout;
        forumRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        forumRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));
        List<ForumData> forumList = new ArrayList<>();
        DataKeeper<ForumData> dataKeeper = new DataKeeper<>();
        for (int i = 0; i < 5; i++) {
            if (sql != null) {
                forumList.add(sql.read_forum_data());
            }
        }

        forumRecyclerAdapter = new ForumRecyclerAdapter(forumList);
        //forumList.addAll(DataDownloader.getForumDataList("newest", 10, 19));
        forumRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                List<ForumData> forumList_add = DataDownloader.getForumDataList("newest", 0, 9);
                if (forumList_add != null) {
                    forumList.clear();
                    forumList.addAll(forumList_add);
                    forumRecyclerAdapter.notifyDataSetChanged();
                }
                refreshlayout.finishRefresh();//传入false表示刷新失败
                Toast.makeText(refreshlayout.getLayout().getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            }
        });
        forumRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                List<ForumData> forumList_add = DataDownloader.getForumDataList("newest", 0, 9);
                if (forumList_add != null) {
                    int size = forumList_add.size();
                    for (int i = 0; i < size; i++) {
                        ForumData forumData = forumList_add.get(i);
                        forumList.add(forumList.size(), forumData);
                    }
                    forumRecyclerAdapter.notifyDataSetChanged();
                    //forumRecyclerAdapter.notifyItemRangeInserted(forumList.size() - size, size);
                }
                refreshlayout.finishLoadMore();//传入false表示加载失败
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
