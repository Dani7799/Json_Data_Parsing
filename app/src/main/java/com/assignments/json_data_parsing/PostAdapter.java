package com.assignments.json_data_parsing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;
    private Context context;
    private OnPostClickListener listener;

    public interface OnPostClickListener {
        void onDeleteClick(int postId);
        void onUpdateClick(int postId);
        void onCommentsClick(int postId);
    }

    public PostAdapter(Context context, List<Post> postList, OnPostClickListener listener) {
        this.context = context;
        this.postList = postList;
        this.listener = listener;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());

        holder.deleteButton.setOnClickListener(v -> listener.onDeleteClick(post.getId()));
        holder.updateButton.setOnClickListener(v -> listener.onUpdateClick(post.getId()));
        holder.commentsButton.setOnClickListener(v -> listener.onCommentsClick(post.getId()));
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView title, body;
        Button deleteButton, updateButton, commentsButton;

        public PostViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.post_title);
            body = itemView.findViewById(R.id.post_body);
            deleteButton = itemView.findViewById(R.id.btn_delete);
            updateButton = itemView.findViewById(R.id.btn_update);
            commentsButton = itemView.findViewById(R.id.btn_comments);
        }
    }
}
