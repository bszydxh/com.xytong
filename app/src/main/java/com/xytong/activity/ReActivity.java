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
import com.xytong.databinding.ActivityReBinding;
import com.xytong.model.vo.CommentVO;
import com.xytong.model.vo.ReVO;
import com.xytong.model.vo.UserVO;
import com.xytong.service.DataSender;
import com.xytong.utils.ViewCreateUtils;
import com.xytong.viewModel.CommentDataViewModel;

import java.util.List;

import static com.xytong.service.DataDownloader.RE_MODULE_NAME;

@SuppressLint("ClickableViewAccessibility")
public class ReActivity extends AppCompatActivity {
    private ActivityReBinding binding;
    ReVO reVO;
    CommentRecyclerAdapter commentRecyclerAdapter;
    CommentDataViewModel model;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///////////////////////////////////////////
        Bundle bundle_back = getIntent().getExtras();
        binding = ActivityReBinding.inflate(getLayoutInflater());
        position = bundle_back.getInt("pos");
        reVO = (ReVO) bundle_back.getSerializable("reData");
        model = new ViewModelProvider(this).get(CommentDataViewModel.class);
        model.init(RE_MODULE_NAME, reVO.getCid());
        LiveData<List<CommentVO>> liveData = model.getDataList();
        ///////////////////////////////////////////
        ViewCreateUtils.setBlackStatusBar(this);
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//进入渐变动画
        ImageController.setAvatarViewBitmap(binding.cardReIndex.cardReUserAvatar, reVO.getUserAvatarUrl());
        binding.cardReIndex.cardReUserName.setText(reVO.getUserName());
        binding.cardReIndex.cardReTitle.setText(reVO.getTitle());
        binding.cardReIndex.cardReText.setText(reVO.getText());
        binding.cardReIndex.cardReDate.setText(reVO.getDate());
        binding.cardReIndex.cardRePrice.setText(String.format("¥%s", reVO.getPrice()));
        View.OnClickListener imageClickListener = (v -> {
            UserVO userVO = new UserVO();
            userVO.setName(reVO.getUserName());
            userVO.setUserAvatarUrl(reVO.getUserAvatarUrl());
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
        RefreshLayout commentRefreshLayout = binding.reCommentRefreshLayout;
        commentRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        commentRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        commentRefreshLayout.setOnRefreshListener(refreshLayout -> {
            model.refreshData();
            refreshLayout.finishRefresh(2000);
        });
        commentRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            model.loadMoreData();
            refreshLayout.finishLoadMore(2000);
        });
        RecyclerView commentRecyclerView = binding.reCommentRecyclerView;
        commentRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager commentLinearLayoutManager = new LinearLayoutManager(this);
        commentRecyclerView.setLayoutManager(commentLinearLayoutManager);
        commentLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentRecyclerAdapter = new CommentRecyclerAdapter(liveData.getValue());
        commentRecyclerView.setAdapter(commentRecyclerAdapter);
        commentRecyclerAdapter.setOnItemClickListener(new CommentRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onTitleClick(View view, int position, CommentVO commentData) {
                binding.cardReCommentEdit.clearFocus();
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
                RefreshLayout mRefreshLayout = binding.reCommentRefreshLayout;
                mRefreshLayout.finishRefresh();
                mRefreshLayout.finishLoadMore();
                commentRecyclerAdapter.notifyDataSetChanged();
            }
        });
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
        binding.cardReCommentSend.setOnClickListener(v -> {
            if (binding.cardReCommentEdit.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                return;
            }
            DataSender.sendComment(
                    this,
                    RE_MODULE_NAME,
                    reVO.getCid(),
                    binding.cardReCommentEdit.getText().toString(),
                    new DataSender.StateListener() {
                        @Override
                        public void onSuccess(Context context) {
                            DataSender.getBaseStateListener().onSuccess(context);
                            binding.cardReCommentEdit.setText("");
                            binding.cardReCommentEdit.clearFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                            binding.reCommentRefreshLayout.autoRefresh();
                        }

                        @Override
                        public void onStart(Context context) {

                        }

                        @Override
                        public void onFalse(Context context, int error_flag) {
                            DataSender.getBaseStateListener().onFalse(context, error_flag);
                            binding.cardReCommentEdit.clearFocus();
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
        bundle.putSerializable("reData", reVO);
        bundle.putInt("pos", position);
        Intent intent = new Intent();
        intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
        setResult(Activity.RESULT_OK, intent);
        super.finish();
        overridePendingTransition(R.anim.nav_default_enter_anim, R.anim.nav_default_exit_anim);//退出渐变动画
    }

}
