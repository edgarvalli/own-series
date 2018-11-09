package net.ddsn.ev_server.ownseries;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class ShowVideo extends AppCompatActivity {

    VideoView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent i = getIntent();

        video = findViewById(R.id.video);
        Uri uri = Uri.parse(i.getStringExtra("url"));
        video.setVideoURI(uri);

        MediaController mc = new MediaController(this);
        video.setMediaController(mc);
        mc.setAnchorView(video);

        video.start();
    }

}
