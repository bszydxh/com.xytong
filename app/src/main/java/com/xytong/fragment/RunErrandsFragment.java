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
import com.xytong.adapter.ReRecyclerAdapter;
import com.xytong.data.viewModel.ReDataViewModel;
import com.xytong.databinding.FragmentRunErrandsBinding;

public class RunErrandsFragment extends Fragment {
    FragmentRunErrandsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRunErrandsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ReDataViewModel model = new ViewModelProvider(this).get(ReDataViewModel.class);
        model.getDataList().observe(getViewLifecycleOwner(), dataList -> {
        });
        RefreshLayout reRefreshLayout = binding.reRefreshLayout;
        reRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        reRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));
        ReRecyclerAdapter reRecyclerAdapter = new ReRecyclerAdapter(model.getDataList().getValue());
        reRefreshLayout.setOnRefreshListener(refreshLayout -> {
            if (model.refreshData()) {
                refreshLayout.finishRefresh(true);
                Toast.makeText(refreshLayout.getLayout().getContext(), "刷新成功", Toast.LENGTH_SHORT).show();
                reRecyclerAdapter.notifyDataSetChanged();
            } else {
                refreshLayout.finishRefresh(false);
                Toast.makeText(refreshLayout.getLayout().getContext(), "刷新失败", Toast.LENGTH_SHORT).show();
            }
        });
        reRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (model.loadMoreData()) {
                reRecyclerAdapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore(true);
            } else {
                refreshLayout.finishLoadMore(false);
            }
        });
        RecyclerView reRecyclerView = binding.reRecyclerView;

        reRecyclerView.setAdapter(reRecyclerAdapter);
        LinearLayoutManager reLinearLayoutManager = new LinearLayoutManager(this.requireContext());
        reRecyclerView.setLayoutManager(reLinearLayoutManager);
        reLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

    }
}
