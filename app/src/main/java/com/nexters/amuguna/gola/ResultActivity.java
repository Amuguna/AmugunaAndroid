package com.nexters.amuguna.gola;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nexters.amuguna.gola.manager.GolaImageManager;

import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Daesub Kim on 2016-07-15.
 */
public class ResultActivity extends AppCompatActivity {

    Intent intent;

    @Bind(com.nexters.amuguna.gola.R.id.retry_top_linear)
    ViewGroup retryTopLinear;

    @Bind(com.nexters.amuguna.gola.R.id.go_home_top_linear)
    ViewGroup goHomeTopLinear;

    @Bind(com.nexters.amuguna.gola.R.id.result_img)
    ImageView resultImg;

    @Bind(R.id.result_top_text)
    TextView resultTopText;

    @Bind(R.id.result_text)
    TextView resultText;

    @Bind(com.nexters.amuguna.gola.R.id.result_again)
    ImageView againBottomImg;

    @Bind(com.nexters.amuguna.gola.R.id.result_cross)
    ImageView crossBottomImg;

    //@Bind(com.nexters.amuguna.gola.R.id.retryBtn)
    FButton retryBtn;

    //@Bind(com.nexters.amuguna.gola.R.id.goHomeBtn)
    FButton goHomeBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.nexters.amuguna.gola.R.layout.activity_result);
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();

        //Glide.with(this).load(com.nexters.amuguna.gola.R.drawable.fleax_main).into(resultImg);

        /* 토너먼트에서 넘어왔는지 랜덤 선택에서 넘어왔는지 확인 */
        intent = getIntent();
        if(intent.getBooleanExtra("isTournament", true)){

            /*Glide.with(this).load(StaticInfo.RAN.get(intent.getIntExtra("result",1)-1))
                    .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(),20,0)).into(resultImg);*/

            StaticInfo.imageList.get(intent.getIntExtra("result",0)).into(resultImg);
            resultTopText.setText("토너먼트 결과");
            resultText.setText(StaticInfo.foodName[intent.getIntExtra("result",0)]);

            /*Glide.with(this).load(StaticInfo.resourceList.get(intent.getIntExtra("result",1)-1))
                    .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(),20,0)).into(resultImg);*/

            crossBottomImg.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.result_random_btn));

            Log.e("isTournament ?", "TOURNAMENT !!!! ");
            Log.e("RESULT", ""+intent.getIntExtra("result",1));

        } else {

            //Glide.with(this).load(R.drawable.gola_img_dupbab).into(resultImg);
            /*Glide.with(this).load(R.drawable.gola_img_dupbab)
                    .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(),20,0)).into(resultImg);*/

            int ran = (int) (Math.random()*GolaImageManager.food.length);
            StaticInfo.imageList.get( ran ).into(resultImg);
            resultTopText.setText("랜덤 선택 결과");
            resultText.setText(StaticInfo.foodName[ran]);

            //StaticInfo.imageList.get(StaticInfo.RAN.get(0)).into(resultImg);



            /*Glide.with(this).load( StaticInfo.resourceList.get(StaticInfo.RAN.get(0)-1))
                    .bitmapTransform(new RoundedCornersTransformation(getApplicationContext(),20,0)).into(resultImg);*/

            crossBottomImg.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.result_tournament_btn));

            Log.e("isTournament ?", "NO !!!! ");
            Log.e("RESULT", ""+StaticInfo.foodName[StaticInfo.RAN.get(0)]);

        }
    }



    @OnClick(R.id.go_home_top_linear)
    void goHomeBtnClick() {
        Intent intent = new Intent(ResultActivity.this,GolaMainActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick({com.nexters.amuguna.gola.R.id.result_again, com.nexters.amuguna.gola.R.id.retry_top_linear})
    void againBtnClick() {

        if(intent.getBooleanExtra("isTournament", true)){
            /* Move to TournamentActivity. */
            Intent intent = new Intent(ResultActivity.this,TournamentActivity.class);
            intent.putExtra("isTournament", true);
            intent.putExtra("isFirstRound", true);
            intent.putExtra("round", StaticInfo.ROUND);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            /* Move to ResultActivity. */
            Intent intent = new Intent(ResultActivity.this,ResultActivity.class);
            intent.putExtra("isTournament", false);
            startActivity(intent);
            finish();
        }

    }

    @OnClick(R.id.result_cross)
    void crossBtnClick() {

        if(intent.getBooleanExtra("isTournament", true)){
            /* Move to ResultActivity. */
            Intent intent = new Intent(ResultActivity.this,ResultActivity.class);
            intent.putExtra("isTournament", false);
            startActivity(intent);
            finish();
        } else {
            /* Move to TournamentActivity. */
            Intent intent = new Intent(ResultActivity.this,TournamentActivity.class);
            intent.putExtra("isTournament", true);
            intent.putExtra("isFirstRound", true);
            intent.putExtra("round", StaticInfo.ROUND);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

}
