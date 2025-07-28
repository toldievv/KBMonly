package com.example.kbmonly;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.rtoshiro.view.video.FullscreenVideoLayout;

import java.io.IOException;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String name = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String genre = getIntent().getStringExtra("genre");
        String releaseYear = getIntent().getStringExtra("release_year");
        String videoUrl = getIntent().getStringExtra("video_url");

        TextView nameTV = findViewById(R.id.titleTV);
        TextView descriptionTV = findViewById(R.id.descriptionTV);
        TextView genreTV = findViewById(R.id.genreTV);
        TextView releaseYearTV = findViewById(R.id.releaseYearTV);

        nameTV.setText(name);
        descriptionTV.setText(description);
        genreTV.setText(genre);
        releaseYearTV.setText(releaseYear);

//        FullscreenVideoLayout videoLayout;
//        videoLayout = (FullscreenVideoLayout) findViewById(R.id.gameVideoView);

        VideoView videoView = findViewById(R.id.gameVideoView);

        videoView.setVideoURI(Uri.parse(videoUrl));

        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus(0);
        videoView.start();

//        videoLayout.setActivity(this);
//
//        Uri videoUri = Uri.parse(videoUrl);
//        try {
//            videoLayout.setVideoURI(videoUri);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
