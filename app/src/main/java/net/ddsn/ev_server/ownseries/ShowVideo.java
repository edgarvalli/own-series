package net.ddsn.ev_server.ownseries;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONObject;

public class ShowVideo extends AppCompatActivity {

    VideoView video;
    ImageView back;
    ProgressBar loading;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Intent i;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        i = getIntent();

        sp = getApplication().getSharedPreferences("videos", Context.MODE_PRIVATE);
        editor = sp.edit();

        video = findViewById(R.id.video);
        back = findViewById(R.id.back_arrow_chapters);
        loading = findViewById(R.id.loading);
        back.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPositionVideo();
                finish();
            }
        });

        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE
            // Set the content to appear under the system bars so that the
            // content doesn't resize when the system bars hide and show.
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            // Hide the nav bar and status bar
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
        );

        Uri uri = Uri.parse(i.getStringExtra("url"));
        video.setVideoURI(uri);

        MediaController mc;
        mc = new MediaController(this) {

            @Override
            public void hide() {
                back.setVisibility(View.INVISIBLE);
                getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                );
                super.hide();
            }

            @Override
            public void show() {
                back.setVisibility(View.VISIBLE);
                super.show();
            }
        };
        video.setMediaController(mc);
        mc.setAnchorView(video);

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                int position = i.getIntExtra(i.getStringExtra("video_id"), 0);
                video.seekTo(position);
                mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        if(percent > 0) {
                            loading.setVisibility(View.INVISIBLE);
                        } else {
                            loading.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        video.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setPositionVideo();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setPositionVideo();
        video.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        video.start();
    }

    void setPositionVideo() {
        editor.putInt(i.getStringExtra("video_id"), video.getCurrentPosition());
        editor.commit();
    }

    int getPositionVideo() {
        int position = sp.getInt(i.getStringExtra("video_id"), 0);
        return position;
    }
}
