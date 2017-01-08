package com.example.android.androidbooks;

import android.graphics.Bitmap;

/**
 * Created by Skinner on 12/24/16.
 */

public class Book {
    private String mTitle;
    private String mAuthor;
    private String mRating;
    private Bitmap mImage;

    public Book(String title, String author, String rating, Bitmap imageBitmap) {
        mTitle = title;
        mAuthor = author;
        mRating = rating;
        mImage = imageBitmap;
    }

    public Book(String title, String author, Bitmap imageBitmap) {
        mTitle = title;
        mAuthor = author;
        mImage = imageBitmap;

    }

    public Bitmap getmImage() {

        //TODO: Can we use this method to return the image from the url?
        return mImage;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmTitle() {
        return mTitle;
    }
}
