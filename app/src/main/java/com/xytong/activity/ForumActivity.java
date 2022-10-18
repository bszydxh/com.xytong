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
import androidx.core.content.ContextCompat;
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
import com.xytong.databinding.ActivityForumBinding;
import com.xytong.model.vo.CommentVO;
import com.xytong.model.vo.ForumVO;
import com.xytong.model.vo.UserVO;
import com.xytong.utils.ImageUtils;
import com.xytong.utils.ViewCreateUtils;
import com.xytong.view.Thump;
import com.xytong.viewModel.CommentDataViewModel;

import java.util.List;

public class ForumActivity extends AppCompatActivity {
    private ActivityForumBinding binding;
    ForumVO forumData;
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
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        Bundle bundle_back = getIntent().getExtras();
        position = bundle_back.getInt("pos");
        forumData = (ForumVO) bundle_back.getSerializable("forumData");
        ImageUtils.setAvatarViewBitmap(binding.cardForumIndex.cardForumUserAvatar, forumData.getUserAvatarUrl());
        binding.cardForumIndex.cardForumUserName.setText(forumData.getUserName());
        binding.cardForumIndex.cardForumTitle.setText(forumData.getTitle());
        binding.cardForumIndex.cardForumText.setText(forumData.getText());
        binding.cardForumIndex.cardForumDate.setText(forumData.getDate());
        Thump<ForumVO> thump = new Thump<>();
        thump.setupThump(forumData, binding.cardForumIndex.cardForumLikesImage, binding.cardForumIndex.cardForumLikes);
        String commentsNum = forumData.getComments().toString();
        String forwardingNum = forumData.getForwarding().toString();
        binding.cardForumIndex.cardForumComments.setText(commentsNum);
        binding.cardForumIndex.cardForumForwarding.setText(forwardingNum);
        binding.cardForumIndex.cardForumLikesLayout.setOnClickListener(v -> thump.changeThump(forumData, binding.cardForumIndex.cardForumLikesImage, binding.cardForumIndex.cardForumLikes));
        binding.cardForumIndex.cardForumForwardingLayout.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            // 比如发送文本形式的数据内容
            // 指定发送的内容
            sendIntent.putExtra(Intent.EXTRA_TEXT, forumData.getTitle() + "\n"
                    + forumData.getText() + "\n用户:" +
                    forumData.getUserName() +
                    "\n---来自校园通客户端");
            // 指定发送内容的类型
            sendIntent.setType("text/plain");
            ContextCompat.startActivity(this, Intent.createChooser(sendIntent, "将内容分享至"), null);
        });
        View.OnClickListener imageClickListener = (v -> {
            UserVO userVO = new UserVO();
            userVO.setName(forumData.getUserName());
            userVO.setUserAvatarUrl(forumData.getUserAvatarUrl());
            Bundle bundle = new Bundle();
            bundle.putSerializable("userData", userVO);
            Intent intent = new Intent(v.getContext(), UserActivity.class);
            intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
            v.getContext().startActivity(intent);
        });
        binding.cardForumIndex.cardForumUserAvatar.setOnClickListener(imageClickListener);
        binding.cardForumIndex.cardForumUserName.setOnClickListener(imageClickListener);
        binding.cardForumIndex.cardForumDate.setOnClickListener(imageClickListener);
        setContentView(binding.getRoot());//binding中cardForumRoot()方法是对binding根视图的引用,也相当于创建视图
        binding.forumBack.setOnClickListener(v -> finish());
        circularProgressIndicator = binding.forumCommentProgress;
        RefreshLayout commentRefreshLayout = binding.forumCommentRefreshLayout;
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
        RecyclerView commentRecyclerView = binding.forumCommentRecyclerView;
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
                            binding.cardForumCommentEdit.clearFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        binding.cardForumComment.setOnTouchListener((v, event) -> true);
        binding.getRoot().setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                binding.cardForumCommentEdit.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return true;
        });
    }

    @Override
    public void finish() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("forumData", forumData);
        bundle.putInt("pos", position);
        Intent intent = new Intent();
        intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
        setResult(Activity.RESULT_OK, intent);
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//退出渐变动画
    }

}