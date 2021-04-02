package edu.temple.bookshelf;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class BookListFragment extends Fragment {

    private BookList bookList;

    public BookListFragment() {
        // Required empty public constructor
    }

    public static BookListFragment newInstance(BookList booklist) {

        BookListFragment newFragment = new BookListFragment();

        if (booklist.size() > 0) {
            Bundle newBundle = new Bundle();
            newBundle.putParcelable("booklist", booklist);
            newFragment.setArguments(newBundle);
        }

        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookList = new BookList();
        Bundle myBundle = getArguments();

        if (myBundle != null) {
            bookList = myBundle.getParcelable("booklist");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_book_list, container, false);

        ListView BookListView = (ListView) layout.findViewById(R.id.BookListView);

        ListAdapter adapter = new ListAdapter(layout.getContext(), bookList);

        BookListView.setAdapter(adapter);

        BookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((BookListFragmentInterface)getActivity()).fragmentClick(position);
            }

        });

        return layout;
    }

    public void updateList(BookListFragment bookListFragment) {




    }

    interface BookListFragmentInterface {
        void fragmentClick(int position);
    }
}