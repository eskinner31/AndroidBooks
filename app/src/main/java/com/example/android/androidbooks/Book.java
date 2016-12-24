package com.example.android.androidbooks;

import android.media.Image;

/**
 * Created by Skinner on 12/24/16.
 */

public class Book {
    private String mTitle;
    private String mAuthor;
    private String mDescription;
    private Image mImage;

    public Book(String title, String author, String description, Image image) {
        mTitle = title;
        mAuthor = author;
        mDescription = description;
        mImage = image;
    }

    public Image getmImage() {
        return mImage;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmAuthor() {
        return mAuthor;
    }

    public String getmTitle() {
        return mTitle;
    }
}
