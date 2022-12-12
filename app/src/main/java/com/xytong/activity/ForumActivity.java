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
import androidx.core.content.ContextCompat;
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
import com.xytong.databinding.ActivityForumBinding;
import com.xytong.model.vo.CommentVO;
import com.xytong.model.vo.ForumVO;
import com.xytong.model.vo.UserVO;
import com.xytong.service.DataSender;
import com.xytong.utils.ViewCreateUtils;
import com.xytong.view.Thump;
import com.xytong.viewModel.CommentDataViewModel;

import java.util.List;

import static com.xytong.service.DataDownloader.FORUM_MODULE_NAME;

public class ForumActivity extends AppCompatActivity {
    private ActivityForumBinding binding;
    ForumVO forumVO;
    CommentRecyclerAdapter commentRecyclerAdapter;
    CommentDataViewModel model;
    int position;

    @SuppressLint({"ClickableViewAccessibility", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///////////////////////////////////////////
        Bundle bundle_back = getIntent().getExtras();
        position = bundle_back.getInt("pos");
        forumVO = (ForumVO) bundle_back.getSerializable("forumData");
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        model = new ViewModelProvider(this).get(CommentDataViewModel.class);
        model.init(FORUM_MODULE_NAME, forumVO.getCid());
        LiveData<List<CommentVO>> liveData = model.getDataList();
        ///////////////////////////////////////////
        ViewCreateUtils.setBlackStatusBar(this);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        ImageController.setAvatarViewBitmap(binding.cardForumIndex.cardForumUserAvatar, forumVO.getUserAvatarUrl());
        ///////////////////////////////////////////
        binding.cardForumIndex.cardForumUserName.setText(forumVO.getUserName());
        binding.cardForumIndex.cardForumTitle.setText(forumVO.getTitle());
        binding.cardForumIndex.cardForumText.setText(forumVO.getText());
        binding.cardForumIndex.cardForumDate.setText(forumVO.getDate());
        Thump<ForumVO> thump = new Thump<>();
        thump.setupThump(forumVO, binding.cardForumIndex.cardForumLikesImage, binding.cardForumIndex.cardForumLikes);
        String commentsNum = forumVO.getComments().toString();
        String forwardingNum = forumVO.getForwarding().toString();
        binding.cardForumIndex.cardForumComments.setText(commentsNum);
        binding.cardForumIndex.cardForumForwarding.setText(forwardingNum);
        binding.cardForumIndex.cardForumLikesLayout.setOnClickListener(v ->
                thump.changeThump(forumVO, binding.cardForumIndex.cardForumLikesImage, binding.cardForumIndex.cardForumLikes));
        binding.cardForumIndex.cardForumForwardingLayout.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            // 比如发送文本形式的数据内容
            // 指定发送的内容
            sendIntent.putExtra(Intent.EXTRA_TEXT, forumVO.getTitle() + "\n"
                    + forumVO.getText() + "\n用户:" +
                    forumVO.getUserName() +
                    "\n---来自校园通客户端");
            // 指定发送内容的类型
            sendIntent.setType("text/plain");
            ContextCompat.startActivity(this, Intent.createChooser(sendIntent, "将内容分享至"), null);
        });
        View.OnClickListener imageClickListener = (v -> {
            UserVO userVO = new UserVO();
            userVO.setName(forumVO.getUserName());
            userVO.setUserAvatarUrl(forumVO.getUserAvatarUrl());
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
        RefreshLayout commentRefreshLayout = binding.forumCommentRefreshLayout;
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
        RecyclerView commentRecyclerView = binding.forumCommentRecyclerView;
        commentRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager commentLinearLayoutManager = new LinearLayoutManager(this);
        commentRecyclerView.setLayoutManager(commentLinearLayoutManager);
        commentLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentRecyclerAdapter = new CommentRecyclerAdapter(liveData.getValue());
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
        liveData.observe(this, dataList -> {
            if (commentRecyclerView.getAdapter() != null) {
                Log.i("dataChange", "data num:" + commentRecyclerAdapter.getItemCount());
                RefreshLayout mRefreshLayout = binding.forumCommentRefreshLayout;
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                commentRecyclerAdapter.notifyDataSetChanged();
            }
        });
        binding.cardForumComment.setOnTouchListener((v, event) -> true);
        binding.getRoot().setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                binding.cardForumCommentEdit.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
            return true;
        });
        binding.cardForumCommentSend.setOnClickListener(v -> {
            if (binding.cardForumCommentEdit.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                return;
            }
            DataSender.sendComment(
                    this,
                    FORUM_MODULE_NAME,
                    forumVO.getCid(),
                    binding.cardForumCommentEdit.getText().toString(),
                    new DataSender.StateListener() {
                        @Override
                        public void onSuccess(Context context) {
                            DataSender.getBaseStateListener().onSuccess(context);
                            binding.cardForumCommentEdit.setText("");
                            binding.cardForumCommentEdit.clearFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            binding.forumCommentRefreshLayout.autoRefresh();
                        }

                        @Override
                        public void onStart(Context context) {

                        }

                        @Override
                        public void onFalse(Context context, int error_flag) {
                            DataSender.getBaseStateListener().onFalse(context, error_flag);
                            binding.cardForumCommentEdit.clearFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
            );
        });
        model.refreshData();
    }

    @Override
    public void finish() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("forumData", forumVO);
        bundle.putInt("pos", position);
        Intent intent = new Intent();
        intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
        setResult(Activity.RESULT_OK, intent);
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//退出渐变动画
    }

}
