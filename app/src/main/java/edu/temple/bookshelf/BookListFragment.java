package edu.temple.bookshelf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BookListFragment extends Fragment {

    BookList bookList;

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookList = new BookList();

        String[] titlesAndAuthors = getResources().getStringArray(R.array.books);
        for (int i = 0; i <= 18; i += 2) {
            Book book = new Book(titlesAndAuthors[i], titlesAndAuthors[i+1]);
            bookList.add(book);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_book_list, container, false);

        ListView BookListView = (ListView) layout.findViewById(R.id.BookListView);

        ListAdapter adapter = new ListAdapter(layout.getContext(), bookList);

        BookListView.setAdapter(adapter);

        return layout;
    }

}