package com.nexters.amuguna.gola;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.skyfishjy.library.RippleBackground;
import java.util.Collections;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by Daesub Kim on 2016-07-25.
 */
public class TournamentActivity extends AppCompatActivity {

    static boolean isFlag;
    Intent intent;

    @Bind(R.id.img_16)
    View oval16;
    @Bind(R.id.img_8)
    View oval8;
    @Bind(R.id.img_4)
    View oval4;
    @Bind(R.id.img_2)
    View oval2;

    @Bind(R.id.tv16)
    TextView txtView16;
    @Bind(R.id.tv8)
    TextView txtView8;
    @Bind(R.id.tv4)
    TextView txtView4;
    @Bind(R.id.tv2)
    TextView txtView2;

    @Bind(R.id.img_bg_top)
    View imgBgTop;
    @Bind(R.id.img_bg_bottom)
    View imgBgBottom;


    @Bind(com.nexters.amuguna.gola.R.id.center_top_next_img)
    ImageView topNextImage;
    @Bind(com.nexters.amuguna.gola.R.id.center_top_img)
    ImageView topImage;
    @Bind(R.id.center_top_text)
    TextView topText;

    @Bind(com.nexters.amuguna.gola.R.id.center_bottom_next_img)
    ImageView bottomNextImage;
    @Bind(com.nexters.amuguna.gola.R.id.center_bottom_img)
    ImageView bottomImage;
    @Bind(R.id.center_bottom_text)
    TextView bottomText;


    @Bind(R.id.ripple)
    RippleBackground ripple;

    @Bind(R.id.selected_top_like)
    ImageView selectedTopLike;
    @Bind(R.id.selected_top_next_like)
    ImageView selectedTopLike2;

    @Bind(R.id.selected_bottom_like)
    ImageView selectedBottomLike;
    @Bind(R.id.selected_bottom_next_like)
    ImageView selectedBottomLike2;

    private void setLikeImage(int resourceId) {
        selectedBottomLike.setImageBitmap(BitmapFactory.decodeResource(getResources(),resourceId));
        selectedBottomLike2.setImageBitmap(BitmapFactory.decodeResource(getResources(),resourceId));
        selectedTopLike.setImageBitmap(BitmapFactory.decodeResource(getResources(),resourceId));
        selectedTopLike2.setImageBitmap(BitmapFactory.decodeResource(getResources(),resourceId));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();

        intent = getIntent();
        isFlag = true;
        setLikeImage(R.drawable.selected_16);

        if(intent.getBooleanExtra("isFirstRound", true)) {

            // CNT, ROUND 초기화
            StaticInfo.CNT = 0; StaticInfo.ROUND = 16;
            // 랜덤수 섞어줌
            Collections.shuffle(StaticInfo.RAN);



            // 섞은 랜덤수를 16강 배열에 넣음.
            for(int i=0; i<StaticInfo.ROUND; i++)
                StaticInfo.ROUND_16[i] = StaticInfo.RAN.get(i);

            /* 상단 Progress 세팅  */
            setTopProgress();

            /* 첫 Image 세팅 */
            StaticInfo.imageList.get(StaticInfo.ROUND_16[0]).into(topImage);
            StaticInfo.imageList.get(StaticInfo.ROUND_16[1]).into(bottomImage);

            /* 다음 Image 세팅 */
            StaticInfo.imageList.get(StaticInfo.ROUND_16[2]).into(topNextImage);
            StaticInfo.imageList.get(StaticInfo.ROUND_16[3]).into(bottomNextImage);


            topText.setText(StaticInfo.foodName[StaticInfo.ROUND_16[0]]);
            bottomText.setText(StaticInfo.foodName[StaticInfo.ROUND_16[1]]);
            topText2.setText(StaticInfo.foodName[StaticInfo.ROUND_16[2]]);
            bottomText2.setText(StaticInfo.foodName[StaticInfo.ROUND_16[3]]);
        }
    }

    @Bind(R.id.ripple_background_image)
    View rippleBackgroundImage;
    @Bind(R.id.round_end_info)
    TextView round_end_info;
    @Bind(R.id.im_selected_menu)
    TextView imSelectMenu;
    @Bind(R.id.round_end_info2)
    TextView roundEndInfo2;

