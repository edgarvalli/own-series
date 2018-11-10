package net.ddsn.ev_server.ownseries;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class ShowVideo extends AppCompatActivity {

    VideoView video;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent i = getIntent();

        video = findViewById(R.id.video);
        back = findViewById(R.id.back_arrow_chapters);
        back.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Uri uri = Uri.parse(i.getStringExtra("url"));
        video.setVideoURI(uri);

        MediaController mc;
        mc = new MediaController(this) {

            @Override
            public void hide() {
                back.setVisibility(View.INVISIBLE);
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

        video.start();
    }

}
