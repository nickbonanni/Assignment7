package edu.temple.bookshelf;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class BookListFragment extends Fragment {

    BookList bookList;

    public BookListFragment() {
        // Required empty public constructor
    }

    public static BookListFragment newInstance(BookList booklist) {

        BookListFragment newFragment = new BookListFragment();

        Bundle newBundle = new Bundle();
        for (int i = 0; i < 10; i++) {
            newBundle.putStringArrayList("book" + i, booklist.getList(i));
        }
        newFragment.setArguments(newBundle);

        return newFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookList = new BookList();
        ArrayList<String> strings = new ArrayList<>(2);

        Bundle myBundle = getArguments();

        if (myBundle != null) {
            for (int i = 0; i < 10; i++) {
                strings = myBundle.getStringArrayList("book" + i);
                Book book = new Book(strings.get(0), strings.get(1));
                bookList.add(book);
            }
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
                ((FragmentInterface)getActivity()).fragmentClick(position);
            }

        });

        return layout;
    }

    interface FragmentInterface {
        void fragmentClick(int position);
    }

}