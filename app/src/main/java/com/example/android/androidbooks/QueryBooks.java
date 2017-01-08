package com.example.android.androidbooks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            response = bookHttpRequest(url);
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

    private static String bookHttpRequest(URL url) throws IOException {
        String response = "";

        if (url == null) {
            return response;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            //casting the passed in url to a urlconnection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {

                //if successful retrieve the stream aka the response
                inputStream = urlConnection.getInputStream();

                /**
                 * use a stream reader aka string builder to put together our data into
                 * parseable json;
                 */
                response = streamReader(inputStream);

            } else {
                Log.e(TAG, "Error Response Code" + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(TAG, "Issues retrieving the json", e);
        }finally{
            if (urlConnection != null) {
                urlConnection.disconnect(); //CLOSE YOUR CONNECTION
            }
            if (inputStream != null) {
                inputStream.close(); //CLOSE YOUR STREAM
            }
        }
        return response;
    }

    private static String streamReader(InputStream inputStream) throws IOException {
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


    private static List<Book> parseJsonFeatures(String bookResults) {

        if(TextUtils.isEmpty(bookResults)) {
            return Collections.EMPTY_LIST;  //Empty list rather than returning null;
        }

        List<Book> books = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(bookResults);
            JSONArray booksArray = jsonResponse.getJSONArray("items");

            for (int i = 0; i< booksArray.length(); i++) {
                JSONObject currentBook = booksArray.getJSONObject(i);

                //parse title
                JSONObject currentVolumeInfo = currentBook.getJSONObject("volumeInfo");
                String currentTitle = currentVolumeInfo.getString("title");

                //parse author
                JSONArray authorsArray = currentVolumeInfo.getJSONArray("authors");
                String currentPrimaryAuthor = authorsArray.getString(0);

                //parse rating -> need to check for value since it does not always return
                String currentRating = null;
                if (currentVolumeInfo.has("averageRating")) {
                    currentRating = currentVolumeInfo.getString("averageRating");
                }

                //parse image -> Need to research caching the returned image
                JSONObject imageObject = currentVolumeInfo.getJSONObject("imageLinks");
                String currentImageUrl = imageObject.getString("thumbnail");
                Bitmap imageBitmap = getBookImage(currentImageUrl);

                Book book;

                if (currentRating != null) {
                    book = new Book(currentTitle, currentPrimaryAuthor, currentRating, imageBitmap);
                } else {
                    book = new Book(currentTitle, currentPrimaryAuthor, imageBitmap);
                }

                books.add(book);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Issues with Json Parsing", e);
        }

        return books;
    }

    private static Bitmap getBookImage(String imageUrlSource) {
        try {
            URL source = new URL(imageUrlSource);
            HttpURLConnection connection = (HttpURLConnection) source.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap imageBitmap = BitmapFactory.decodeStream(input);
            return imageBitmap;
        } catch (Exception e) {
            Log.d(TAG, "getBookImage: bitmap exception" + e);
        }
        return null;
    }
}
