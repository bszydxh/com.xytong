package com.xytong.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.xytong.R;
import com.xytong.adapter.CommentRecyclerAdapter;
import com.xytong.service.ImageController;
import com.xytong.databinding.ActivityShBinding;
import com.xytong.model.vo.CommentVO;
import com.xytong.model.vo.ShVO;
import com.xytong.model.vo.UserVO;
import com.xytong.service.DataSender;
import com.xytong.utils.ViewCreateUtils;
import com.xytong.viewModel.CommentDataViewModel;

import java.util.List;

import static com.xytong.service.DataDownloader.SH_MODULE_NAME;

public class ShActivity extends AppCompatActivity {
    private ActivityShBinding binding;
    ShVO shVO;
    CommentRecyclerAdapter commentRecyclerAdapter;
    CommentDataViewModel model;
    int position;
    boolean isFullScreen = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///////////////////////////////////////////
        Bundle bundle_back = getIntent().getExtras();
        position = bundle_back.getInt("pos");
        shVO = (ShVO) bundle_back.getSerializable("shData");
        binding = ActivityShBinding.inflate(getLayoutInflater());
        model = new ViewModelProvider(this).get(CommentDataViewModel.class);
        model.init(SH_MODULE_NAME, shVO.getCid());
        LiveData<List<CommentVO>> liveData = model.getDataList();
        ///////////////////////////////////////////
        ViewCreateUtils.setBlackStatusBar(this);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        ///////////////////////////////////////////

        ImageController.setAvatarViewBitmap(binding.cardShIndex.cardShUserAvatar, shVO.getUserAvatarUrl());
        ImageController.setImageBitmap(binding.cardShIndex.cardShImage, shVO.getImageUrl());
        binding.cardShIndex.cardShUserName.setText(shVO.getUserName());
        binding.cardShIndex.cardShTitle.setText(shVO.getTitle());
        binding.cardShIndex.cardShDate.setText(shVO.getDate());
        binding.cardShIndex.cardShText.setText(shVO.getText());
        binding.cardShIndex.cardShPrice.setText(String.format("¥%s", shVO.getPrice()));
        View.OnClickListener imageClickListener = (v -> {
            UserVO userVO = new UserVO();
            userVO.setName(shVO.getUserName());
            userVO.setUserAvatarUrl(shVO.getUserAvatarUrl());
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
        RefreshLayout commentRefreshLayout = binding.shCommentRefreshLayout;
        commentRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        commentRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        commentRefreshLayout.setOnRefreshListener(refreshLayout -> {
            model.refreshData();
            refreshLayout.finishRefresh(2000);
        });
        commentRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            model.loadMoreData();
            refreshLayout.finishRefresh(2000);
        });
        RecyclerView commentRecyclerView = binding.shCommentRecyclerView;
        commentRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager commentLinearLayoutManager = new LinearLayoutManager(this);
        commentRecyclerView.setLayoutManager(commentLinearLayoutManager);
        commentLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentRecyclerAdapter = new CommentRecyclerAdapter(liveData.getValue());
        commentRecyclerView.setAdapter(commentRecyclerAdapter);
        commentRecyclerAdapter.setOnItemClickListener(new CommentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onTitleClick(View view, int position, CommentVO commentData) {
                binding.cardShCommentEdit.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            @Override
            public void onTitleLongClick(View view, int position) {
                //TODO
            }
        });

        liveData.observe(this, dataList -> {
            if (commentRecyclerView.getAdapter() != null) {
                Log.i("dataChange", "data num:" + commentRecyclerAdapter.getItemCount());
                RefreshLayout mRefreshLayout = binding.shCommentRefreshLayout;
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                commentRecyclerAdapter.notifyDataSetChanged();
            }
        });
        binding.getRoot().setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                binding.cardShCommentEdit.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return true;
        });
        binding.shCommentRefreshLayout.setOnClickListener(v -> {
            binding.cardShCommentEdit.clearFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
        binding.cardShCommentSend.setOnClickListener(v -> {
            if (binding.cardShCommentEdit.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                return;
            }
            DataSender.sendComment(
                    this,
                    SH_MODULE_NAME,
                    shVO.getCid(),
                    binding.cardShCommentEdit.getText().toString(),
                    new DataSender.StateListener() {
                        @Override
                        public void onSuccess(Context context) {
                            DataSender.getBaseStateListener().onSuccess(context);
                            binding.cardShCommentEdit.setText("");
                            binding.cardShCommentEdit.clearFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            binding.shCommentRefreshLayout.autoRefresh();
                        }

                        @Override
                        public void onStart(Context context) {

                        }

                        @Override
                        public void onFalse(Context context, int error_flag) {
                            DataSender.getBaseStateListener().onFalse(context, error_flag);
                            binding.cardShCommentEdit.clearFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
            );
        });
        model.refreshData();
        binding.shCommentTab.setOnClickListener(v -> {
            if (!isFullScreen) {
                ViewCreateUtils.setBackground(
                        binding.shFullscreen,
                        R.drawable.baseline_close_fullscreen_24,
                        R.color.dark_gray
                );
                binding.cardShIndex.getRoot().setVisibility(View.GONE);
            } else {
                ViewCreateUtils.setBackground(
                        binding.shFullscreen,
                        R.drawable.baseline_open_in_full_24,
                        R.color.dark_gray);
                binding.cardShIndex.getRoot().setVisibility(View.VISIBLE);
            }
            isFullScreen = !isFullScreen;
        });

    }

    @Override
    public void finish() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("shData", shVO);
        bundle.putInt("pos", position);
        Intent intent = new Intent();
        intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
        setResult(Activity.RESULT_OK, intent);
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//退出渐变动画
    }

}
