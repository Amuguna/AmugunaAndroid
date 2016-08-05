package com.nexters.amuguna.gola;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nexters.amuguna.gola.manager.GolaImageManager;

import butterknife.ButterKnife;

/**
 * Created by Daesub Kim on 2016-07-09.
 */
public class SplashScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.nexters.amuguna.gola.R.layout.splash);
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();

        //Glide.with(this).load(com.nexters.amuguna.gola.R.drawable.fleax_splash).into(splashImg);



        Thread timerThread = new Thread(){
            public void run(){
                try{

                    // 랜덤 수 세팅
                    StaticInfo.RAN.clear();
                    for(int i=1; i<=GolaImageManager.food.length; i++) {
                        StaticInfo.RAN.add(i);

                    }

                    /* Expose splash image for 2 sec. */
                    sleep(1500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                   /* Move to GolaMainActivity normally. */
                    Intent intent = new Intent(SplashScreen.this,GolaMainActivity.class);

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
