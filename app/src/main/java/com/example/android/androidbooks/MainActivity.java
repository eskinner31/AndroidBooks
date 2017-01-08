package com.example.android.androidbooks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final String GOOGLE_BOOKS_BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final int BOOK_LOADER_ID = 1;
    private Context mContext;

    private Button mSubmitButtonView;
    private EditText mUserInputView;
    private ListView mBookListView;


    private BookArrayAdapter mBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set the varibles as the views are inflated
        //Cast Views, types need to be explicit
        mContext = MainActivity.this;
        mSubmitButtonView = (Button) findViewById(R.id.submit_search_button);
        mUserInputView = (EditText) findViewById(R.id.user_input);
        mBookListView = (ListView) findViewById(R.id.book_list);

        //No need to cast objects in this case since the types are implied
        mBookAdapter = new BookArrayAdapter(this, new ArrayList<Book>());

        //assign adapters as necessary
        mBookListView.setAdapter(mBookAdapter);



        //assign click listeners



        mSubmitButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiateSearch();
            }
        });
    }


    //TODO: May need to leverage shared preferences

    //TODO: Create a loading screen

    //TODO: Create Blank Screen ON FRESH START

    //TODO: Persist results when orientation changes

    //TODO: Add Polish



    public void initiateSearch() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        LoaderManager loaderManager = getSupportLoaderManager();

        if(networkInfo != null && networkInfo.isConnected()) {
            if(loaderManager.getLoader(BOOK_LOADER_ID) == null) {
                loaderManager.initLoader(BOOK_LOADER_ID, null, this);
            } else {
                loaderManager.restartLoader(BOOK_LOADER_ID, null, this);
            }
        } else {
            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(GOOGLE_BOOKS_BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", mUserInputView.getText().toString());

        return new BookLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> bookData) {

        mBookAdapter.clear();

        if(bookData != null && !bookData.isEmpty()) {
            mBookAdapter.addAll(bookData);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mBookAdapter.clear();
    }
}
