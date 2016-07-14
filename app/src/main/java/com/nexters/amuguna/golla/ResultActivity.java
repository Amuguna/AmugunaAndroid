package com.nexters.amuguna.golla;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Daesub Kim on 2016-07-15.
 */
public class ResultActivity extends AppCompatActivity {

    @Bind(R.id.result_img)
    ImageView resultImg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();

        Glide.with(this).load(R.drawable.fleax_main).into(resultImg);
    }
}
