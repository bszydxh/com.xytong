package com.xytong.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xytong.R;
import com.xytong.activity.UserActivity;
import com.xytong.model.vo.CommentVO;
import com.xytong.model.vo.UserVO;
import com.xytong.utils.ImageGetter;
import com.xytong.view.Thump;

import java.util.List;

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder> {
    private final List<CommentVO> localDataSet;
    private OnItemClickListener onItemClickListener;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final ImageView userAvatar;
        private final TextView date;
        private final TextView text;
        private final ViewGroup likesLayout;
        private final ImageView likesImage;
        private final TextView likes;
        private final ViewGroup rootTouchLayout;

        public ViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.card_comment_user_name);
            userAvatar = view.findViewById(R.id.card_comment_user_avatar);
            date = view.findViewById(R.id.card_comment_date);
            text = view.findViewById(R.id.card_comment_text);
            likes = view.findViewById(R.id.card_comment_likes);
            likesImage = view.findViewById(R.id.card_comment_likes_image);
            likesLayout = view.findViewById(R.id.card_comment_likes_layout);
            rootTouchLayout = view.findViewById(R.id.card_comment_touch);
        }

        public TextView getUserName() {
            return userName;
        }

        public ImageView getUserAvatar() {
            return userAvatar;
        }

        public TextView getDate() {
            return date;
        }


        public TextView getLikes() {
            return likes;
        }

        public ViewGroup getLikesLayout() {
            return likesLayout;
        }

        public ImageView getLikesImage() {
            return likesImage;
        }


        public TextView getText() {
            return text;
        }

        public ViewGroup getRootTouchLayout() {
            return rootTouchLayout;
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public CommentRecyclerAdapter(List<CommentVO> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_comment, viewGroup, false);

        return new ViewHolder(view);
    }

    public interface OnItemClickListener {

        void onTitleClick(View view, int position, CommentVO commentData);

        void onTitleLongClick(View view, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Thump<CommentVO> thump = new Thump<>();
        ImageGetter.setAvatarViewBitmap(viewHolder.getUserAvatar(), localDataSet.get(viewHolder.getAdapterPosition()).getUserAvatarUrl());
        viewHolder.getUserName().setText(localDataSet.get(position).getUserName());
        viewHolder.getText().setText(localDataSet.get(position).getText());
        viewHolder.getDate().setText(localDataSet.get(position).getDate());
        thump.setupThump(localDataSet.get(position),
                viewHolder.getLikesImage(), viewHolder.getLikes());
        viewHolder.getRootTouchLayout().setOnClickListener(v -> {
            if (onItemClickListener != null) {
                int pos = viewHolder.getLayoutPosition();
                onItemClickListener.onTitleClick(viewHolder.itemView, pos, localDataSet.get(pos));
            }
        });
        viewHolder.getText().setOnClickListener(v -> {
            if (onItemClickListener != null) {
                int pos = viewHolder.getLayoutPosition();
                onItemClickListener.onTitleClick(viewHolder.itemView, pos, localDataSet.get(pos));
            }
        });
        viewHolder.getLikesLayout().setOnClickListener(v -> {
            int pos = viewHolder.getLayoutPosition();
            thump.changeThump(localDataSet.get(pos),
                    viewHolder.getLikesImage(), viewHolder.getLikes());
        });
        View.OnClickListener imageClickListener = (v->{
            UserVO userVO = new UserVO();
            userVO.setName(localDataSet.get(position).getUserName());
            userVO.setUserAvatarUrl(localDataSet.get(position).getUserAvatarUrl());
            Bundle bundle = new Bundle();
            bundle.putSerializable("userData", userVO);
            Intent intent = new Intent(v.getContext(), UserActivity.class);
            intent.putExtras(bundle); // 将Bundle对象嵌入Intent中
            v.getContext().startActivity(intent);
        });
        viewHolder.getUserAvatar().setOnClickListener(imageClickListener);
        viewHolder.getUserName().setOnClickListener(imageClickListener);
        viewHolder.getDate().setOnClickListener(imageClickListener);
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

