package com.xytong.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import java.util.List;

public class ForumFragment extends Fragment {
    FragmentForumBinding binding;
    ForumRecyclerAdapter forumRecyclerAdapter;
    ForumDataViewModel model;
    // GetContent creates an ActivityResultLauncher<String> to allow you to pass
// in the mime type you'd like to allow the user to select
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    ForumData data = (ForumData) intent.getExtras().getSerializable("forumData");
                    List<ForumData> dataList = model.getDataList().getValue();
                    int pos = intent.getExtras().getInt("pos");
                    dataList.remove(pos);
                    dataList.add(pos, data);
                    model.setDataList(dataList);
                }
            });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentForumBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model = new ViewModelProvider(this).get(ForumDataViewModel.class);
        model.getDataList().observe(getViewLifecycleOwner(), dataList -> {
            forumRecyclerAdapter.notifyDataSetChanged();
        });

        RefreshLayout forumRefreshLayout = binding.forumRefreshLayout;
        forumRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        forumRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));
        forumRecyclerAdapter = new ForumRecyclerAdapter(model.getDataList().getValue());
        forumRefreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(2000);
            if (model.refreshData()) {
                refreshLayout.finishRefresh(true);
                Toast.makeText(refreshLayout.getLayout().getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
            } else {
                refreshLayout.finishRefresh(false);
                Toast.makeText(refreshLayout.getLayout().getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
            }
        });
        forumRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.finishLoadMore(2000);
            if (model.loadMoreData()) {
                refreshLayout.finishLoadMore(true);
            } else {
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
                bundle.putInt("pos", position);
                Intent intent = new Intent(view.getContext(), ForumActivity.class);
                intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
                //startActivity(intent);
                mStartForResult.launch(intent);
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
