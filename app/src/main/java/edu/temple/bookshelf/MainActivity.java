package edu.temple.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements BookListFragment.FragmentInterface {

    FragmentManager fragmentManager;
    FragmentTransaction ft;
    BookListFragment bookListFragment;
    BookDetailsFragment bookDetailsFragment;
    BookList bookList;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookList = new BookList();
        String[] titlesAndAuthors = getResources().getStringArray(R.array.books);

        for (int i = 0; i <= 18; i += 2) {
            Book book = new Book(titlesAndAuthors[i], titlesAndAuthors[i + 1]);
            bookList.add(book);
        }

        // Building list fragment
        bookListFragment = new BookListFragment().newInstance(bookList);
        fragmentManager = getSupportFragmentManager();

        ft = fragmentManager.beginTransaction();
        ft.add(R.id.container, bookListFragment);

        ft.commit();

    }

    public void fragmentClick(int position) {
        bundle.putInt("POSITION", position);
        bookDetailsFragment.setArguments(bundle);
    }

}