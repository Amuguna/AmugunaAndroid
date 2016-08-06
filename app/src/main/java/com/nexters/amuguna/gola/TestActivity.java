package com.nexters.amuguna.gola;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Daesub Kim on 2016-08-06.
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);
        ButterKnife.bind(this);

        /* Hide ActionBar */
        getSupportActionBar().hide();

    }
}
