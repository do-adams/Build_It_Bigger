package com.damian.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.damian.builditbigger.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<EndpointsAsyncTask.GCECallback, Void, String> {
    private static String TAG = EndpointsAsyncTask.class.getSimpleName();
    private GCECallback mCallbackClass;

    public interface GCECallback {
        void onPostExecute(String result);
    }

    @Override
    protected String doInBackground(GCECallback... params) {
        mCallbackClass = params[0];

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

        MyApi myApiService = builder.build();

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.e(TAG, "Error while trying to get GCE API service response");
            return "Error: could not connect to the GCE server. Are you running the backend?"
            + " Are you on a test device?";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        mCallbackClass.onPostExecute(result);
    }
}
