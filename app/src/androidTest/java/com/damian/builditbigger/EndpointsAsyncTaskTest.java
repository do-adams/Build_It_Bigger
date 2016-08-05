/*
 * Copyright (C) 2016 Damián Adams
 */

package com.damian.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * Test class responsible for testing the EndpointsAsyncTask class.
 */
public class EndpointsAsyncTaskTest extends ApplicationTestCase<Application>
        implements EndpointsAsyncTask.GCECallback {
    private boolean mGotResponse;
    private String mResponse;

    public EndpointsAsyncTaskTest() {
        super(Application.class);
    }

    @Override
    public void onPostExecute(String result) {
        mResponse = result;
        mGotResponse = true;
    }

    /**
     * Unit test for testing the GCE string response.
     * The endpoints async task must not return a null string
     * or a string containing the word "Error".
     *
     * Before running this test make sure you are running the backend
     * GCE configuration (server).
     *
     * You must run this test on the emulator, otherwise it will fail
     * on a test device (unless you deploy your GCE server to the cloud or
     * get your local network set up with the backend properly).
     */
    public void testGoogleEndpointsResponse() {
        new EndpointsAsyncTask().execute(this);
        while (!mGotResponse) {} // wait for the background thread to fetch the response
        assertTrue(mResponse != null);
        assertTrue(!mResponse.contains("Error"));
    }
}