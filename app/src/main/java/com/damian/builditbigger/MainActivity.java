package com.damian.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.damian.builditbigger.backend.myApi.MyApi;
import com.damian.freshjokes.FreshJoke;
import com.damian.jokedisplay.JokeActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();
    private static String TEST_DEVICE_ID_AD_MOB = "7FDA1E82084FB2DA81E38E52B97C7981";

    // If you are running this app on an emulator you will have access to
    // GCE functionality. Set this flag to true and
    // run the 'backend' Gradle configuration before running this app.
    // If you are going to run this app on a device set this flag to false
    // and run the app normally since it will default to a local Java library
    // for the same task.
    private static boolean IS_EMULATOR = false;

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

    private void startJokeActivity(String joke) {
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY, joke);
        startActivity(intent);
    }

    class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
        private String TAG = getClass().getSimpleName();
        private MyApi myApiService = null;
        private Context context;

        @Override
        protected String doInBackground(Context... params) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();

            context = params[0];

            try {
                return myApiService.getJoke().execute().getData();
            } catch (IOException e) {
                Log.e(TAG, "Error while trying to get GCE API service response");
                return new FreshJoke().chuckNorris(); // retrieve the joke locally in case of network failure
            }
        }

        @Override
        protected void onPostExecute(String result) {
            startJokeActivity(result);
        }
    }

}
