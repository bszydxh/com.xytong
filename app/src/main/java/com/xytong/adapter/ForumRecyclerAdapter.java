package com.xytong.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xytong.R;
import com.xytong.data.ForumData;

import java.util.List;

public class ForumRecyclerAdapter extends RecyclerView.Adapter<ForumRecyclerAdapter.ViewHolder> {

    //private String[] localDataSet;
    //private List<String> localDataSet;
    private List<ForumData> localDataSet;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView userName;
        private final ImageView userAvatar;
        private final TextView title;
        private final TextView text;
        private final TextView likes;
        private final TextView comments;
        private final TextView forwarding;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            userName = view.findViewById(R.id.card_forum_user_name);
            userAvatar = view.findViewById(R.id.card_forum_user_avatar);
            title = view.findViewById(R.id.card_forum_title);
            text = view.findViewById(R.id.card_forum_text);
            likes = view.findViewById(R.id.card_forum_likes);
            comments = view.findViewById(R.id.card_forum_comments);
            forwarding = view.findViewById(R.id.card_forum_forwarding);
        }

        public TextView getUserName() {
            return userName;
        }

        public ImageView getUserAvatar() {
            return userAvatar;
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

        public TextView getText() {
            return text;
        }

        public TextView getTitle() {
            return title;
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

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL url;
//                    url = new URL(localDataSet.get(position).getUserAvatarUrl());
//                    Log.v("onBindViewHolder",url.toString());
//                    viewHolder.getUserAvatar().setImageBitmap(BitmapFactory.decodeStream(url.openStream()));
//                } catch (Exception e) {
//                    Log.e("onBindViewHolder", "Could not load user avatar", e);
//                }
//            }
//        });

        viewHolder.getUserName().setText(localDataSet.get(position).getUserName());
        viewHolder.getTitle().setText(localDataSet.get(position).getTitle());
        viewHolder.getText().setText(localDataSet.get(position).getText());
        String likesNum = localDataSet.get(position).getLikes().toString();
        String commentsNum = localDataSet.get(position).getComments().toString();
        String forwardingNum = localDataSet.get(position).getForwarding().toString();
        viewHolder.getLikes().setText(likesNum);
        viewHolder.getComments().setText(commentsNum);
        viewHolder.getForwarding().setText(forwardingNum);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

