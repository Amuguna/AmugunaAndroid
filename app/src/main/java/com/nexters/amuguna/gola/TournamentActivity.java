package com.nexters.amuguna.gola;

import android.content.Intent;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.nexters.amuguna.gola.manager.GolaImageManager;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
/**
 * Created by Daesub Kim on 2016-07-25.
 */
public class TournamentActivity extends AppCompatActivity {

    final AppCompatActivity thisAc = this;
    Intent intent;

    @Bind(R.id.img_16)
    View oval16;
    @Bind(R.id.img_8)
    View oval8;
    @Bind(R.id.img_4)
    View oval4;
    @Bind(R.id.img_2)
    View oval2;

    @Bind(com.nexters.amuguna.gola.R.id.center_top_img)
    ImageView topImage;

    @Bind(R.id.center_top_text)
    TextView topText;

    @Bind(com.nexters.amuguna.gola.R.id.center_bottom_img)
    ImageView bottomImage;

    @Bind(R.id.center_bottom_text)
    TextView bottomText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();
        intent = getIntent();

        if(intent.getBooleanExtra("isFirstRound", true)) {

            // CNT, ROUND 초기화
            StaticInfo.CNT = 0; StaticInfo.ROUND = 16;
            // 랜덤수 섞어줌
            Collections.shuffle(StaticInfo.RAN);

            // 섞은 랜덤수를 16강 배열에 넣음.
            for(int i=0; i<StaticInfo.ROUND; i++)
                StaticInfo.ROUND_16[i] = StaticInfo.RAN.get(i);

            /* 상단 Progress 세팅  */
            setTopProgressOval();

            /* 첫 Image 세팅 */
            StaticInfo.imageList.get(StaticInfo.ROUND_16[0]).into(topImage);
            StaticInfo.imageList.get(StaticInfo.ROUND_16[1]).into(bottomImage);

            topText.setText(StaticInfo.foodName[StaticInfo.ROUND_16[0]]);
            bottomText.setText(StaticInfo.foodName[StaticInfo.ROUND_16[1]]);

        }
    }

    private void setTopProgressOval() {
        /* 상단 Progress 세팅  */
        switch (StaticInfo.ROUND) {
            case 16 :
                oval16.setAlpha(0);
                oval8.setAlpha(0.5f);
                oval4.setAlpha(0.5f);
                oval2.setAlpha(0.5f);
                break;
            case 8 :
                oval16.setAlpha(0.5f);
                oval8.setAlpha(0);
                oval4.setAlpha(0.5f);
                oval2.setAlpha(0.5f);
                break;
            case 4 :
                oval16.setAlpha(0.5f);
                oval8.setAlpha(0.5f);
                oval4.setAlpha(0);
                oval2.setAlpha(0.5f);
                break;
            case 2 :
                oval16.setAlpha(0.5f);
                oval8.setAlpha(0.5f);
                oval4.setAlpha(0.5f);
                oval2.setAlpha(0);
                break;
        }


    }
    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    /**
     * 위의 음식 그림 선택 시
     */
    @OnClick(com.nexters.amuguna.gola.R.id.center_top_img)
    void topImgClick() {

        topImage.setEnabled(false);
        bottomImage.setEnabled(false);


        //bottomImage.setImageResource(R.drawable.card_x);
        bottomImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.card_x));



        sleep(200);
        Log.e("Top", StaticInfo.ROUND + "강 : " + (StaticInfo.CNT+1) + "경기 : ");

        switch(StaticInfo.ROUND) {

            case 16 :

                Log.e("Top Food", StaticInfo.ROUND_16[ 2*StaticInfo.CNT ] + " / " +  StaticInfo.foodName[StaticInfo.ROUND_16[ 2*StaticInfo.CNT ]]);
                StaticInfo.ROUND_8[StaticInfo.CNT] =  StaticInfo.ROUND_16[ 2*StaticInfo.CNT ];
                StaticInfo.CNT++;

                // 16강 라운드 마지막경기 일 경우
                if( StaticInfo.CNT == StaticInfo.ROUND/2 ) {
                    StaticInfo.ROUND/=2; StaticInfo.CNT=0;
                }

                break;
            case 8 :

                Log.e("Top Food", StaticInfo.ROUND_8[ 2*StaticInfo.CNT ] + " / " +  StaticInfo.foodName[StaticInfo.ROUND_8[ 2*StaticInfo.CNT ]]);
                StaticInfo.ROUND_4[StaticInfo.CNT] =  StaticInfo.ROUND_8[ 2*StaticInfo.CNT ];
                StaticInfo.CNT++;

                // 16강 라운드 마지막경기 일 경우
                if( StaticInfo.CNT == StaticInfo.ROUND/2 ) {
                    StaticInfo.ROUND/=2; StaticInfo.CNT=0;
                }

                break;
            case 4 :

                Log.e("Top Food", StaticInfo.ROUND_4[ 2*StaticInfo.CNT ] + " / " +  StaticInfo.foodName[StaticInfo.ROUND_4[ 2*StaticInfo.CNT ]]);
                StaticInfo.ROUND_2[StaticInfo.CNT] =  StaticInfo.ROUND_4[ 2*StaticInfo.CNT ];
                StaticInfo.CNT++;

                // 16강 라운드 마지막경기 일 경우
                if( StaticInfo.CNT == StaticInfo.ROUND/2 ) {
                    StaticInfo.ROUND/=2; StaticInfo.CNT=0;
                }

                break;

            case 2 :

                //StaticInfo.ROUND_2[StaticInfo.CNT] =  StaticInfo.ROUND_4[ 2*StaticInfo.CNT ];

                Log.e("IsLastGame", "True");
                /* Move to ResultActivity. */

                intent = new Intent(TournamentActivity.this,ResultActivity.class);
                intent.putExtra("result", StaticInfo.ROUND_2[0] );
                intent.putExtra("isTournament", true);
                intent.putExtra("isFirstRound", false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;

        }

        // Image Load
        setTopProgressOval();
        imageLoad();
    }


    /**
     * 하단의 음식 그림 선택 시
     */
    @OnClick(com.nexters.amuguna.gola.R.id.center_bottom_img)
    void bottomImgClick() {

        topImage.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.card_x));

        topImage.setEnabled(false);
        bottomImage.setEnabled(false);



        /*대섭*/
        Log.e("Bottom", StaticInfo.ROUND + "강 : " + (StaticInfo.CNT+1) + "경기 : " +  2*StaticInfo.CNT+1);

        switch(StaticInfo.ROUND) {

            case 16 :

                Log.e("Bottom Food", StaticInfo.ROUND_16[ 2*StaticInfo.CNT+1 ] + " / " +  StaticInfo.foodName[StaticInfo.ROUND_16[ 2*StaticInfo.CNT+1 ]]);
                StaticInfo.ROUND_8[StaticInfo.CNT] =  StaticInfo.ROUND_16[ 2*StaticInfo.CNT+1 ];
                StaticInfo.CNT++;

                // 16강 라운드 마지막경기 일 경우
                if( StaticInfo.CNT == StaticInfo.ROUND/2 ) {
                    StaticInfo.ROUND/=2; StaticInfo.CNT=0;
                }

                break;
            case 8 :

                Log.e("Bottom Food", StaticInfo.ROUND_8[ 2*StaticInfo.CNT+1 ] + " / " +  StaticInfo.foodName[StaticInfo.ROUND_8[ 2*StaticInfo.CNT+1 ]]);
                StaticInfo.ROUND_4[StaticInfo.CNT] =  StaticInfo.ROUND_8[ 2*StaticInfo.CNT+1 ];
                StaticInfo.CNT++;
                // 16강 라운드 마지막경기 일 경우
                if( StaticInfo.CNT == StaticInfo.ROUND/2 ) {
                    StaticInfo.ROUND/=2; StaticInfo.CNT=0;
                }

                break;
            case 4 :

                Log.e("Bottom Food", StaticInfo.ROUND_4[ 2*StaticInfo.CNT+1 ] + " / " +  StaticInfo.foodName[StaticInfo.ROUND_4[ 2*StaticInfo.CNT+1 ]]);
                StaticInfo.ROUND_2[StaticInfo.CNT] =  StaticInfo.ROUND_4[ 2*StaticInfo.CNT+1 ];

                StaticInfo.CNT++;

                // 16강 라운드 마지막경기 일 경우
                if( StaticInfo.CNT == StaticInfo.ROUND/2 ) {
                    StaticInfo.ROUND/=2; StaticInfo.CNT=0;
                }

                break;
            case 2 :
                //StaticInfo.ROUND_16[ 2*StaticInfo.CNT+1 ];
                Log.e("IsLastGame", "True");

                intent = new Intent(TournamentActivity.this,ResultActivity.class);
                intent.putExtra("result", StaticInfo.ROUND_2[0] );
                intent.putExtra("isTournament", true);
                intent.putExtra("isFirstRound", false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
        }

        // Image Load
        setTopProgressOval();
        imageLoad();

    }

    /* 대섭 */
    private void imageLoad() {

        switch(StaticInfo.ROUND) {

            case 16 :
                StaticInfo.imageList.get(StaticInfo.ROUND_16[ 2*StaticInfo.CNT ]).into(topImage);
                StaticInfo.imageList.get(StaticInfo.ROUND_16[ 2*StaticInfo.CNT+1 ]).into(bottomImage);

                topText.setText(StaticInfo.foodName[StaticInfo.ROUND_16[ 2*StaticInfo.CNT ]]);
                bottomText.setText(StaticInfo.foodName[StaticInfo.ROUND_16[ 2*StaticInfo.CNT+1 ]]);
                break;
            case 8 :
                StaticInfo.imageList.get(StaticInfo.ROUND_8[ 2*StaticInfo.CNT ]).into(topImage);
                StaticInfo.imageList.get(StaticInfo.ROUND_8[ 2*StaticInfo.CNT+1 ]).into(bottomImage);

                topText.setText(StaticInfo.foodName[StaticInfo.ROUND_8[ 2*StaticInfo.CNT ]]);
                bottomText.setText(StaticInfo.foodName[StaticInfo.ROUND_8[ 2*StaticInfo.CNT+1 ]]);
                break;
            case 4 :
                StaticInfo.imageList.get(StaticInfo.ROUND_4[ 2*StaticInfo.CNT ]).into(topImage);
                StaticInfo.imageList.get(StaticInfo.ROUND_4[ 2*StaticInfo.CNT+1 ]).into(bottomImage);

                topText.setText(StaticInfo.foodName[StaticInfo.ROUND_4[ 2*StaticInfo.CNT ]]);
                bottomText.setText(StaticInfo.foodName[StaticInfo.ROUND_4[ 2*StaticInfo.CNT+1 ]]);
                break;
            case 2 :
                StaticInfo.imageList.get(StaticInfo.ROUND_2[ 2*StaticInfo.CNT ]).into(topImage);
                StaticInfo.imageList.get(StaticInfo.ROUND_2[ 2*StaticInfo.CNT+1 ]).into(bottomImage);

                topText.setText(StaticInfo.foodName[StaticInfo.ROUND_2[ 2*StaticInfo.CNT ]]);
                bottomText.setText(StaticInfo.foodName[StaticInfo.ROUND_2[ 2*StaticInfo.CNT+1 ]]);
                //StaticInfo.ROUND_16[ 2*StaticInfo.CNT+1 ];
                break;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            topImage.setEnabled(true);
                            bottomImage.setEnabled(true);
                        }
                    });
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();


    }

    /*---------------------------------------------------------------------------------------------------------------------------*/

    @OnClick(R.id.go_home_top_linear)
    void goHomeBtnClick() {
        Intent intent = new Intent(TournamentActivity.this,GolaMainActivity.class);
        intent.putExtra("isFirstRound", true);
        startActivity(intent);
        finish();
    }
    @OnClick( com.nexters.amuguna.gola.R.id.retry_top_linear)
    void retryBtnClick() {
        /* Move to TournamentActivity. */
        Intent intent = new Intent(TournamentActivity.this,TournamentActivity.class);
        intent.putExtra("isTournament", true);
        intent.putExtra("isFirstRound", true);
        intent.putExtra("round", StaticInfo.ROUND);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

/*public void startFlipAnimation(){

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(topImage,"rotationX",180,0);
        animator1.setDuration(DURATION_TIME);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(bottomImage,"rotationX",180,0);
        animator2.setDuration(DURATION_TIME);
        animator1.start();
        animator2.start();

    }*/
/*private int getResourceId(int imgIndex){
        Log.e("index-", imgIndex + "");
        int resourceId = getResources().getIdentifier("com.nexters.amuguna.gola:drawable/"+ GolaImageManager.food[imgIndex],null,null);
        Log.e("resourceId", resourceId + "");
        return resourceId;
    }*/
/*
private void nextGame(int selectedNum) {
    showAsLog(new String[]{"몇강?",""+ StaticInfo.CUR_ROUND,"총 몇 경기?", ""+ StaticInfo.GAME_TOT,"게임Count", ""+ StaticInfo.GAME_CNT} );

    StaticInfo.NEXT_NODE[StaticInfo.GAME_CNT++] = selectedNum;

    if(StaticInfo.GAME_CNT > StaticInfo.GAME_TOT) {
        // 결승
        if(StaticInfo.CUR_ROUND == 2) {
            //return -1;
            Log.e("결승 SelectedNum", ""+selectedNum);
                */
/* Move to ResultActivity. *//*

            intent = new Intent(TournamentActivity.this,ResultActivity.class);
            intent.putExtra("result",selectedNum);
            intent.putExtra("isTournament", true);
            intent.putExtra("isFirstRound", false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        }
        initNode(StaticInfo.CUR_ROUND);
    }

    Log.e("SelectedNum", "" + selectedNum);

    loadingImage();
}*/
