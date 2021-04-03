package edu.temple.bookshelf;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookDetailsFragment extends Fragment {

    Book book;
    TextView textViewTitle;
    TextView textViewAuthor;
    ImageView imageViewCover;

    public BookDetailsFragment() {
    }

    public static BookDetailsFragment newInstance(Book book) {

        BookDetailsFragment newFragment = new BookDetailsFragment();

        Bundle newBundle = new Bundle();
        newBundle.putParcelable("book", book);
        newFragment.setArguments(newBundle);

        return newFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle myBundle = getArguments();

        if (myBundle != null) {
            book = myBundle.getParcelable("book");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout =  inflater.inflate(R.layout.fragment_book_details, container, false);

        textViewTitle = layout.findViewById(R.id.textViewTitle);
        textViewAuthor = layout.findViewById(R.id.textViewAuthor);
        imageViewCover = layout.findViewById(R.id.imageViewCover);

        if (book != null) {
            displayBook(book);
        }

        return layout;
    }

    public void displayBook(Book book) {

        textViewTitle.setText(book.getTitle());

        textViewAuthor.setText(book.getAuthor());

        Picasso.get().load(Uri.parse(book.getCoverURL())).into(imageViewCover);

    }
}