package com.assignments.json_data_parsing;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import API.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PostAdapter.OnPostClickListener {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private ApiService apiService;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);

        loadPosts();
    }

    private void loadPosts() {
        apiService.getAllPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    postAdapter = new PostAdapter(MainActivity.this, response.body(), MainActivity.this);
                    recyclerView.setAdapter(postAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error loading posts", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteClick(int postId) {
        apiService.deletePost(postId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Post deleted", Toast.LENGTH_SHORT).show();
                    loadPosts();  // Reload posts after deletion
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error deleting post", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onUpdateClick(int postId) {
        Post updatedPost = new Post(postId, "Updated Title", "Updated Body");
        apiService.updatePost(postId, updatedPost).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Post updated", Toast.LENGTH_SHORT).show();
                    loadPosts();  // Reload posts after update
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error updating post", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCommentsClick(int postId) {
        // Handle the comments button click (for now, just show a Toast)
        Toast.makeText(this, "Show comments for Post " + postId, Toast.LENGTH_SHORT).show();
    }
}
