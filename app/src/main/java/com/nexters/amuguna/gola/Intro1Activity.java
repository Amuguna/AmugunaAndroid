package com.nexters.amuguna.gola;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Daesub Kim on 2016-08-05.
 */
public class Intro1Activity extends AppCompatActivity {

    @Bind(R.id.go_to_intro2_linear)
    ViewGroup mainBottom;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_1);
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();
    }

    @OnClick(R.id.go_to_intro2_linear)
    void goToIntro2() {
        Intent intent = new Intent(Intro1Activity.this,Intro2Activity.class);
        startActivity(intent);
    }
}
