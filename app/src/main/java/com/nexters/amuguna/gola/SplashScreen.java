package com.nexters.amuguna.gola;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.nexters.amuguna.gola.manager.GolaImageManager;

import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

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

                    for(int i=0;i < StaticInfo.IMAGE_COUNT; i++ ){
                        StaticInfo.resourceList.add(getResources().getIdentifier("com.nexters.amuguna.gola:drawable/"+ GolaImageManager.food[i],null,null));
                    }
                    for(int i=0;i<StaticInfo.IMAGE_COUNT;i++){
                        StaticInfo.imageList.add(
                                Glide.with(SplashScreen.this).load(StaticInfo.resourceList.get(i))
                                        .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(), 20, 0))
                        );
                    }
                    Log.e("imageSize-",StaticInfo.IMAGE_COUNT+"");
                    /*------------잠시 구현한 부분 ---------------*/

                    /**
                     * 이미지 16장 로딩
                     */
                    for(int i= 0  ; i< StaticInfo.ROUND ; i++)
                        StaticInfo.initTree[i]=Glide.with(SplashScreen.this).load(StaticInfo.resourceList.get(i)).bitmapTransform(new RoundedCornersTransformation(getApplicationContext(), 20, 0));
                    /*------------------------------------------*/
                    /* Expose splash image for 2 sec. */
                    sleep(1);
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
