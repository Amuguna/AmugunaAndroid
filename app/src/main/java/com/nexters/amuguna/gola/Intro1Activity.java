package com.nexters.amuguna.gola;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
 * Created by Daesub Kim on 2016-08-05.
 */
public class Intro1Activity extends AppCompatActivity {

    float pressedX;

    @Bind(R.id.intro1_root)
    View intro1Root;
    @Bind(R.id.intro_1_text_linear)
    View intro1TextLinear;
    @Bind(R.id.intro1_img_linear)
    View intro1ImgLinear;

    @Bind(R.id.go_to_intro2_linear)
    ViewGroup mainBottom;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.intro1_color));
        }
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();
    }

    @OnTouch({R.id.intro1_root, R.id.intro1_img_linear, R.id.intro_1_text_linear})
    boolean dragLeft(MotionEvent event) {
        float distance = 0;

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pressedX = event.getX();
                //break;
                return true;
            case MotionEvent.ACTION_UP:
                distance = pressedX - event.getX();
                if (Math.abs(distance) < 100) {
                    Log.e("Distance not enough", "True");
                    return false;
                }
                break;
        }

        if (distance > 0) {
            Log.e("Drag to Left", "True");
            dragToLeft();
            return false;
        }
        return true;
    }

    @OnClick(R.id.go_to_intro2_linear)
    void goToIntro2() {
        dragToLeft();
    }

    private void dragToLeft() {
        Intent intent = new Intent(Intro1Activity.this,Intro2Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
    }
}