    @Bind(R.id.end_infomation)
    RelativeLayout end_infomation;
    //@OnClick(R.id.end_infomation)
    public void endInfomationClick(View v){
        endPageClick(v);
    }


    private void setTopProgress() {
        /* 상단 Progress 세팅  */
        switch (StaticInfo.ROUND) {
            case 16 :
                oval16.setAlpha(0);     txtView16.setAlpha(1);
                oval8.setAlpha(0.5f);   txtView8.setAlpha(0.5f);
                oval4.setAlpha(0.5f);   txtView4.setAlpha(0.5f);
                oval2.setAlpha(0.5f);   txtView2.setAlpha(0.5f);
                break;
            case 8 :
                oval16.setAlpha(0.5f);  txtView16.setAlpha(0.5f);
                oval8.setAlpha(0);      txtView8.setAlpha(1);
                oval4.setAlpha(0.5f);   txtView4.setAlpha(0.5f);
                oval2.setAlpha(0.5f);   txtView2.setAlpha(0.5f);
                break;
            case 4 :
                oval16.setAlpha(0.5f);  txtView16.setAlpha(0.5f);
                oval8.setAlpha(0.5f);   txtView8.setAlpha(0.5f);
                oval4.setAlpha(0);      txtView4.setAlpha(1);
                oval2.setAlpha(0.5f);   txtView2.setAlpha(0.5f);
                break;
            case 2 :
                oval16.setAlpha(0.5f);  txtView16.setAlpha(0.5f);
                oval8.setAlpha(0.5f);   txtView8.setAlpha(0.5f);
                oval4.setAlpha(0.5f);   txtView4.setAlpha(0.5f);
                oval2.setAlpha(0);      txtView2.setAlpha(1);
                break;
        }
    }



