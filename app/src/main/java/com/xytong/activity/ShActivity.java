package com.xytong.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.xytong.R;
import com.xytong.adapter.CommentRecyclerAdapter;
import com.xytong.databinding.ActivityShBinding;
import com.xytong.model.vo.CommentVO;
import com.xytong.model.vo.ShVO;
import com.xytong.model.vo.UserVO;
import com.xytong.utils.ImageUtils;
import com.xytong.utils.ViewCreateUtils;
import com.xytong.viewModel.CommentDataViewModel;

import java.util.List;

public class ShActivity extends AppCompatActivity {
    private ActivityShBinding binding;
    ShVO shData;
    CommentRecyclerAdapter commentRecyclerAdapter;
    CommentDataViewModel model;
    int position;
    CircularProgressIndicator circularProgressIndicator;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ViewCreateUtils.setBlackStatusBar(this);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        super.onCreate(savedInstanceState);
        binding = ActivityShBinding.inflate(getLayoutInflater());
        Bundle bundle_back = getIntent().getExtras();
        position = bundle_back.getInt("pos");
        shData = (ShVO) bundle_back.getSerializable("shData");
        ImageUtils.setAvatarViewBitmap(binding.cardShIndex.cardShUserAvatar, shData.getUserAvatarUrl());
        binding.cardShIndex.cardShUserName.setText(shData.getUserName());
        binding.cardShIndex.cardShTitle.setText(shData.getTitle());
        binding.cardShIndex.cardShDate.setText(shData.getDate());
        binding.cardShIndex.cardShText.setText(shData.getText());
        binding.cardShIndex.cardShPrice.setText(String.format("¥%s", shData.getPrice()));
        View.OnClickListener imageClickListener = (v->{
            UserVO userVO = new UserVO();
            userVO.setName(shData.getUserName());
            userVO.setUserAvatarUrl(shData.getUserAvatarUrl());
            Bundle bundle = new Bundle();
            bundle.putSerializable("userData", userVO);
            Intent intent = new Intent(v.getContext(), UserActivity.class);
            intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
            v.getContext().startActivity(intent);
        });
        binding.cardShIndex.cardShUserAvatar.setOnClickListener(imageClickListener);
        binding.cardShIndex.cardShUserName.setOnClickListener(imageClickListener);
        binding.cardShIndex.cardShDate.setOnClickListener(imageClickListener);
        setContentView(binding.getRoot());//binding中cardShRoot()方法是对binding根视图的引用,也相当于创建视图
        binding.shBack.setOnClickListener(v -> finish());
        circularProgressIndicator = binding.shCommentProgress;
        RefreshLayout commentRefreshLayout = binding.shCommentRefreshLayout;
        commentRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        commentRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        commentRefreshLayout.setOnRefreshListener(refreshLayout -> {
            refreshLayout.finishRefresh(2000);
            new Thread(() -> refreshLayout.finishRefresh(model.refreshData())).start();
        });
        commentRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            refreshLayout.finishLoadMore(2000);
            new Thread(() -> refreshLayout.finishLoadMore(model.loadMoreData())).start();
        });
        RecyclerView commentRecyclerView = binding.shCommentRecyclerView;
        commentRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager commentLinearLayoutManager = new LinearLayoutManager(this);
        commentRecyclerView.setLayoutManager(commentLinearLayoutManager);
        commentLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        new Thread(() -> {
            model = new ViewModelProvider(this).get(CommentDataViewModel.class);
            LiveData<List<CommentVO>> liveData = model.getDataList();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> liveData.observe(this, dataList -> {
                if (commentRecyclerView.getAdapter() == null) {
                    Log.i("setAdapter", "ok");
                    circularProgressIndicator.setVisibility(View.GONE);
                    commentRecyclerAdapter = new CommentRecyclerAdapter(dataList);
                    commentRecyclerView.setAdapter(commentRecyclerAdapter);
                    commentRecyclerAdapter.setOnItemClickListener(new CommentRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onTitleClick(View view, int position, CommentVO commentData) {
                            binding.cardShCommentEdit.clearFocus();
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
                        }

                        @Override
                        public void onTitleLongClick(View view, int position) {
                            //TODO
                        }
                    });
                } else {
                    Log.i("dataChange", "data num:" + commentRecyclerAdapter.getItemCount());
                    commentRecyclerAdapter.notifyDataSetChanged();
                }
            }));

        }).start();
        binding.getRoot().setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                binding.cardShCommentEdit.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return true;
        });
        binding.cardShComment.setOnTouchListener((v, event) -> true);
        binding.cardShCommentSend.setVisibility(View.INVISIBLE);
        binding.cardShCommentEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.cardShCommentButton.setVisibility(View.INVISIBLE);
                binding.cardShCommentSend.setVisibility(View.VISIBLE);
            } else {
                binding.cardShCommentButton.setVisibility(View.VISIBLE);
                binding.cardShCommentSend.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void finish() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("shData", shData);
        bundle.putInt("pos", position);
        Intent intent = new Intent();
        intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
        setResult(Activity.RESULT_OK, intent);
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//退出渐变动画
    }

}
