package com.nexters.amuguna.golla;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;

/**
 * Created by Daesub Kim on 2016-07-09.
 */
public class GollaMainActivity extends AppCompatActivity {

    @Bind(R.id.tournamentBtn)
    FButton tourBtn;

    @Bind(R.id.randomBtn)
    FButton randomBtn;

    @Bind(R.id.main_top_img)
    ImageView mainTopImg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_golla_main);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.fleax_main).into(mainTopImg);

        /* Hide ActionBar */
        getSupportActionBar().hide();
    }

    @OnClick(R.id.tournamentBtn)
    void tournamentBtnClick() {
        /* Move to MainActivity. */
        Intent intent = new Intent(GollaMainActivity.this,MainActivity.class);
        startActivity(intent);
    }

    //@OnClick(R.id.randomBtn)
    /*void randomBtnClick() {
        *//* Move to MainActivity. *//*
        Intent intent = new Intent(GollaMainActivity.this,TestActivity.class);
        startActivity(intent);
    }*/

}
