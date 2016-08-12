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

import com.bumptech.glide.DrawableRequestBuilder;
import com.nexters.amuguna.gola.manager.GolaImageManager;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    int nowLevelFirstNodeNo = (int) Math.pow(2,StaticInfo.TREE_CURRENT_LEVEL-1)-1;

    public static boolean isFinal = false;

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

    public static int CURRENT_TOP_POINTER;
    public static int CCURRENT_BOTTOM_POINTER;
    public static int CURRENT_POINTER_LEVEL = StaticInfo.TREE_MAX_LEVEL;
    public static int CURRENT_MAX_NODE = ( (int) Math.pow(2,CURRENT_POINTER_LEVEL-1)-1 ) * 2;;

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
            List<DrawableRequestBuilder<Integer>> tempList=Arrays.asList(StaticInfo.initTree);
            Collections.shuffle(tempList);
            /**
             * 섞인애를 다시 initTree에 넣어주공
             */
            StaticInfo.initTree = ( DrawableRequestBuilder<Integer>[]) tempList.toArray();

            /**
             * 렙 5노드의 첫 시작노드.
             */
            StaticInfo.TREE_CURRENT_LEVEL=5;
            int levelFiveStartNode = (int)Math.pow(2,StaticInfo.TREE_CURRENT_LEVEL-1)-1;

            /**
             * 5레벨의 모든 노드에 초기화걸음
             */
            for(int i=0;i<16;i++){
                StaticInfo.tournamentTree[levelFiveStartNode++] = StaticInfo.initTree[i];
            }
            levelFiveStartNode-=16;
            //System.arraycopy(StaticInfo.initTree,0,StaticInfo.tournamentTree,levelFiveStartNode,levelFiveStartNode*2+2);

            CURRENT_TOP_POINTER = StaticInfo.TREE_CURRENT = levelFiveStartNode;
            CCURRENT_BOTTOM_POINTER = CURRENT_TOP_POINTER+1;
            StaticInfo.tournamentTree[StaticInfo.TREE_CURRENT++].into(topImage);
            StaticInfo.tournamentTree[StaticInfo.TREE_CURRENT++].into(bottomImage);
            StaticInfo.tournamentTree[StaticInfo.TREE_CURRENT++].into(topImage2);
            StaticInfo.tournamentTree[StaticInfo.TREE_CURRENT++].into(bottomImage2);
        }
    }

    public static boolean isLoadingImageMutex = true;
    private void loadingImage(){
        checkRound();
        if(!isFinal) {
            int tempStartNodeNo = (int) Math.pow(2, StaticInfo.TREE_CURRENT_LEVEL - 1) - 1;
            if (StaticInfo.TREE_CURRENT == tempStartNodeNo * 2) {
                int superStartNodeNo = (int) Math.pow(2, StaticInfo.TREE_CURRENT_LEVEL - 2) - 1;
                topImgNum = superStartNodeNo++;
                bottomImgNum = superStartNodeNo;
            } else {
                topImgNum = StaticInfo.TREE_CURRENT++;
                bottomImgNum = StaticInfo.TREE_CURRENT++;
            }
            CURRENT_TOP_POINTER += 2;
            CCURRENT_BOTTOM_POINTER += 2;
            try {
                if (isLoadingImageMutex) {
                    secondLinear.setVisibility(View.VISIBLE);
                    firstLinear.setVisibility(View.GONE);
                    StaticInfo.tournamentTree[topImgNum].into(topImage);
                    StaticInfo.tournamentTree[bottomImgNum].into(bottomImage);
                } else {
                    firstLinear.setVisibility(View.VISIBLE);
                    secondLinear.setVisibility(View.GONE);
                    StaticInfo.tournamentTree[topImgNum].into(topImage2);
                    StaticInfo.tournamentTree[bottomImgNum].into(bottomImage2);
                }
                isLoadingImageMutex = !isLoadingImageMutex;
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else if( CURRENT_TOP_POINTER == 2 || CCURRENT_BOTTOM_POINTER == 2) {
            secondLinear.setVisibility(View.VISIBLE);
            firstLinear.setVisibility(View.GONE);
            StaticInfo.tournamentTree[1].into(topImage2);
            StaticInfo.tournamentTree[2].into(bottomImage2);
        }

    }

    private void checkRound(){
        nowLevelFirstNodeNo = (int) Math.pow(2,StaticInfo.TREE_CURRENT_LEVEL-1)-1;
        if( StaticInfo.TREE_CURRENT == nowLevelFirstNodeNo*2+1 ) {
            StaticInfo.TREE_CURRENT = (nowLevelFirstNodeNo-1)/2;
            --StaticInfo.TREE_CURRENT_LEVEL;

        }
        Log.e("TOP_POINTER/MAX_NODE", CURRENT_TOP_POINTER + "/" + CURRENT_MAX_NODE);
        if( CURRENT_TOP_POINTER == CURRENT_MAX_NODE-1) {
            CURRENT_POINTER_LEVEL-=1;
            int temp = (int)Math.pow(2,CURRENT_POINTER_LEVEL-1)-1;
            if(temp==0) {

            }
            Log.e("temp",temp+"");

            CURRENT_TOP_POINTER = temp-2;
            CURRENT_MAX_NODE = temp*2;

            CCURRENT_BOTTOM_POINTER = CURRENT_TOP_POINTER+1;

        }
        Log.e("nowIndex--",StaticInfo.TREE_CURRENT+"");
        if(StaticInfo.TREE_CURRENT == 1) {
            isFinal = true;
        }

    }


    /**
     * 위의 음식 그림 선택 시
     */
    private void topImageClick(){
        // 결승

        /*부모노드에 데이터 셋!*/
        Log.e("top-부모노드/자식노드 - ",((CURRENT_TOP_POINTER-1)/2)+"/"+CURRENT_TOP_POINTER );
        StaticInfo.tournamentTree[(CURRENT_TOP_POINTER-1)/2]=StaticInfo.tournamentTree[CURRENT_TOP_POINTER];
        loadingImage();
    }
    @OnClick(com.nexters.amuguna.gola.R.id.center_top_img)
    void topImgClick() {
        topImageClick();
    }
    @OnClick(R.id.center_top_img2)
    void topImgClick2(){
        topImageClick();
    }
    /**
     * 하단의 음식 그림 선택 시
     */
    public void bottomImageClick(){

        Log.e("bottom부모노드/자식노드 - ",((CCURRENT_BOTTOM_POINTER-1)/2)+"/"+CCURRENT_BOTTOM_POINTER );
        StaticInfo.tournamentTree[(CCURRENT_BOTTOM_POINTER-1)/2]=StaticInfo.tournamentTree[CCURRENT_BOTTOM_POINTER];
        loadingImage();
    }
    @OnClick(com.nexters.amuguna.gola.R.id.center_bottom_img)
    void bottomImgClick() {
        bottomImageClick();
        //clickThread(bottomImgNum, topImage);
    }
    @OnClick(R.id.center_bottom_img2)
    void bottomClick2(){
        bottomImageClick();
    }




    /*---------------------------------------------------------------------------------------------------------------------------*/

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
    /*private void clickThread(final int imgNo,final ImageView imageView){
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
    }*/
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
