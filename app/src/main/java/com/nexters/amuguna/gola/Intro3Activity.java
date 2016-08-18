package com.nexters.amuguna.gola;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Daesub Kim on 2016-08-18.
 */
public class Intro3Activity extends AppCompatActivity {

    @Bind(R.id.go_to_gola_main_linear)
    ViewGroup goToMainBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_3);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.intro3_color));
        }
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();
    }

    @OnClick(R.id.go_to_gola_main_linear)
    void startBtnClick() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstRun", false);
        editor.commit();

        Intent intent = new Intent(Intro3Activity.this,GolaMainActivity.class);
        startActivity(intent);
        finish();
    }

}
