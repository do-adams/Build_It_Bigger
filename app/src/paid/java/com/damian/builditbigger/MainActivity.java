/*
 * Copyright (C) 2016 Damián Adams
 */

package com.damian.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.damian.freshjokes.FreshJoke;
import com.damian.jokedisplay.JokeActivity;

public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.GCECallback {
    private static String TAG = MainActivity.class.getSimpleName();
    private static String TEST_DEVICE_ID_AD_MOB = "7FDA1E82084FB2DA81E38E52B97C7981";

    // If you are running this app on an emulator you will have access to
    // GCE functionality. Set this flag to true and
    // run the 'backend' Gradle configuration before running this app.
    // If you are going to run this app on a device set this flag to false
    // and run the app normally since it will default to a local Java library
    // for the same task.
    private static boolean IS_EMULATOR = true;

    TextView mJokeTextView;
    Button mJokeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mJokeTextView = (TextView) findViewById(R.id.jokeTextView);

        mJokeButton = (Button) findViewById(R.id.jokeButton);
        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IS_EMULATOR && isNetworkAvailable()) { // because we can't afford Google App Engine ':(
                    new EndpointsAsyncTask().execute(MainActivity.this);
                } else { // grabs the joke locally instead
                    startJokeActivity(new FreshJoke().chuckNorris());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mJokeTextView.setText(new FreshJoke().chuckNorris()); // kill them with jokes!
    }

    @Override
    public void onPostExecute(String result) {
        startJokeActivity(result);
    }


    /**
     * Sends the joke through to the JokeActivity of the jokedisplay lib.
     */
    private void startJokeActivity(String joke) {
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY, joke);
        startActivity(intent);
    }

    /**
     * Checks to ensure the user can connect to the network,
     * as per Google's Android Developer guidelines.
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        //This method requires permission ACCESS_NETWORK_STATE
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            //Checks if a network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }
}
