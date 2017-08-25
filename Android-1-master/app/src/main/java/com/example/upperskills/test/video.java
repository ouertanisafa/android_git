package com.example.upperskills.test;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Debug;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.VideoView;

import static android.R.attr.path;
import static android.os.Environment.getExternalStorageDirectory;

/**
 * Created by Ridha on 01/08/2017.
 */

public class video  extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.video);
        Button btn = (Button) findViewById(R.id.button);
        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        String videoPath ="android.resource://"+getPackageName()+ "/"+R.raw.shell;
        Uri uri = Uri.parse(videoPath);
        btn.setEnabled(false);
        videoView.setVideoURI(uri);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer vmp) {
                Intent intent = new Intent(video.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });




        videoView.start();
        skipping(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(video.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }
    public void  skipping(final Button btn){
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                btn.setText("skip " + (millisUntilFinished / 1000));
            }

            public void onFinish() {
                btn.setEnabled(true);
                btn.setText("Skip !");
            }
        }.start();
    }
 }
