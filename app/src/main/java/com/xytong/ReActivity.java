package com.xytong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.xytong.adapter.CommentRecyclerAdapter;
import com.xytong.data.CommentData;
import com.xytong.data.ReData;
import com.xytong.data.viewModel.CommentDataViewModel;
import com.xytong.databinding.ActivityReBinding;
import com.xytong.image.ImageGetter;

import java.util.List;

public class ReActivity extends AppCompatActivity {
    private ActivityReBinding binding;
    ReData reData;
    CommentRecyclerAdapter commentRecyclerAdapter;
    CommentDataViewModel model;
    int position;
    CircularProgressIndicator circularProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        super.onCreate(savedInstanceState);
        binding = ActivityReBinding.inflate(getLayoutInflater());
        Bundle bundle_back = getIntent().getExtras();
        position = bundle_back.getInt("pos");
        reData = (ReData) bundle_back.getSerializable("reData");
        ImageGetter.setAvatarViewBitmap(binding.cardReIndex.cardReUserAvatar, reData.getUserAvatarUrl());
        binding.cardReIndex.cardReUserName.setText(reData.getUserName());
        binding.cardReIndex.cardReTitle.setText(reData.getTitle());
        binding.cardReIndex.cardReText.setText(reData.getText());
        binding.cardReIndex.cardRePrice.setText(String.format("¥%s", reData.getPrice()));
        setContentView(binding.getRoot());//binding中cardReRoot()方法是对binding根视图的引用,也相当于创建视图
        binding.reBack.setOnClickListener(v -> finish());
        circularProgressIndicator = binding.reCommentProgress;
        RefreshLayout commentRefreshLayout = binding.reCommentRefreshLayout;
        commentRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        commentRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        commentRefreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(2000);
            new Thread(() -> {
                refreshLayout.finishRefresh(model.refreshData());
            }).start();
        });
        commentRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.finishLoadMore(2000);
            new Thread(() -> {
                refreshLayout.finishLoadMore(model.loadMoreData());
            }).start();
        });
        RecyclerView commentRecyclerView = binding.reCommentRecyclerView;
        commentRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager commentLinearLayoutManager = new LinearLayoutManager(this);
        commentRecyclerView.setLayoutManager(commentLinearLayoutManager);
        commentLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        new Thread(() -> {
            model = new ViewModelProvider(this).get(CommentDataViewModel.class);
            LiveData<List<CommentData>> liveData = model.getDataList();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                liveData.observe(this, dataList -> {
                    if (commentRecyclerView.getAdapter() == null) {
                        Log.e("setAdapter", "ok");
                        circularProgressIndicator.setVisibility(View.GONE);
                        commentRecyclerAdapter = new CommentRecyclerAdapter(dataList);
                        commentRecyclerView.setAdapter(commentRecyclerAdapter);
                    } else {
                        Log.e("dataChange", "data num:" + commentRecyclerAdapter.getItemCount());
                        commentRecyclerAdapter.notifyDataSetChanged();
                    }
                });
            });

        }).start();
        binding.cardReCommentEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.cardReCommentButton.setVisibility(View.INVISIBLE);
            } else {
                binding.cardReCommentButton.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void finish() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("reData", reData);
        bundle.putInt("pos", position);
        Intent intent = new Intent();
        intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
        setResult(Activity.RESULT_OK, intent);
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//退出渐变动画
    }

}
