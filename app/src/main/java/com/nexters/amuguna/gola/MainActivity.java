package com.nexters.amuguna.gola;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.transitionseverywhere.TransitionManager;

import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    //private static ArrayList<String, String>
    int[] ran;
    //boolean visible=true;
    SharedPreferences prefs;
    Intent intent;
    final String firstRoundPrefs = "firstRound";
    //TournametManager tournament;

    @Bind(com.nexters.amuguna.gola.R.id.center_top_img)
    ImageView topImage;

    @Bind(com.nexters.amuguna.gola.R.id.center_bottom_img)
    ImageView bottomImage;

    @Bind(com.nexters.amuguna.gola.R.id.center_layout)
    ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.nexters.amuguna.gola.R.layout.activity_main);
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();

        intent = getIntent();
        //tournament = TournametManager.getInstance();

        if(intent.getBooleanExtra("isFirstRound", true)) {

            /* 랜덤 수를 한번 섞어준다 */
            Collections.shuffle(StaticInfomation.RAN);

            initTree();


            //int[] ran = tournament.initTree(intent.getIntExtra("round", StaticInfomation.DEFAULT_ROUND));
            //int[] ran = tournament.initTree(intent.getIntExtra("round", StaticInfomation.DEFAULT_ROUND));
        }

        Glide.with(this).load(R.drawable.gola_img_dupbab).into(topImage);
        Glide.with(this).load(R.drawable.gola_img_galbi).into(bottomImage);
    }

    private void initTree() {
        switch(StaticInfomation.DEFAULT_ROUND) {
            case 32 :
                StaticInfomation.ROUND = 32;
                StaticInfomation.ROUND_TOT = 16;
                break;
            case 16 :
                StaticInfomation.ROUND = 16;
                StaticInfomation.ROUND_TOT = 8;
                break;
            case 8 :
                StaticInfomation.ROUND = 8;
                StaticInfomation.ROUND_TOT = 4;
                break;
            case 4 :
                StaticInfomation.ROUND = 4;
                StaticInfomation.ROUND_TOT = 2;
                break;
            case 2 :
                StaticInfomation.ROUND = 2;
                StaticInfomation.ROUND_TOT = 1;
                break;
        }
        StaticInfomation.ARR_NODE = new int[StaticInfomation.DEFAULT_ROUND+1];
        StaticInfomation.ARR_NODE[0] = StaticInfomation.DEFAULT_ROUND;
        for(int i=1; i<=StaticInfomation.DEFAULT_ROUND; i++) {
            StaticInfomation.ARR_NODE[i] = StaticInfomation.RAN.get(i-1);
        }
        StaticInfomation.NEXT_ARR_NODE = new int[StaticInfomation.DEFAULT_ROUND/2 + 1];
        StaticInfomation.NEXT_ARR_NODE[0] = StaticInfomation.DEFAULT_ROUND/2;
    }

    private void nextRound(int selectedNum) {

        Log.e("몇강?", ""+StaticInfomation.ROUND);
        Log.e("몇 경기?", ""+StaticInfomation.ROUND_TOT);
        Log.e("COUNT", ""+StaticInfomation.CNT);

        StaticInfomation.NEXT_ARR_NODE[StaticInfomation.CNT] = selectedNum;
        if(StaticInfomation.ROUND_TOT == StaticInfomation.CNT) {
            // 결승
            if(StaticInfomation.ROUND == 2) {
                //return -1;
                Log.e("SelectedNum", ""+selectedNum);
                /* Move to ResultActivity. */
                intent = new Intent(MainActivity.this,ResultActivity.class);
                intent.putExtra("result",selectedNum);
                intent.putExtra("isTournament", true);
                intent.putExtra("isFirstRound", false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return;
            }
            nextNode();
            //return 0;
        }

        StaticInfomation.CNT++;

        //return 1;
        Log.e("SelectedNum", ""+selectedNum);
        intent = new Intent(MainActivity.this,MainActivity.class);
        //intent.putExtra("isTournament", true);
        intent.putExtra("isFirstRound", false);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    private void nextNode() {
        int[] temp;
        temp = StaticInfomation.ARR_NODE;
        StaticInfomation.ARR_NODE = StaticInfomation.NEXT_ARR_NODE;
        StaticInfomation.NEXT_ARR_NODE = temp;

        StaticInfomation.ROUND/=2;
        StaticInfomation.ROUND_TOT/=2;

        StaticInfomation.NEXT_ARR_NODE[0] = StaticInfomation.ROUND;

        StaticInfomation.CNT = 0;

    }

    /*private void nextRound1() {

        switch (tournament.nextRound()) {

            case -1 :
                *//* Test *//*
                Log.e("Depth / Count", tournament.getDepth() + " / " + tournament.getCount());
                *//* Move to ResultActivity. *//*
                intent = new Intent(MainActivity.this,ResultActivity.class);
                intent.putExtra("isTournament", true);
                intent.putExtra("isFirstRound", false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case 0 :
            case 1 :
                *//* Test *//*
                Log.e("Depth / Count", tournament.getDepth() + " / " + tournament.getCount());
                intent = new Intent(MainActivity.this,MainActivity.class);
                //intent.putExtra("isTournament", true);
                intent.putExtra("isFirstRound", false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return ;
    }*/

    @OnClick(com.nexters.amuguna.gola.R.id.center_top_img)
    void topImgClick() {

        /*TransitionManager.beginDelayedTransition(viewGroup);
        visible = !visible;
        bottomImage.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);*/


        TransitionManager.beginDelayedTransition(viewGroup);
        bottomImage.setVisibility(View.INVISIBLE);

        nextRound(StaticInfomation.ARR_NODE[StaticInfomation.CNT*2-1]);

        //switch (nextRound(StaticInfomation.ARR_NODE[StaticInfomation.CNT*2-1]){

        //}
    }

    @OnClick(com.nexters.amuguna.gola.R.id.center_bottom_img)
    void bottomImgClick() {

        /*TransitionManager.beginDelayedTransition(viewGroup);
        visible = !visible;
        topImage.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);*/


        TransitionManager.beginDelayedTransition(viewGroup);
        topImage.setVisibility(View.INVISIBLE);

        nextRound(StaticInfomation.ARR_NODE[StaticInfomation.CNT*2]);
    }
}