    private void topClicked() {

        switch(StaticInfo.ROUND) {

            case 16 :
                Log.e("Top Food", StaticInfo.ROUND_16[ 2*StaticInfo.CNT ] + " / " +  StaticInfo.foodName[StaticInfo.ROUND_16[ 2*StaticInfo.CNT ]]);
                StaticInfo.ROUND_8[StaticInfo.CNT] =  StaticInfo.ROUND_16[ 2*StaticInfo.CNT ];
                StaticInfo.CNT++;

                // 16강 라운드 마지막경기 일 경우
                if( StaticInfo.CNT == StaticInfo.ROUND/2 ) {
                    /*FF3030->10진수로하면-16724016*/
                    endRound(new int[]{255, 30, 30}, StaticInfo.ROUND_8);
                    StaticInfo.ROUND/=2; StaticInfo.CNT=0;

                }
                break;

            case 8 :
                Log.e("Top Food", StaticInfo.ROUND_8[2 * StaticInfo.CNT] + " / " + StaticInfo.foodName[StaticInfo.ROUND_8[2 * StaticInfo.CNT]]);
                StaticInfo.ROUND_4[StaticInfo.CNT] =  StaticInfo.ROUND_8[ 2*StaticInfo.CNT ];
                StaticInfo.CNT++;

                // 16강 라운드 마지막경기 일 경우
                if( StaticInfo.CNT == StaticInfo.ROUND/2 ) {
                    endRound(new int[]{0, 174, 54}, StaticInfo.ROUND_4);

                    selectedBottomLike.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.selected_4));
                    selectedBottomLike2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.selected_4));
                    selectedTopLike.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.selected_4));
                    selectedTopLike2.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.selected_4));
                    StaticInfo.ROUND/=2; StaticInfo.CNT=0;
                }
                break;

            case 4 :
                Log.e("Top Food", StaticInfo.ROUND_4[ 2*StaticInfo.CNT ] + " / " +  StaticInfo.foodName[StaticInfo.ROUND_4[ 2*StaticInfo.CNT ]]);
                StaticInfo.ROUND_2[StaticInfo.CNT] =  StaticInfo.ROUND_4[ 2*StaticInfo.CNT ];
                StaticInfo.CNT++;
                setLikeImage(R.drawable.selected_4);
                // 16강 라운드 마지막경기 일 경우
                if( StaticInfo.CNT == StaticInfo.ROUND/2 ) {
                    endRound(new int[]{16, 99, 238}, StaticInfo.ROUND_2);
                    StaticInfo.ROUND/=2; StaticInfo.CNT=0;

                }
                break;

            case 2 :

                StaticInfo.ROUND/=2; StaticInfo.CNT=0;
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
        setTopProgress();
        imageLoad();
    }
    @Bind(R.id.img_bg_bottom2)
    View imgBgBottom2;
    @Bind(R.id.img_bg_top2)
    View imgBgTop2;
    @Bind(R.id.center_bottom_text2)
    TextView bottomText2;
    @Bind(R.id.center_top_text2)
    TextView topText2;
    /**
     * 위의 음식 그림 선택 시
     */
    @OnClick(com.nexters.amuguna.gola.R.id.center_top_img)
    void topImgClick() {
        imgClick(0, topImage, bottomImage, bottomText, imgBgBottom,selectedTopLike);
    }
    @OnClick(R.id.center_top_next_img)
    void topNextImgClick() {
        imgClick(0, topNextImage, bottomNextImage, bottomText2, imgBgBottom2,selectedTopLike2);
    }
    /**
     * 하단의 음식 그림 선택 시
     */
    @OnClick(com.nexters.amuguna.gola.R.id.center_bottom_img)
    void bottomImgClick() {
        imgClick(1, bottomImage, topImage, topText, imgBgTop , selectedBottomLike);
    }
    @OnClick(R.id.center_bottom_next_img)
    void bottomNextImgClick() {
        imgClick(1, bottomNextImage, topNextImage, topText2, imgBgTop2 ,selectedBottomLike2);
    }

    private void bottomClicked() {

        switch(StaticInfo.ROUND) {

            case 16 :

                Log.e("Bottom Food", StaticInfo.ROUND_16[ 2*StaticInfo.CNT+1 ] + " / " +  StaticInfo.foodName[StaticInfo.ROUND_16[ 2*StaticInfo.CNT+1 ]]);
                StaticInfo.ROUND_8[StaticInfo.CNT] =  StaticInfo.ROUND_16[ 2*StaticInfo.CNT+1 ];
                StaticInfo.CNT++;

                // 16강 라운드 마지막경기 일 경우
                if( StaticInfo.CNT == StaticInfo.ROUND/2 ) {
                    endRound(new int[]{255,30,30},StaticInfo.ROUND_8);

                    StaticInfo.ROUND/=2; StaticInfo.CNT=0;
                }

                break;
            case 8 :

                Log.e("Bottom Food", StaticInfo.ROUND_8[ 2*StaticInfo.CNT+1 ] + " / " +  StaticInfo.foodName[StaticInfo.ROUND_8[ 2*StaticInfo.CNT+1 ]]);
                StaticInfo.ROUND_4[StaticInfo.CNT] =  StaticInfo.ROUND_8[ 2*StaticInfo.CNT+1 ];
                StaticInfo.CNT++;
                // 16강 라운드 마지막경기 일 경우
                if( StaticInfo.CNT == StaticInfo.ROUND/2 ) {
                    endRound(new int[]{0,174,54},StaticInfo.ROUND_4);
                    StaticInfo.ROUND/=2; StaticInfo.CNT=0;
                }

                break;
            case 4 :

                Log.e("Bottom Food", StaticInfo.ROUND_4[ 2*StaticInfo.CNT+1 ] + " / " +  StaticInfo.foodName[StaticInfo.ROUND_4[ 2*StaticInfo.CNT+1 ]]);
                StaticInfo.ROUND_2[StaticInfo.CNT] =  StaticInfo.ROUND_4[ 2*StaticInfo.CNT+1 ];
                StaticInfo.CNT++;

                // 16강 라운드 마지막경기 일 경우
                if( StaticInfo.CNT == StaticInfo.ROUND/2 ) {

                    endRound(new int[]{16,99,238},StaticInfo.ROUND_2);
                    StaticInfo.ROUND/=2; StaticInfo.CNT=0;

                }

                break;
            case 2 :

                StaticInfo.ROUND/=2; StaticInfo.CNT=0;
                //StaticInfo.ROUND_16[ 2*StaticInfo.CNT+1 ];
                Log.e("IsLastGame", "True");

                intent = new Intent(TournamentActivity.this,ResultActivity.class);
                intent.putExtra("result", StaticInfo.ROUND_2[1] );
                intent.putExtra("isTournament", true);
                intent.putExtra("isFirstRound", false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return;
        }
        // Image Load
        setTopProgress();
        imageLoad();
    }

    private void imgClick(final int which, final ImageView clickedImage, final ImageView unClickImage ,final TextView bottomTopText,final View imgBg, final ImageView selectedLike) {

        Log.e("Top", StaticInfo.ROUND + "강 : " + (StaticInfo.CNT + 1) + "경기 : ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            selectedLike.setVisibility(View.VISIBLE);
                            clickedImage.setEnabled(false);
                            unClickImage.setEnabled(false);


                            switch (StaticInfo.ROUND) {
                                case 16 :
                                    //unClickImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.non_selected));
                                    unClickImage.setAlpha(0.6f);

                                    break;
                                case 8 :
                                    //unClickImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.non_selected));
                                    unClickImage.setAlpha(0.6f);
                                    break;
                                case 4 :
                                    //unClickImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.non_selected));
                                    unClickImage.setAlpha(0.6f);
                                    break;
                                case 2 :
                                    //unClickImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.non_selected));
                                    unClickImage.setAlpha(0.6f);
                                    break;

                            }

                            bottomTopText.setAlpha(0.5f);
                            imgBg.setVisibility(View.INVISIBLE);
                        }
                    });
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            selectedLike.setVisibility(View.GONE);
                            unClickImage.setAlpha(1.0f);
                            bottomTopText.setAlpha(1.0f);

                            if(which==0)
                                topClicked();
                            else
                                bottomClicked();

                            clickedImage.setEnabled(true);
                            unClickImage.setEnabled(true);
                            if(isFlag) {
                                imgBgTop.setVisibility(View.VISIBLE);
                                imgBgBottom.setVisibility(View.VISIBLE);
                            } else {
                                imgBgTop2.setVisibility(View.VISIBLE);
                                imgBgBottom2.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

    }
    @Bind(R.id.firstLinear)
    LinearLayout firstLinear;
    @Bind(R.id.secondLinear)
    LinearLayout secondLinear;
    /* 대섭 */
    private void imageLoad() {

            if(isFlag) {
                /*topImage.setVisibility(View.GONE);
                bottomImage.setVisibility(View.GONE);
                topNextImage.setVisibility(View.VISIBLE);
                bottomNextImage.setVisibility(View.VISIBLE);*/
                firstLinear.setVisibility(View.GONE);
                secondLinear.setVisibility(View.VISIBLE);

                nextImgLoad(topImage, bottomImage,topText2,bottomText2);


            } else {

                /*topNextImage.setVisibility(View.GONE);
                bottomNextImage.setVisibility(View.GONE);
                topImage.setVisibility(View.VISIBLE);
                bottomImage.setVisibility(View.VISIBLE);*/
                firstLinear.setVisibility(View.VISIBLE);
                secondLinear.setVisibility(View.GONE);
                nextImgLoad(topNextImage, bottomNextImage, topText, bottomText);


            }
            isFlag =!isFlag;


    }

    private void nextImgLoad(final ImageView topNextImage_, final ImageView bottomNextImage_, TextView topText, TextView bottomText) {

        switch(StaticInfo.ROUND) {


            case 16 :
                if(StaticInfo.CNT != StaticInfo.ROUND/2-1) {
                    StaticInfo.imageList.get(StaticInfo.ROUND_16[ 2*StaticInfo.CNT+2 ]).into(topNextImage_);
                    StaticInfo.imageList.get(StaticInfo.ROUND_16[ 2*StaticInfo.CNT+3 ]).into(bottomNextImage_);

                } else {
                    StaticInfo.imageList.get(StaticInfo.ROUND_8[0]).into(topNextImage_);
                    StaticInfo.imageList.get(StaticInfo.ROUND_8[1]).into(bottomNextImage_);
                }

                topText.setText(StaticInfo.foodName[StaticInfo.ROUND_16[ 2*StaticInfo.CNT ]]);
                bottomText.setText(StaticInfo.foodName[StaticInfo.ROUND_16[ 2*StaticInfo.CNT+1 ]]);
                break;

            case 8 :
                if(StaticInfo.CNT != StaticInfo.ROUND/2-1) {
                    StaticInfo.imageList.get(StaticInfo.ROUND_8[ 2*StaticInfo.CNT+2 ]).into(topNextImage_);
                    StaticInfo.imageList.get(StaticInfo.ROUND_8[ 2*StaticInfo.CNT+3 ]).into(bottomNextImage_);

                } else {
                    StaticInfo.imageList.get(StaticInfo.ROUND_4[0]).into(topNextImage_);
                    StaticInfo.imageList.get(StaticInfo.ROUND_4[1]).into(bottomNextImage_);
                }

                topText.setText(StaticInfo.foodName[StaticInfo.ROUND_8[ 2*StaticInfo.CNT ]]);
                bottomText.setText(StaticInfo.foodName[StaticInfo.ROUND_8[ 2*StaticInfo.CNT+1 ]]);
                break;

            case 4 :
                if(StaticInfo.CNT != StaticInfo.ROUND/2-1) {
                    StaticInfo.imageList.get(StaticInfo.ROUND_4[ 2*StaticInfo.CNT+2 ]).into(topNextImage_);
                    StaticInfo.imageList.get(StaticInfo.ROUND_4[ 2*StaticInfo.CNT+3 ]).into(bottomNextImage_);

                } else {
                    StaticInfo.imageList.get(StaticInfo.ROUND_2[0]).into(topNextImage_);
                    StaticInfo.imageList.get(StaticInfo.ROUND_2[1]).into(bottomNextImage_);
                }


                topText.setText(StaticInfo.foodName[StaticInfo.ROUND_4[ 2*StaticInfo.CNT ]]);
                bottomText.setText(StaticInfo.foodName[StaticInfo.ROUND_4[ 2*StaticInfo.CNT+1 ]]);
                break;

            case 2 :
                StaticInfo.imageList.get(StaticInfo.ROUND_2[ 2*StaticInfo.CNT+1 ]).into(bottomImage);

                topText.setText(StaticInfo.foodName[StaticInfo.ROUND_2[ 2*StaticInfo.CNT ]]);
                bottomText.setText(StaticInfo.foodName[StaticInfo.ROUND_2[ 2*StaticInfo.CNT+1 ]]);
                break;
        }


    }

    private void endRound(int []color ,int []selectFood){

        bottomText.setVisibility(View.GONE);
        bottomImage.setVisibility(View.GONE);
        topImage.setVisibility(View.GONE);
        switch(StaticInfo.ROUND) {
            case 16 : setLikeImage(R.drawable.selected_8);break;
            case 8 : setLikeImage(R.drawable.selected_4);break;
            case 4 : setLikeImage(R.drawable.selected_2);break;

        }


        round_end_info.setText("\n" + StaticInfo.ROUND + "강 종료.");
        if(StaticInfo.ROUND!=4)
            roundEndInfo2.setText("터치하면 " + (StaticInfo.ROUND / 2) + "강 시작!");
        else
            roundEndInfo2.setText("터치하면 결승 시작!");
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<selectFood.length;i++)
            if(i%4!=0)
                stringBuilder.append(" \t"+StaticInfo.foodName[selectFood[i]]);
            else
                stringBuilder.append("\n\t"+StaticInfo.foodName[selectFood[i]]);
        imSelectMenu.setText(stringBuilder.toString());
        end_infomation.setVisibility(View.VISIBLE);
        ripple.startRippleAnimation();

        rippleBackgroundImage.setBackgroundColor(Color.rgb(color[0],color[1],color[2]));

    }

    @OnClick(R.id.ripple)
    public void rippleClick(View v) {
        endPageClick(v);
    }
    public void endPageClick(View v){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            end_infomation.setVisibility(View.GONE);
                            ripple.stopRippleAnimation();

                            bottomText.setVisibility(View.VISIBLE);
                            bottomImage.setVisibility(View.VISIBLE);
                            topImage.setVisibility(View.VISIBLE);
                            bottomImage.setEnabled(true);
                            topImage.setEnabled(true);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
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