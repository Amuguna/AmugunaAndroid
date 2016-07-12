package com.nexters.amuguna.golla;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Daesub Kim on 2016-07-09.
 */
public class SplashScreen extends AppCompatActivity {

    @Bind(R.id.splash_img)
    ImageView splashImg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.fleax_splash).into(splashImg);

        /* Hide ActionBar */
        getSupportActionBar().hide();

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    /* Expose splash image for 2 sec. */
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    /* Move to GollaMainActivity. */
                    Intent intent = new Intent(SplashScreen.this,GollaMainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    protected void onPause() {
        super.onPause();
        finish();
    }
}
