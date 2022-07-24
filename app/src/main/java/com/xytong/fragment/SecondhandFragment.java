package com.xytong.fragment;

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
import com.xytong.adapter.ShRecyclerAdapter;
import com.xytong.data.viewModel.ShDataViewModel;
import com.xytong.databinding.FragmentSecondhandBinding;

public class SecondhandFragment extends Fragment {
    FragmentSecondhandBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSecondhandBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ShDataViewModel model = new ViewModelProvider(this).get(ShDataViewModel.class);
        model.getDataList().observe(getViewLifecycleOwner(), dataList -> {
        });
        RefreshLayout shRefreshLayout = binding.shRefreshLayout;
        shRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        shRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));
        ShRecyclerAdapter shRecyclerAdapter = new ShRecyclerAdapter(model.getDataList().getValue());
        shRefreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(2000);
            if (model.refreshData()) {
                refreshLayout.finishRefresh(true);
                Toast.makeText(refreshLayout.getLayout().getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                shRecyclerAdapter.notifyDataSetChanged();
            }
            else{
                refreshLayout.finishRefresh(false);
                Toast.makeText(refreshLayout.getLayout().getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
            }
        });
        shRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.finishLoadMore(2000);
            if (model.loadMoreData()) {
                shRecyclerAdapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore(true);
            }
            else{
                refreshLayout.finishLoadMore(false);
            }
        });
        RecyclerView shRecyclerView = binding.shRecyclerView;

        shRecyclerView.setAdapter(shRecyclerAdapter);
        LinearLayoutManager shLinearLayoutManager = new LinearLayoutManager(this.requireContext());
        shRecyclerView.setLayoutManager(shLinearLayoutManager);
        shLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

    }


}
