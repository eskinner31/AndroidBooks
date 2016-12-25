package com.example.android.androidbooks;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Skinner on 12/24/16.
 */

public final class BookRequest {

    private static final String GOOGLE_BOOKS_VOLUMES_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";

    private BookRequest() {}

    public static List<Book> getVolumesByQuery(String stringUrl) {

    }

    private static URL createUrl(String stringUrl) {

    }

    private static String makeRequest(URL url) throws IOException {

    }

    private static String streamReader(InputStream inputStream) throws IOException {

    }

    private static List<Book> parseJSONResponse(String volumesJSON) {

    }
}
