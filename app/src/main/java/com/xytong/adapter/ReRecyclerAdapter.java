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

public class ReRecyclerAdapter extends RecyclerView.Adapter<ReRecyclerAdapter.ViewHolder> {

    private List<ReData> localDataSet;

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
        public ViewHolder(View view) {
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

    public ReRecyclerAdapter(List<ReData> dataSet) {
        localDataSet = dataSet;
    }

    @NonNull
    @Override
    public ReRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_re, viewGroup, false);

        return new ReRecyclerAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ReRecyclerAdapter.ViewHolder viewHolder, final int position) {
        ImageGetter.setAvatarViewBitmap(viewHolder.getUserAvatar(), localDataSet.get(viewHolder.getAdapterPosition()).getUserAvatarUrl());//被弃用
        viewHolder.getUserName().setText(localDataSet.get(position).getUserName());
        viewHolder.getTitle().setText(localDataSet.get(position).getTitle());
        viewHolder.getText().setText(localDataSet.get(position).getText());
        viewHolder.getPrice().setText(String.format("¥%s", localDataSet.get(position).getPrice()));
        viewHolder.getDate().setText(localDataSet.get(position).getDate());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

