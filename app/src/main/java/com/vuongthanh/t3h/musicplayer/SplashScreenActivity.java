package com.vuongthanh.t3h.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class SplashScreenActivity extends Activity {
   // private ProgressBar progressBar;
    private static int SPLASH_TIME_OUT = 3000;
    private LinearLayout lnMain;
    private Animation alpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);
        lnMain = findViewById(R.id.lnMain);
        alpha = AnimationUtils.loadAnimation(this,R.anim.alpha_anim);
        lnMain.startAnimation(alpha);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
      //  progressBar = findViewById(R.id.pb_SplashScreen);
      //  progressBar.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              //  progressBar.setVisibility(View.GONE);
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);
                overridePendingTransition(R.anim.alpha1_anim,R.anim.alpha_anim);
                finish();

            }
        }, SPLASH_TIME_OUT);

    }
}
