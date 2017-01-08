package com.example.android.androidbooks;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Skinner on 12/24/16.
 */

public class BookArrayAdapter extends ArrayAdapter<Book> {

    public BookArrayAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        //Set Author Info
        TextView authorTextView = (TextView) listItemView.findViewById(R.id.book_author);
        authorTextView.setText(currentBook.getmAuthor());

        //Set Rating Info
        TextView bookRatingTextView = (TextView) listItemView.findViewById(R.id.book_rating);
        bookRatingTextView.setText(currentBook.getmRating());

        //Set Book Title Info
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.book_title);
        titleTextView.setText(currentBook.getmTitle());

        ImageView bookImageView = (ImageView) listItemView.findViewById(R.id.book_image);
        bookImageView.setImageBitmap(currentBook.getmImage());

        return listItemView;
    }
}
