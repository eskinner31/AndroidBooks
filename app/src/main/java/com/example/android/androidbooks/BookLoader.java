package com.example.android.androidbooks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Skinner on 12/24/16.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private static final String TAG = BookLoader.class.getName();

    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {

    }
}
