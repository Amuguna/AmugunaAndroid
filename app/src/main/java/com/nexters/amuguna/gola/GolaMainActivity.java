package com.nexters.amuguna.gola;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;

/**
 * Created by Daesub Kim on 2016-07-09.
 */
public class GolaMainActivity extends AppCompatActivity {

    SharedPreferences prefs;
    final String firstRunPrefs = "firstRun";

    @Bind(com.nexters.amuguna.gola.R.id.tournamentBtn)
    ImageView tourBtn;

    @Bind(com.nexters.amuguna.gola.R.id.randomBtn)
    ImageView randomBtn;

    @Bind(R.id.back_to_tutorial_linear)
    ViewGroup backToTutorialLinear;

    @Bind(R.id.menu_select_linear)
    LinearLayout menuSelectLinear;

    //@Bind(com.nexters.amuguna.gola.R.id.main_top_img)
    ImageView mainTopImg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.nexters.amuguna.gola.R.layout.activity_gola_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.intro2_main_color));
        }
        ButterKnife.bind(this);

         /* Check the First Run */
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstRun = prefs.getBoolean(firstRunPrefs, true);
        if(firstRun) {
            /* Is the First Run */
            /* display Coach Mark */
            onCoachMark();
        }

        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int displayWidth = display.getWidth();
        displayWidth *=0.12;
        /* Hide ActionBar */
        getSupportActionBar().hide();
        menuSelectLinear.setPadding(displayWidth,0,displayWidth,0);

        //Glide.with(this).load(com.nexters.amuguna.gola.R.drawable.fleax_main).into(mainTopImg);

        //GolaImageManager.getInstatnce().initImages();


    }

    /* Display the Coach Mark */
    private void onCoachMark() {
        Log.d("isOnCoachMark ? ", "Is CoachMark !");

        Intent intent = new Intent(GolaMainActivity.this,Intro1Activity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(com.nexters.amuguna.gola.R.id.tournamentBtn)
    void tournamentBtnClick() {
        /* Move to MainActivity. */
        Intent intent = new Intent(GolaMainActivity.this,TournamentActivity.class);

        //Intent intent = new Intent(GolaMainActivity.this,TestActivity.class);

        intent.putExtra("isTournament", true);
        intent.putExtra("isFirstRound", true);
        intent.putExtra("round", StaticInfo.ROUND);
        startActivity(intent);
    }

    @OnClick(com.nexters.amuguna.gola.R.id.randomBtn)
    void randomBtnClick() {
        /* Move to ResultActivity. */
        Intent intent = new Intent(GolaMainActivity.this,ResultActivity.class);

        intent.putExtra("isTournament", false);
        startActivity(intent);
    }

    @OnClick(R.id.back_to_tutorial_linear)
    void backToTutorial() {
        Intent intent = new Intent(GolaMainActivity.this,Intro1Activity.class);
        startActivity(intent);
        finish();
    }
}
