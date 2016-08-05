/*
 * Copyright (C) 2016 Damián Adams
 */

package com.damian.jokedisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Activity in charge of displaying a joke, received through a String extra via Intent.
 */
public class JokeActivity extends AppCompatActivity {
    private static String TAG = JokeActivity.class.getSimpleName();
    public static String JOKE_KEY = "joke";

    private TextView mJokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        mJokeTextView = (TextView) findViewById(R.id.jokeTextView);

        Intent intent = getIntent();

        if (intent != null) {
            String joke;
            if (intent.hasExtra(JOKE_KEY)) {
                joke = intent.getStringExtra(JOKE_KEY);
                mJokeTextView.setText(joke);
            } else {
                Log.e(TAG, "Error: received empty intent");
            }
        } else {
            Log.e(TAG, "Error while accessing intent");
        }
    }
}
