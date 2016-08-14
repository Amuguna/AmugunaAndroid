package com.nexters.amuguna.gola;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.nexters.amuguna.gola.manager.GolaImageManager;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Daesub Kim on 2016-07-09.
 */
public class SplashScreen extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.nexters.amuguna.gola.R.layout.splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.splash_color));
        }
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    StaticInfo.RAN.clear();
                    StaticInfo.cardX = Glide.with(SplashScreen.this)
                            .load(getResources().getIdentifier("com.nexters.amuguna.gola:drawable/card_x",null,null));

                    final Class<?> clz = R.drawable.class;
                    Field[] drawables = clz.getDeclaredFields();
                    int i = 0;
                    for (Field f : drawables) {
                        String imageName = f.getName();
                        if(imageName.startsWith("gola_img_")) {

                            Log.e("ImageName", i + " / " + imageName);
                            // 랜덤 수 세팅
                            StaticInfo.RAN.add(i);
                            // ImageList 세팅
                            StaticInfo.imageList.add(
                                    Glide.with(SplashScreen.this)
                                            .load(getResources().getIdentifier("com.nexters.amuguna.gola:drawable/" + imageName,null,null))
                                            .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(), 20, 0))
                            );
                            i++;
                        }

                    }
                    sleep(1500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }catch(Exception e) {
                    e.printStackTrace();
                }finally{
                   /* Move to GolaMainActivity normally. */
                    Intent intent = new Intent(SplashScreen.this,GolaMainActivity.class);
                    startActivity(intent);
                    finish();
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
