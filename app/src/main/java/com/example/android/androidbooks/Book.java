package com.example.android.androidbooks;

/**
 * Created by Skinner on 12/24/16.
 */

public class Book {
    private String mTitle;
    private String mAuthor;
    private String mRating;
    private String mImage;

    public Book(String title, String author, String rating, String imageUrl) {
        mTitle = title;
        mAuthor = author;
        mRating = rating;
        mImage = imageUrl;
    }

    public String getmImage() {

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
