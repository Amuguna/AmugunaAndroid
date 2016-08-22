package com.nexters.amuguna.gola;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by Daesub Kim on 2016-08-18.
 */
public class Intro3Activity extends AppCompatActivity {

    float pressedX;

    @Bind(R.id.intro3_root)
    View intro3Root;
    @Bind(R.id.intro_3_text_linear)
    View intro3TextLinear;
    @Bind(R.id.intro3_img_linear)
    View intro3ImgLinear;

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

    @OnTouch({R.id.intro3_root, R.id.intro3_img_linear, R.id.intro_3_text_linear})
    boolean dragLeft(MotionEvent event) {
        float distance = 0;

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pressedX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                distance = pressedX - event.getX();
                if (Math.abs(distance) < 100) {
                    Log.e("Distance not enough", "True");
                    return false;
                }
                break;
        }

        if (distance < 0) {
            Log.e("Drag to Right", "True");
            dragToRight();
        }
        return true;
    }

    @OnClick(R.id.go_to_gola_main_linear)
    void startBtnClick() {
        dragToLeft();
    }

    private void dragToLeft() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstRun", false);
        editor.commit();

        Intent intent = new Intent(Intro3Activity.this,GolaMainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }

    private void dragToRight() {
        Intent intent = new Intent(Intro3Activity.this,Intro2Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
        finish();
    }

}
