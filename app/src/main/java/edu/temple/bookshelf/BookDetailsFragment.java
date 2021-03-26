package edu.temple.bookshelf;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookDetailsFragment extends Fragment {

    Book book;
    TextView textViewTitle;
    TextView textViewAuthor;

    public BookDetailsFragment() {
        // Required empty public constructor
    }

    public static BookDetailsFragment newInstance(Book book) {

        BookDetailsFragment newFragment = new BookDetailsFragment();

        Bundle newBundle = new Bundle();

        String title = book.getTitle();
        String author = book.getAuthor();
        newBundle.putString("TITLE", title);
        newBundle.putString("AUTHOR", author);

        newFragment.setArguments(newBundle);

        return newFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle myBundle = getArguments();

        if (myBundle != null) {
            String title = myBundle.getString("TITLE");
            String author = myBundle.getString("AUTHOR");
            book = new Book(title, author);
        } else {
            book = null;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_book_details, container, false);

        textViewTitle = layout.findViewById(R.id.textViewTitle);
        textViewAuthor = layout.findViewById(R.id.textViewAuthor);

        if (book != null) {
            displayBook(book);
        }

        return layout;
    }

    public void displayBook(Book book) {

        textViewTitle.setText(book.getTitle());
        textViewTitle.setTextSize(40);

        textViewAuthor.setText(book.getAuthor());
        textViewAuthor.setTextSize(30);

    }
}