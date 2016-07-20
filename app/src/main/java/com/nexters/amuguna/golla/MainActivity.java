package com.nexters.amuguna.golla;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexters.amuguna.golla.manager.TournametManager;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity {

    /*boolean visible=true;*/
    SharedPreferences prefs;
    Intent intent;
    final String firstRoundPrefs = "firstRound";

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

        intent = getIntent();

        if(intent.getBooleanExtra("isFirstRound", true)) {
            TournametManager.getInstance().initTree(intent.getIntExtra("round", 8));
        }
    }

    private int nextRound() {
        return TournametManager.getInstance().nextRound();
    }

    @OnClick(R.id.center_top_img)
    void topImgClick() {

        /*TransitionManager.beginDelayedTransition(viewGroup);
        visible = !visible;
        bottomImage.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);*/


        TransitionManager.beginDelayedTransition(viewGroup);
        bottomImage.setVisibility(View.INVISIBLE);

        switch (nextRound()) {
            case -1 :

                /* Test */
                Log.e("Depth / Count", TournametManager.getInstance().getDepth() + " / " + TournametManager.getInstance().getCount());
                /* Move to ResultActivity. */
                intent = new Intent(MainActivity.this,ResultActivity.class);
                intent.putExtra("isTournament", true);
                intent.putExtra("isFirstRound", false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case 0 :
                /* Test */
                Log.e("Depth / Count", TournametManager.getInstance().getDepth() + " / " + TournametManager.getInstance().getCount());
                intent = new Intent(MainActivity.this,MainActivity.class);
                //intent.putExtra("isTournament", true);
                intent.putExtra("isFirstRound", false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case 1 :
                /* Test */
                Log.e("Depth / Count", TournametManager.getInstance().getDepth() + " / " + TournametManager.getInstance().getCount());
                intent = new Intent(MainActivity.this,MainActivity.class);
                //intent.putExtra("isTournament", true);
                intent.putExtra("isFirstRound", false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }


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
