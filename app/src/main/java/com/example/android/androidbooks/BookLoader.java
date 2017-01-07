package com.example.android.androidbooks;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Skinner on 12/24/16.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    /**
     * Why wouldn't we store the baseURL for the Google Api in the Loader or
     * in the QueryBooks class. Wouldn't it be more OOD since the classes that are
     * actually responsible for talking to google know the information that they need
     * rather than the MainActivity class whose major responsibility is for coordinating thes
     * display of the final result?
     */
    private static final String TAG = BookLoader.class.getSimpleName();

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
        if(mUrl == null) {
            return null;
        }

        List<Book> books = QueryBooks.getBooks(mUrl);
        return books;
    }
}
