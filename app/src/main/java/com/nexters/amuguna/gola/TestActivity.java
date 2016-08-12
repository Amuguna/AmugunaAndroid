package com.nexters.amuguna.gola;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.nexters.amuguna.gola.manager.GolaImageManager;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Daesub Kim on 2016-08-06.
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
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
                                    Glide.with(TestActivity.this)
                                            .load(getResources().getIdentifier("com.nexters.amuguna.gola:drawable/" + imageName,null,null))
                                            .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(), 20, 0))
                            );
                            i++;
                        }

                    }

                  /*  for(int i=0; i<StaticInfo.imageList.size(); i++) {
                    //for(DrawableRequestBuilder a : StaticInfo.imageList) {
                        //Log.e(StaticInfo.imageList.get(i).toString() , i + " / " + StaticInfo.foodName[i] );
                        StaticInfo.RAN.add(i);
                    }*/

                    sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }catch(Exception e) {
                    e.printStackTrace();
                }finally{
                   /* Move to GolaMainActivity normally. */
                    Intent intent = new Intent(TestActivity.this,GolaMainActivity.class);

                    startActivity(intent);

                    Log.e("Finished?", "YES");
                }
            }
        };
        timerThread.start();
    }
}
