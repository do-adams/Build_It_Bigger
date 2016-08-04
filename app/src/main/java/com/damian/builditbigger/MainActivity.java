package com.damian.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.damian.freshjokes.FreshJoke;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private static String TEST_DEVICE_ID_AD_MOB = "7FDA1E82084FB2DA81E38E52B97C7981";

    AdView mAdView;
    TextView mJokeTextView;
    Button mJokeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), getString(R.string.ad_mob_app_id));

        mAdView = (AdView) findViewById(R.id.adView);

        // use your own test device id (check the log)
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEVICE_ID_AD_MOB)
                .build();
        mAdView.loadAd(adRequest);

        mJokeTextView = (TextView) findViewById(R.id.jokeTextView);

        mJokeButton = (Button) findViewById(R.id.jokeButton);
        mJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJokeTextView.setText(new FreshJoke().chuckNorris());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mJokeTextView.setText(new FreshJoke().chuckNorris()); // kill them with jokes!
    }
}
