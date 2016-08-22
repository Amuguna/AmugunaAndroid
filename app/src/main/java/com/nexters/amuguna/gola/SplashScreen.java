package com.nexters.amuguna.gola;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
                                            .diskCacheStrategy( DiskCacheStrategy.RESULT )
                                            .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(), 20, 0))
                                            //.override(w,h)
                            );
                            i++;
                        }
                    }

                    int w=0,h=0;
                    if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
                        Log.e("Display!!!", "Large");
                        w=400; h=280;
                    }
                    else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
                        Log.e("Display!!!", "Normal");
                        w=280; h=180;
                    }
                    else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
                        Log.e("Display!!!", "Small");
                        w=180; h=130;
                    }
                    else {
                        Log.e("Display!!!", "Neither");
                        w=400; h=280;
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
