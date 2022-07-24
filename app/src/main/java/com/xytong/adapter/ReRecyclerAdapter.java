package com.xytong.adapter;

//TODO

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xytong.R;
import com.xytong.data.ReData;
import com.xytong.image.ImageGetter;

import java.util.List;

public class ReRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ReData> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ReCardViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final ImageView userAvatar;
        private final TextView title;
        private final TextView text;
        private final TextView price;
        private final TextView date;

        public ReCardViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.card_re_user_name);
            userAvatar = view.findViewById(R.id.card_re_user_avatar);
            date = view.findViewById(R.id.card_re_date);
            title = view.findViewById(R.id.card_re_title);
            text = view.findViewById(R.id.card_re_text);
            price = view.findViewById(R.id.card_re_price);
        }

        public TextView getDate() {
            return date;
        }

        public TextView getUserName() {
            return userName;
        }

        public ImageView getUserAvatar() {
            return userAvatar;
        }

        public TextView getText() {
            return text;
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getPrice() {
            return price;
        }
    }

    public static class ImageCardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView banner;

        public ImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banner);
        }

        public ImageView getBanner() {
            return banner;
        }
    }

    public ReRecyclerAdapter(List<ReData> dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 0: {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.card_re, viewGroup, false);
                viewHolder = new ReCardViewHolder(view);
                break;
            }
            case 1: {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.card_image, viewGroup, false);
                viewHolder = new ImageCardViewHolder(view);
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case 0: {
                ReCardViewHolder reCardViewHolder = (ReCardViewHolder) viewHolder;
                ImageGetter.setAvatarViewBitmap(reCardViewHolder.getUserAvatar(), localDataSet.get(reCardViewHolder.getAdapterPosition()).getUserAvatarUrl());//被弃用
                reCardViewHolder.getUserName().setText(localDataSet.get(position).getUserName());
                reCardViewHolder.getTitle().setText(localDataSet.get(position).getTitle());
                reCardViewHolder.getText().setText(localDataSet.get(position).getText());
                reCardViewHolder.getPrice().setText(String.format("¥%s", localDataSet.get(position).getPrice()));
                reCardViewHolder.getDate().setText(localDataSet.get(position).getDate());
                break;
            }
            case 1: {
                break;
            }
        }

    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

