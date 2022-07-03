package com.xytong.fragment;

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
import com.xytong.adapter.ReRecyclerAdapter;
import com.xytong.data.ReData;
import com.xytong.databinding.FragmentRunErrandsBinding;
import com.xytong.sqlite.MySQL;

import java.util.ArrayList;
import java.util.List;

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
        MySQL sql = null;
        try {
            sql = new MySQL(this.requireContext());
        } catch (RuntimeException e) {
            Toast.makeText(this.requireContext(), "file error,check the log!", Toast.LENGTH_SHORT).show();
        }
        RefreshLayout reRefreshLayout = binding.reRefreshLayout;
        reRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        reRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));
        List<ReData> reList = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            ReData reData = new ReData();
            reList.add(sql.read_run_errands_data());
        }

        ReRecyclerAdapter reRecyclerAdapter = new ReRecyclerAdapter(reList);
        reRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
            }
        });
        reRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        RecyclerView reRecyclerView = binding.reRecyclerView;

        reRecyclerView.setAdapter(reRecyclerAdapter);
        LinearLayoutManager reLinearLayoutManager = new LinearLayoutManager(this.requireContext());
        reRecyclerView.setLayoutManager(reLinearLayoutManager);
        reLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

    }
}
