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
public class Intro2Activity extends AppCompatActivity {

    float pressedX;

    @Bind(R.id.intro2_root)
    View intro2Root;
    @Bind(R.id.intro_2_text_linear)
    View intro2TextLinear;
    @Bind(R.id.intro2_img_linear)
    View intro2ImgLinear;

    @Bind(R.id.start_linear)
    ViewGroup startBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.intro2_main_color));
        }
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();
    }

    @OnTouch({R.id.intro2_root, R.id.intro2_img_linear, R.id.intro_2_text_linear})
    boolean dragLeft(MotionEvent event) {
        float distance = 0;

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pressedX = event.getX();
                Log.e("pressedX", ""+pressedX);
                //break;
                return true;
            case MotionEvent.ACTION_UP:
                distance = pressedX - event.getX();
                Log.e("distance", ""+distance);
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
        if (distance < 0) {
            Log.e("Drag to Right", "True");
            dragToRight();
            return false;
        }
        return true;
    }

    @OnClick(R.id.start_linear)
    void startBtnClick() {
        dragToLeft();
    }

    private void dragToLeft() {
        Log.e("Drag to ?","Left");
        Intent intent = new Intent(getApplicationContext(),Intro3Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
        finish();
        return;
    }

    private void dragToRight() {
        Log.e("Drag to ?","Right");
        Intent intent = new Intent(getApplicationContext(),Intro1Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
        finish();
        return;
    }
}
