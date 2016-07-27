package com.nexters.amuguna.gola;

import android.animation.ObjectAnimator;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nexters.amuguna.gola.manager.GolaImageManager;
import com.transitionseverywhere.TransitionManager;


import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Daesub Kim on 2016-07-25.
 */
public class TournamentActivity extends AppCompatActivity {

    Intent intent;

    @Bind(com.nexters.amuguna.gola.R.id.center_top_img)
    ImageView topImage;

    @Bind(com.nexters.amuguna.gola.R.id.center_bottom_img)
    ImageView bottomImage;

    @Bind(com.nexters.amuguna.gola.R.id.center_layout)
    ViewGroup viewGroup;

    public static long DURATION_TIME=1000;

    static int imageIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.nexters.amuguna.gola.R.layout.activity_main);
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();

        intent = getIntent();

        if(intent.getBooleanExtra("isFirstRound", true)) {

             /* 랜덤 수를 한번 섞어준다 */
            Collections.shuffle(StaticInfo.RAN);

            for(int a:StaticInfo.RAN)
                Log.e("Random", "" + a);

            /* Default Round에 따른 CUR_ROUND, GAME_TOT 값 초기화 */
            initRound();

            /* Node 초기화 */
            StaticInfo.CUR_NODE = new int[StaticInfo.DEFAULT_ROUND+1];
            // 섞인 랜덤수를 CUR_NODE에 넣어준다.
            for(int i=1; i<=StaticInfo.DEFAULT_ROUND; i++)
                StaticInfo.CUR_NODE[i] = StaticInfo.RAN.get(i-1);
            // ROUND의 1/2 만큼 배열을 생성하여 NEXT_NODE 에 넣어준다.
            StaticInfo.NEXT_NODE = new int[StaticInfo.DEFAULT_ROUND/2+1];
        }

        Glide.with(this).load(getResourceId()).into(topImage);
        Glide.with(this).load(getResourceId()).into(bottomImage);

    }
    public int getResourceId(){
        Log.e("index-", imageIndex + "");
        int resourceId = getResources().getIdentifier("com.nexters.amuguna.gola:drawable/"+ GolaImageManager.food[imageIndex++],null,null);
        Log.e("resourceId", resourceId + "");
        return resourceId;
    }
    /* CUR_ROUND, GAME_TOT 초기화 */
    private void initRound() {
        switch(StaticInfo.DEFAULT_ROUND) {
            case 16:
                StaticInfo.CUR_ROUND = 16;
                StaticInfo.GAME_TOT = 8;
                StaticInfo.GAME_CNT = 1;
                break;
            case 8:
                StaticInfo.CUR_ROUND = 8;
                StaticInfo.GAME_TOT = 4;
                StaticInfo.GAME_CNT = 1;
                break;
            case 4:

                StaticInfo.CUR_ROUND = 4;
                StaticInfo.GAME_TOT = 2;
                StaticInfo.GAME_CNT = 1;
                break;
            case 2:
                StaticInfo.CUR_ROUND = 2;
                StaticInfo.GAME_TOT = 1;
                StaticInfo.GAME_CNT = 1;
                break;
        }
    }

    private void initNode(int round) {

        // NEXT_NODE를 CUR_NODE에 넣는다.
        StaticInfo.CUR_NODE = StaticInfo.NEXT_NODE;

        // NEXT_NODE를 round/2의 배열로 생셩하여 다시 세팅
        StaticInfo.NEXT_NODE = new int[round/2+1];

        // 현재 Round, Total Game수, GAME_CNT 다시 세팅
        StaticInfo.CUR_ROUND/=2;
        StaticInfo.GAME_TOT/=2;
        StaticInfo.GAME_CNT = 1;
    }

    private void nextGame(int
 selectedNum) {

        Log.e("몇강?", ""+ StaticInfo.CUR_ROUND);
        Log.e("총 몇 경기?", ""+ StaticInfo.GAME_TOT);
        Log.e("게임Count", ""+ StaticInfo.GAME_CNT);

        StaticInfo.NEXT_NODE[StaticInfo.GAME_CNT] = selectedNum;
        StaticInfo.GAME_CNT++;

        if(StaticInfo.GAME_CNT > StaticInfo.GAME_TOT) {
            // 결승
            if(StaticInfo.CUR_ROUND == 2) {
                //return -1;
                Log.e("결승 SelectedNum", ""+selectedNum);
                /* Move to ResultActivity. */
                intent = new Intent(TournamentActivity.this,ResultActivity.class);
                intent.putExtra("result",selectedNum);
                intent.putExtra("isTournament", true);
                intent.putExtra("isFirstRound", false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return;
            }
            initNode(StaticInfo.CUR_ROUND);
        }

        //return 1;
        Log.e("SelectedNum", ""+selectedNum);
        intent = new Intent(TournamentActivity.this,TournamentActivity.class);
        //intent.putExtra("isTournament", true);
        intent.putExtra("isFirstRound", false);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }


    @OnClick(com.nexters.amuguna.gola.R.id.center_top_img)
    void topImgClick() {


        /*TransitionManager.beginDelayedTransition(viewGroup);
        visible = !visible;
        bottomImage.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);*/
        startFlipAnimation();
        /*TransitionManager.beginDelayedTransition(viewGroup);
        bottomImage.setVisibility(View.INVISIBLE);*/

        new Thread(){
            public void run(){
                try {Thread.sleep(DURATION_TIME);} catch(Exception ex){ex.printStackTrace();}
                new Thread(new Runnable(){
                    public void run(){
                        runOnUiThread(new Runnable(){
                            public void run(){
                                nextGame(StaticInfo.CUR_NODE[StaticInfo.GAME_CNT * 2 - 1]);
                            }
                        });
                    }
                }).start();
            }
        }.start();
    }
    @OnClick(com.nexters.amuguna.gola.R.id.center_bottom_img)
    void bottomImgClick() {
        /*TransitionManager.beginDelayedTransition(viewGroup);
        visible = !visible;
        topImage.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);*/
        startFlipAnimation();
        /*TransitionManager.beginDelayedTransition(viewGroup);
        topImage.setVisibility(View.INVISIBLE);*/

        new Thread(){
            public void run(){
                try {Thread.sleep(DURATION_TIME);} catch(Exception ex){ex.printStackTrace();}
                new Thread(new Runnable(){
                    public void run(){
                        runOnUiThread(new Runnable(){
                            public void run(){
                                nextGame(StaticInfo.CUR_NODE[StaticInfo.GAME_CNT * 2]);
                            }
                        });
                    }
                }).start();
            }
        }.start();
    }
    public void startFlipAnimation(){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(topImage,"rotationY",0,180);
        animator1.setDuration(DURATION_TIME);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(bottomImage,"rotationY",0,180);
        animator2.setDuration(DURATION_TIME);
        animator1.start();
        animator2.start();


    }
}
