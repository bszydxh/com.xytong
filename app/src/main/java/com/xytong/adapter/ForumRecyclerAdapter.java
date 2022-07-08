package com.xytong.adapter;
//TODO

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.xytong.R;
import com.xytong.data.ForumData;
import com.xytong.image.ImageGetter;

import java.util.List;

public class ForumRecyclerAdapter extends RecyclerView.Adapter<ForumRecyclerAdapter.ViewHolder> {
    private final List<ForumData> localDataSet;
    private OnItemClickListener onItemClickListener;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final ImageView userAvatar;
        private final TextView date;
        private final TextView title;
        private final TextView text;
        private final ViewGroup likesLayout;
        private final ImageView likesImage;
        private final TextView likes;
        private final TextView comments;
        private final TextView forwarding;
        private final ViewGroup forwardingLayout;
        private final ViewGroup rootTouchLayout;

        public ViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.card_forum_user_name);
            userAvatar = view.findViewById(R.id.card_forum_user_avatar);
            date = view.findViewById(R.id.card_forum_date);
            title = view.findViewById(R.id.card_forum_title);
            text = view.findViewById(R.id.card_forum_text);
            likes = view.findViewById(R.id.card_forum_likes);
            likesImage = view.findViewById(R.id.card_forum_likes_image);
            likesLayout = view.findViewById(R.id.card_forum_likes_layout);
            comments = view.findViewById(R.id.card_forum_comments);
            forwarding = view.findViewById(R.id.card_forum_forwarding);
            forwardingLayout = view.findViewById(R.id.card_forum_forwarding_layout);
            rootTouchLayout = view.findViewById(R.id.card_forum_touch);
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

        public TextView getComments() {
            return comments;
        }

        public TextView getForwarding() {
            return forwarding;
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

        public ViewGroup getForwardingLayout() {
            return forwardingLayout;
        }

        public TextView getText() {
            return text;
        }

        public TextView getTitle() {
            return title;
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
    public ForumRecyclerAdapter(List<ForumData> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_forum, viewGroup, false);

        return new ViewHolder(view);
    }

    public interface OnItemClickListener {
        void onTitleClick(View view, int position, ForumData forumData);

        void onTitleLongClick(View view, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        ImageGetter.setAvatarViewBitmap(viewHolder.getUserAvatar(), localDataSet.get(viewHolder.getAdapterPosition()).getUserAvatarUrl());
        viewHolder.getUserName().setText(localDataSet.get(position).getUserName());
        viewHolder.getTitle().setText(localDataSet.get(position).getTitle());
        viewHolder.getText().setText(localDataSet.get(position).getText());
        viewHolder.getDate().setText(localDataSet.get(position).getDate());
        if (localDataSet.get(position).isLiked()) {
            int likes = localDataSet.get(position).getLikes() + 1;
            String likesString = Integer.toString(likes);
            viewHolder.getLikes().setText(likesString);
            Drawable bmpDrawable = ContextCompat.getDrawable(viewHolder.comments.getContext(), R.drawable.ic_baseline_thumb_up_24);
            Drawable.ConstantState state = bmpDrawable.getConstantState();
            Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
            DrawableCompat.setTint(wrap, ContextCompat.getColor(viewHolder.comments.getContext(), R.color.sky_blue));
            viewHolder.getLikesImage().setImageDrawable(wrap);
        } else {
            int likes = localDataSet.get(position).getLikes();
            String likesString = Integer.toString(likes);
            viewHolder.getLikes().setText(likesString);
            Drawable bmpDrawable = ContextCompat.getDrawable(viewHolder.comments.getContext(), R.drawable.ic_baseline_thumb_up_24);
            Drawable.ConstantState state = bmpDrawable.getConstantState();
            Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
            DrawableCompat.setTint(wrap, ContextCompat.getColor(viewHolder.comments.getContext(), R.color.dark_gray));
            viewHolder.getLikesImage().setImageDrawable(wrap);
        }
        String commentsNum = localDataSet.get(position).getComments().toString();
        String forwardingNum = localDataSet.get(position).getForwarding().toString();
        viewHolder.getComments().setText(commentsNum);
        viewHolder.getForwarding().setText(forwardingNum);
        viewHolder.getTitle().setOnClickListener(v -> {
            if (onItemClickListener != null) {
                int pos = viewHolder.getLayoutPosition();
                onItemClickListener.onTitleClick(viewHolder.itemView, pos, localDataSet.get(pos));

            }
        });
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
            if (onItemClickListener != null) {
                int pos = viewHolder.getLayoutPosition();
                if (localDataSet.get(position).isLiked()) {
                    int likes = localDataSet.get(position).getLikes();
                    String likesString = Integer.toString(likes);
                    viewHolder.getLikes().setText(likesString);
                    localDataSet.get(position).setLiked(false);
                    Drawable bmpDrawable = ContextCompat.getDrawable(viewHolder.comments.getContext(), R.drawable.ic_baseline_thumb_up_24);
                    Drawable.ConstantState state = bmpDrawable.getConstantState();
                    Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
                    DrawableCompat.setTint(wrap, ContextCompat.getColor(viewHolder.comments.getContext(), R.color.dark_gray));
                    viewHolder.getLikesImage().setImageDrawable(wrap);
                } else {
                    int likes = localDataSet.get(position).getLikes() + 1;
                    String likesString = Integer.toString(likes);
                    viewHolder.getLikes().setText(likesString);
                    localDataSet.get(position).setLiked(true);
                    Drawable bmpDrawable = ContextCompat.getDrawable(viewHolder.comments.getContext(), R.drawable.ic_baseline_thumb_up_24);
                    Drawable.ConstantState state = bmpDrawable.getConstantState();
                    Drawable wrap = DrawableCompat.wrap(state == null ? bmpDrawable : state.newDrawable());
                    DrawableCompat.setTint(wrap, ContextCompat.getColor(viewHolder.comments.getContext(), R.color.sky_blue));
                    viewHolder.getLikesImage().setImageDrawable(wrap);
                }

            }
        });
        viewHolder.getForwardingLayout().setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            // 比如发送文本形式的数据内容
            // 指定发送的内容
            sendIntent.putExtra(Intent.EXTRA_TEXT, localDataSet.get(position).getTitle() + "\n"
                    + localDataSet.get(position).getText() + "\n用户:" +
                    localDataSet.get(position).getUserName() +
                    "\n---来自校园通客户端");
            // 指定发送内容的类型
            sendIntent.setType("text/plain");
            ContextCompat.startActivity(viewHolder.comments.getContext(), Intent.createChooser(sendIntent, "将内容分享至"), null);
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

