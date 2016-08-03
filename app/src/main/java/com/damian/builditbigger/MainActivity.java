package com.damian.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(getApplicationContext(), getString(R.string.ad_mob_app_id));

        AdView mAdView = (AdView) findViewById(R.id.adView);

        // use your own test device id (check the log)
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("7FDA1E82084FB2DA81E38E52B97C7981")
                .build();
        mAdView.loadAd(adRequest);
    }

}
