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
import com.xytong.adapter.ShRecyclerAdapter;
import com.xytong.data.ShData;
import com.xytong.databinding.FragmentSecondhandBinding;
import com.xytong.sqlite.MySQL;

import java.util.ArrayList;
import java.util.List;

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
        MySQL sql = null;
        try {
            sql = new MySQL(this.requireContext());
        } catch (RuntimeException e) {
            Toast.makeText(this.requireContext(), "file error,check the log!", Toast.LENGTH_SHORT).show();
        }
        RefreshLayout shRefreshLayout = binding.shRefreshLayout;
        shRefreshLayout.setRefreshHeader(new MaterialHeader(this.requireContext()));
        shRefreshLayout.setRefreshFooter(new ClassicsFooter(this.requireContext()));
        List<ShData> shList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            ShData shData = new ShData();
            shList.add(sql.read_secondhand_data());
        }
        ShRecyclerAdapter shRecyclerAdapter = new ShRecyclerAdapter(shList);
        shRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
            }
        });
        shRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        RecyclerView shRecyclerView = binding.shRecyclerView;

        shRecyclerView.setAdapter(shRecyclerAdapter);
        LinearLayoutManager shLinearLayoutManager = new LinearLayoutManager(this.requireContext());
        shRecyclerView.setLayoutManager(shLinearLayoutManager);
        shLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

    }


}
