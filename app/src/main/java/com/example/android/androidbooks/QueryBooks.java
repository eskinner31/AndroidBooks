package com.example.android.androidbooks;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Skinner on 1/3/17.
 *
 * This class is meant for taking our request and constructing/parsing the HTTP CALL
 */

public final class QueryBooks {

    private static final String TAG = QueryBooks.class.getSimpleName();

    private QueryBooks() {};

    //Set up method to get the list of books
    public static List<Book> getBooks(String request) {
        String response = null;
        URL url = createUrl(request);

        try {
            response = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "There was an issue with your request");
        }

        List<Book> books = parseJsonFeatures(response);

        return books;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Could not build URL due to ", e);
        }
        return url;
    }

    //TODO: Structure Proper HTTP REQUEST TO GOOGLE BOOKS API
    private static String makeHttpRequest(URL url) throws IOException {
        String response = "";

        if (url == null) {
            return response;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {

        } catch {

        }finally{

        }
        return response;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferStreamReader = new BufferedReader(inputStreamReader);
            String line = bufferStreamReader.readLine();
            while(line != null) {
                output.append(line);
                line = bufferStreamReader.readLine();
            }
        }
        return output.toString();
    }


    //TODO: PARSE THE JSON OBJECT
    private static List<Book> parseJsonFeatures(String bookResults) {

    }
}
