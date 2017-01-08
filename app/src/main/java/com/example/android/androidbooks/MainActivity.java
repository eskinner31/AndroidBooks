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
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private ProgressBar mProgressBar;
    private TextView mPromptText;


    private BookArrayAdapter mBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this; //Needed to maintain context in method
        mSubmitButtonView = (Button) findViewById(R.id.submit_search_button);
        mUserInputView = (EditText) findViewById(R.id.user_input);
        mBookListView = (ListView) findViewById(R.id.book_list);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_indicator);
        mPromptText = (TextView) findViewById(R.id.empty_list_text_view);

        mProgressBar.setVisibility(View.GONE);
        mPromptText.setVisibility(View.VISIBLE);
        mBookListView.setVisibility(View.GONE);

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

        mPromptText.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);

        Uri baseUri = Uri.parse(GOOGLE_BOOKS_BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("q", mUserInputView.getText().toString());

        return new BookLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> bookData) {
        mProgressBar.setVisibility(View.GONE);
        mBookListView.setVisibility(View.VISIBLE);
        mBookAdapter.clear();
        mUserInputView.setText("");
        
        if(bookData != null && !bookData.isEmpty()) {
            mBookAdapter.addAll(bookData);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mBookAdapter.clear();
    }
}
