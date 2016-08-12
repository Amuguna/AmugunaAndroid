package com.nexters.amuguna.gola;

import android.content.Intent;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nexters.amuguna.gola.manager.GolaImageManager;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
/**
 * Created by Daesub Kim on 2016-07-25.
 */
public class TournamentActivity extends AppCompatActivity {

    final AppCompatActivity thisAc = this;

    Intent intent;
    int topImgNum;
    int bottomImgNum;

    @Bind(com.nexters.amuguna.gola.R.id.retry_top_linear)
    ViewGroup retryTopLinear;

    @Bind(com.nexters.amuguna.gola.R.id.go_home_top_linear)
    ViewGroup goHomeTopLinear;

    @Bind(com.nexters.amuguna.gola.R.id.center_top_img)
    ImageView topImage;

    @Bind(R.id.center_top_img2)
    ImageView topImage2;

    @Bind(com.nexters.amuguna.gola.R.id.center_bottom_img)
    ImageView bottomImage;

    @Bind(R.id.center_bottom_img2)
    ImageView bottomImage2;

    @Bind(R.id.firstLinear)
    LinearLayout firstLinear;
    @Bind(R.id.secondLinear)
    LinearLayout secondLinear;

    public static long DURATION_TIME=500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();
        intent = getIntent();

        if(intent.getBooleanExtra("isFirstRound", true)) {

             /* 랜덤 수를 한번 섞어준다 */
            Collections.shuffle(StaticInfo.RAN);

            for(int a:StaticInfo.RAN) {
                Log.e("Random", "" + a);
            }

            /* Default Round에 따른 CUR_ROUND, GAME_TOT 값 초기화 */
            initRound();

            /* Node 초기화 */
            StaticInfo.CUR_NODE = new int[StaticInfo.DEFAULT_ROUND+1];
            // 섞인 랜덤수를 CUR_NODE에 넣어준다.
            for(int i=1; i<=StaticInfo.DEFAULT_ROUND; i++)
                StaticInfo.CUR_NODE[i] = StaticInfo.RAN.get(i-1);
            // ROUND의 1/2 만큼 배열을 생성하여 NEXT_NODE 에 넣어준다.
            StaticInfo.NEXT_NODE = new int[StaticInfo.DEFAULT_ROUND/2+1];
            loadingImage();

        }
    }
    public static boolean isLoadingImageMutex = true;
    private void loadingImage(){
        topImgNum = StaticInfo.CUR_NODE[StaticInfo.GAME_CNT * 2 - 1];
        bottomImgNum = StaticInfo.CUR_NODE[StaticInfo.GAME_CNT * 2];

        if(isLoadingImageMutex) {
            firstLinear.setVisibility(View.VISIBLE);
            secondLinear.setVisibility(View.GONE);
            StaticInfo.imageList.get(topImgNum - 1).into(topImage2);
            StaticInfo.imageList.get(bottomImgNum - 1).into(bottomImage2);
        } else {
            secondLinear.setVisibility(View.VISIBLE);
            firstLinear.setVisibility(View.GONE);
            StaticInfo.imageList.get(topImgNum - 1).into(topImage);
            StaticInfo.imageList.get(bottomImgNum - 1).into(bottomImage);
        }
        isLoadingImageMutex=!isLoadingImageMutex;

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
    private void showAsLog(String... logs) {
        for(int i=0;i<logs.length;i+=2)
            Log.e(logs[i],logs[i+1]);
    }
    private void nextGame(int selectedNum) {
        showAsLog(new String[]{"몇강?",""+ StaticInfo.CUR_ROUND,"총 몇 경기?", ""+ StaticInfo.GAME_TOT,"게임Count", ""+ StaticInfo.GAME_CNT} );

        StaticInfo.NEXT_NODE[StaticInfo.GAME_CNT++] = selectedNum;

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
                finish();
                return;
            }
            initNode(StaticInfo.CUR_ROUND);
        }

        Log.e("SelectedNum", "" + selectedNum);

        loadingImage();
    }
    @OnClick(R.id.go_home_top_linear)
    void goHomeBtnClick() {
        Intent intent = new Intent(TournamentActivity.this,GolaMainActivity.class);
        startActivity(intent);
        finish();
    }
    @OnClick( com.nexters.amuguna.gola.R.id.retry_top_linear)
    void retryBtnClick() {
        /* Move to TournamentActivity. */
        Intent intent = new Intent(TournamentActivity.this,TournamentActivity.class);
        intent.putExtra("isTournament", true);
        intent.putExtra("isFirstRound", true);
        intent.putExtra("round", StaticInfo.DEFAULT_ROUND);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * 위의 음식 그림 선택 시
     */
    @OnClick(com.nexters.amuguna.gola.R.id.center_top_img)
    void topImgClick() {
        // 결승
        if(StaticInfo.CUR_ROUND == 2) {
            nextGame(topImgNum); return;
        }
        clickThread(topImgNum, bottomImage);
    }

    /**
     * 하단의 음식 그림 선택 시
     */
    @OnClick(com.nexters.amuguna.gola.R.id.center_bottom_img)
    void bottomImgClick() {
        if(StaticInfo.CUR_ROUND == 2) {
            nextGame(bottomImgNum);
            return;
        }
        clickThread(bottomImgNum, topImage);
    }
    private void clickThread(final int imgNo,final ImageView imageView){
        topImage.setEnabled(false);
        bottomImage.setEnabled(false);
        //imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.card_x));
        imageView.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.card_x));
        new Thread(){
            public void run(){
                try {Thread.sleep(DURATION_TIME);} catch(Exception ex){ex.printStackTrace();}
                new Thread(new Runnable(){
                    public void run(){
                        runOnUiThread(new Runnable(){
                            public void run(){
                                nextGame(imgNo);
                                topImage.setEnabled(true);
                                bottomImage.setEnabled(true);
                            }
                        });
                    }
                }).start();
            }
        }.start();
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