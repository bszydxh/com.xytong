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
import com.xytong.data.ShData;
import com.xytong.data.UserData;
import com.xytong.image.ImageGetter;

import java.util.List;

public class ShRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ShData> localDataSet;

    private OnItemClickListener onItemClickListener;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView userName;
        private final ImageView userAvatar;
        private final TextView title;
        private final TextView text;
        private final TextView price;
        private final TextView date;
        private final ViewGroup rootTouchLayout;

        public ViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.card_sh_date);
            userName = view.findViewById(R.id.card_sh_user_name);
            userAvatar = view.findViewById(R.id.card_sh_user_avatar);
            title = view.findViewById(R.id.card_sh_title);
            text = view.findViewById(R.id.card_sh_text);
            price = view.findViewById(R.id.card_sh_price);
            rootTouchLayout = view.findViewById(R.id.card_sh_touch);
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

        public ViewGroup getRootTouchLayout() {
            return rootTouchLayout;
        }
    }

    public interface OnItemClickListener {

        void onUserClick(View view, UserData userData);

        void onTitleClick(View view, int position, ShData shData);

        void onTitleLongClick(View view, int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public ShRecyclerAdapter(List<ShData> dataSet) {
        this.localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ShRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_sh, viewGroup, false);

        return new ShRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        ShRecyclerAdapter.ViewHolder shCardViewHolder = (ViewHolder) viewHolder;
        ImageGetter.setAvatarViewBitmap(shCardViewHolder.getUserAvatar(), localDataSet.get(shCardViewHolder.getAdapterPosition()).getUserAvatarUrl());
        shCardViewHolder.getDate().setText(localDataSet.get(position).getDate());
        shCardViewHolder.getUserName().setText(localDataSet.get(position).getUserName());
        shCardViewHolder.getTitle().setText(localDataSet.get(position).getTitle());
        shCardViewHolder.getText().setText(localDataSet.get(position).getText());
        shCardViewHolder.getPrice().setText(String.format("¥%s", localDataSet.get(position).getPrice()));
        if (onItemClickListener != null) {
            shCardViewHolder.getRootTouchLayout().setOnClickListener(v -> {

                int pos = viewHolder.getLayoutPosition();
                onItemClickListener.onTitleClick(viewHolder.itemView, pos, localDataSet.get(pos));
            });
            shCardViewHolder.getUserAvatar().setOnClickListener(v -> userClick(position, v));
            shCardViewHolder.getUserName().setOnClickListener(v -> userClick(position, v));
            shCardViewHolder.getDate().setOnClickListener(v -> userClick(position, v));
        }
    }

    private void userClick(int position, View v) {
        UserData userData = new UserData();
        userData.setName(localDataSet.get(position).getUserName());
        userData.setUserAvatarUrl(localDataSet.get(position).getUserAvatarUrl());
        onItemClickListener.onUserClick(v, userData);
    }

    @Override
    public int getItemCount() {
        if (localDataSet != null) {
            return localDataSet.size();
        } else {
            return 0;
        }
    }
}

