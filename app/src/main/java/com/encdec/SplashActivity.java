package com.encdec;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private Runnable runnable;
    private AppCompatActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.activity_splash);
        mContext = SplashActivity.this;
        TextView textview = findViewById(R.id.txt_logo);
        setScaleAnimation(textview);

    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    @Override
    protected void onResume() {
        super.onResume();
        runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, MainActivity.class));

            }
        };
        handler = new Handler();
        int SPLASH_TIME_DURATION = 2000;
        handler.postDelayed(runnable, SPLASH_TIME_DURATION);
    }



    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }
}


