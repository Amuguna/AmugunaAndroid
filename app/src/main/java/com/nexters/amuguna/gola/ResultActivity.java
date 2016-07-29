package com.nexters.amuguna.gola;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nexters.amuguna.gola.manager.GolaImageManager;

import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;

/**
 * Created by Daesub Kim on 2016-07-15.
 */
public class ResultActivity extends AppCompatActivity {

    Intent intent;

    @Bind(com.nexters.amuguna.gola.R.id.result_img)
    ImageView resultImg;

    @Bind(com.nexters.amuguna.gola.R.id.retryBtn)
    FButton retryBtn;

    @Bind(com.nexters.amuguna.gola.R.id.goHomeBtn)
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
            Log.e("isTournament ?", "TOURNAMENT !!!! ");
            Log.e("RESULT", ""+intent.getIntExtra("result",1));
            Glide.with(this).load(getResourceId(intent.getIntExtra("result",1)-1)).into(resultImg);
        } else {

            /* 랜덤 수를 한번 섞어준다 */
            Collections.shuffle(StaticInfo.RAN);

            Log.e("isTournament ?", "NO !!!! ");
            Log.e("RESULT", ""+StaticInfo.RAN.get(0));
            Glide.with(this).load(getResourceId(StaticInfo.RAN.get(0)-1)).into(resultImg);
        }
    }

    private int getResourceId(int imgIndex){
        Log.e("index-", imgIndex+"" );
        int resourceId = getResources().getIdentifier("com.nexters.amuguna.gola:drawable/"+ GolaImageManager.food[imgIndex],null,null);
        Log.e("resourceId", resourceId + "");
        return resourceId;
    }

    @OnClick(com.nexters.amuguna.gola.R.id.retryBtn)
    void retryBtnClick() {

        if(intent.getBooleanExtra("isTournament", true)){
            /* Move to TournamentActivity. */
            Intent intent = new Intent(ResultActivity.this,TournamentActivity.class);
            intent.putExtra("isTournament", true);
            intent.putExtra("isFirstRound", true);
            intent.putExtra("round", StaticInfo.DEFAULT_ROUND);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            /* Move to ResultActivity. */
            Intent intent = new Intent(ResultActivity.this,ResultActivity.class);
            intent.putExtra("isTournament", false);
            startActivity(intent);
        }

    }

    @OnClick(com.nexters.amuguna.gola.R.id.goHomeBtn)
    void goHomeBtnClick() {
        Intent intent = new Intent(ResultActivity.this,GolaMainActivity.class);
        startActivity(intent);
    }

}
