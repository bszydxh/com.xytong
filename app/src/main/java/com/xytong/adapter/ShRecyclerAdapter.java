package com.xytong.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xytong.R;
import com.xytong.data.ShData;
import com.xytong.image.ImageDownloader;

import java.util.List;

public class ShRecyclerAdapter extends RecyclerView.Adapter<ShRecyclerAdapter.ViewHolder> {


    //private String[] localDataSet;
    //private List<String> localDataSet;
    private List<ShData> localDataSet;

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
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            userName = view.findViewById(R.id.card_sh_user_name);
            userAvatar = view.findViewById(R.id.card_sh_user_avatar);
            title = view.findViewById(R.id.card_sh_title);
            text = view.findViewById(R.id.card_sh_text);
            price = view.findViewById(R.id.card_sh_price);
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

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     *                by RecyclerView.
     */
    public ShRecyclerAdapter(List<ShData> dataSet) {
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ShRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_sh, viewGroup, false);

        return new ShRecyclerAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ShRecyclerAdapter.ViewHolder viewHolder, final int position) {
        ImageDownloader.setBitmap(viewHolder.getUserAvatar(), localDataSet.get(viewHolder.getAdapterPosition()).getUserAvatarUrl());
        viewHolder.getUserName().setText(localDataSet.get(position).getUserName());
        viewHolder.getTitle().setText(localDataSet.get(position).getTitle());
        viewHolder.getText().setText(localDataSet.get(position).getText());
        viewHolder.getPrice().setText(String.format("¥%s", localDataSet.get(position).getPrice()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

