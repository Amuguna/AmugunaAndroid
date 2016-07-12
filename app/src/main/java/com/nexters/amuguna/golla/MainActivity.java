package com.nexters.amuguna.golla;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity {

    //boolean visible=true;

    @Bind(R.id.center_top_img)
    ImageView topImage;

    @Bind(R.id.center_bottom_img)
    ImageView bottomImage;

    @Bind(R.id.center_layout)
    ViewGroup viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();

    }

    @OnClick(R.id.center_top_img)
    void topImgClick() {

        /*TransitionManager.beginDelayedTransition(viewGroup);
        visible = !visible;
        bottomImage.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);*/


        TransitionManager.beginDelayedTransition(viewGroup);
        bottomImage.setVisibility(View.INVISIBLE);


        //bottomImage.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.center_bottom_img)
    void bottomImgClick() {

        /*TransitionManager.beginDelayedTransition(viewGroup);
        visible = !visible;
        topImage.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);*/


        TransitionManager.beginDelayedTransition(viewGroup);
        topImage.setVisibility(View.INVISIBLE);


        //topImage.setVisibility(View.INVISIBLE);
    }
}
