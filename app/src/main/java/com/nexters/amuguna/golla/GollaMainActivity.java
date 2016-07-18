package com.nexters.amuguna.golla;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
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

    SharedPreferences prefs;
    final String firstRunPrefs = "firstRun";

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

        /* Hide ActionBar */
        getSupportActionBar().hide();

        Glide.with(this).load(R.drawable.fleax_main).into(mainTopImg);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
         /* Check the First Run */
        boolean firstRun = prefs.getBoolean(firstRunPrefs, true);
        /* Is the First Run */
        if(firstRun) {
            /* display Coach Mark */
            onCoachMark();
        }
    }

    /* Display the Coach Mark */
    private void onCoachMark() {

        Log.d("isOnCoachMark ? ", "Is CoachMark !");

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        dialog.setContentView(R.layout.coach_mark);
        dialog.setCanceledOnTouchOutside(true);

        /* Close the Coach Mark */
        View closeView = dialog.findViewById(R.id.coach_mark_close);
        closeView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                dialog.dismiss();

                /* Change First Run prefs to FALSE */
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean(firstRunPrefs, false);
                editor.commit();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.tournamentBtn)
    void tournamentBtnClick() {
        /* Move to MainActivity. */
        Intent intent = new Intent(GollaMainActivity.this,MainActivity.class);
        intent.putExtra("isTournament", true);
        intent.putExtra("isFirstRound", true);
        intent.putExtra("round", 8);
        startActivity(intent);
    }

    @OnClick(R.id.randomBtn)
    void randomBtnClick() {
        /* Move to ResultActivity. */
        Intent intent = new Intent(GollaMainActivity.this,ResultActivity.class);
        intent.putExtra("isTournament", false);
        startActivity(intent);
    }

}
