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
import com.xytong.databinding.ActivityReBinding;
import com.xytong.model.vo.CommentVO;
import com.xytong.model.vo.ReVO;
import com.xytong.model.vo.UserVO;
import com.xytong.utils.ImageUtils;
import com.xytong.utils.ViewCreateUtils;
import com.xytong.viewModel.CommentDataViewModel;

import java.util.List;

@SuppressLint("ClickableViewAccessibility")
public class ReActivity extends AppCompatActivity {
    private ActivityReBinding binding;
    ReVO reData;
    CommentRecyclerAdapter commentRecyclerAdapter;
    CommentDataViewModel model;
    int position;
    CircularProgressIndicator circularProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewCreateUtils.setBlackStatusBar(this);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        binding = ActivityReBinding.inflate(getLayoutInflater());
        Bundle bundle_back = getIntent().getExtras();
        position = bundle_back.getInt("pos");
        reData = (ReVO) bundle_back.getSerializable("reData");
        ImageUtils.setAvatarViewBitmap(binding.cardReIndex.cardReUserAvatar, reData.getUserAvatarUrl());
        binding.cardReIndex.cardReUserName.setText(reData.getUserName());
        binding.cardReIndex.cardReTitle.setText(reData.getTitle());
        binding.cardReIndex.cardReText.setText(reData.getText());
        binding.cardReIndex.cardReDate.setText(reData.getDate());
        binding.cardReIndex.cardRePrice.setText(String.format("¥%s", reData.getPrice()));
        View.OnClickListener imageClickListener = (v->{
            UserVO userVO = new UserVO();
            userVO.setName(reData.getUserName());
            userVO.setUserAvatarUrl(reData.getUserAvatarUrl());
            Bundle bundle = new Bundle();
            bundle.putSerializable("userData", userVO);
            Intent intent = new Intent(v.getContext(), UserActivity.class);
            intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
            v.getContext().startActivity(intent);
        });
        binding.cardReIndex.cardReUserAvatar.setOnClickListener(imageClickListener);
        binding.cardReIndex.cardReUserName.setOnClickListener(imageClickListener);
        binding.cardReIndex.cardReDate.setOnClickListener(imageClickListener);
        setContentView(binding.getRoot());//binding中cardReRoot()方法是对binding根视图的引用,也相当于创建视图
        binding.reBack.setOnClickListener(v -> finish());
        circularProgressIndicator = binding.reCommentProgress;
        RefreshLayout commentRefreshLayout = binding.reCommentRefreshLayout;
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
        RecyclerView commentRecyclerView = binding.reCommentRecyclerView;
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
                            binding.cardReCommentEdit.clearFocus();
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
                binding.cardReCommentEdit.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return true;
        });
        binding.cardReCommentSend.setVisibility(View.INVISIBLE);
        binding.cardReComment.setOnTouchListener((v, event) -> true);
        binding.cardReCommentEdit.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.cardReCommentButton.setVisibility(View.INVISIBLE);
                binding.cardReCommentSend.setVisibility(View.VISIBLE);
            } else {
                binding.cardReCommentButton.setVisibility(View.VISIBLE);
                binding.cardReCommentSend.setVisibility(View.INVISIBLE);
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
