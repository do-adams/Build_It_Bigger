/*
 * Copyright (C) 2016 Damián Adams
 */

package com.damian.freshjokes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Class in charge of providing fresh, random jokes from an attached plaintext file.
 */
public class FreshJoke {

    // Chuck Norris jokes provided by The Internet Chuck Norris Database
    // http://www.icndb.com/the-jokes-2/
    private static int NUM_OF_CHUCK_JOKES = 544;

    // the file path matters depending on the way you load the text file (getClassLoader or getClass)
    private static String PATH_OF_CHUCK_JOKES = "/chuckjokes.txt";

    /**
     * Queries a given plaintext file for a random one-liner.
     */
    private String getJoke(int numOfJokes, String filePath) {
        int rand = new Random().nextInt(numOfJokes);
        String freshJoke = null;

        try {
            InputStream inputStream = this.getClass()
                    .getResourceAsStream(filePath); // Gradle re-directs to resources folder

            if (inputStream == null) {
                System.out.println("Input stream is null");
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            for (int i = 0; i < rand; i++)
                br.readLine();
            freshJoke = br.readLine();
            br.close();

        } catch (IOException io) {
            System.out.println("I/O Error: " + io);
            io.printStackTrace();
        }
        return freshJoke;
    }

    /**
     * Returns a random Chuck Norris one-liner.
     */
    public String chuckNorris() {
        return getJoke(NUM_OF_CHUCK_JOKES, PATH_OF_CHUCK_JOKES);
    }
}
