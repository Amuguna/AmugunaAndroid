package com.nexters.amuguna.gola;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Daesub Kim on 2016-08-05.
 */
public class Intro2Activity extends AppCompatActivity {

    @Bind(R.id.start_linear)
    ViewGroup startBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_2);
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();

    }

    @OnClick(R.id.start_linear)
    void startBtnClick() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstRun", false);
        editor.commit();

        Intent intent = new Intent(Intro2Activity.this,GolaMainActivity.class);
        startActivity(intent);
        finish();
    }
}
