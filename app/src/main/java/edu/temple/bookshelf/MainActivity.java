package edu.temple.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface {

    BookListFragment bookListFragment;
    BookDetailsFragment bookDetailsFragment;
    BookList bookList;
    boolean hasContainer2;
    boolean hasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hasContainer2 = findViewById(R.id.container2) != null;

        bookList = new BookList();
        String[] titlesAndAuthors = getResources().getStringArray(R.array.books);

        for (int i = 0; i <= 18; i += 2) {
            Book book = new Book(titlesAndAuthors[i], titlesAndAuthors[i + 1]);
            bookList.add(book);
        }

        bookListFragment = BookListFragment.newInstance(bookList);
        bookDetailsFragment = (BookDetailsFragment)getSupportFragmentManager().findFragmentByTag("DETAILSADDED");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container1, bookListFragment, "LISTADDED")
                .commit();

        if (bookDetailsFragment != null && !hasContainer2) {

            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(bookDetailsFragment)
                    .remove(bookListFragment)
                    .commit();

            getSupportFragmentManager().executePendingTransactions();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container1, bookDetailsFragment, "DETAILSADDED")
                    .addToBackStack(null)
                    .commit();

            }

        else if (bookDetailsFragment != null && hasContainer2) {

            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(bookListFragment)
                    .remove(bookDetailsFragment)
                    .commit();

            getSupportFragmentManager().executePendingTransactions();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container1, bookListFragment, "LISTADDED")
                    .replace(R.id.container2, bookDetailsFragment, "DETAILSADDED")
                    .commit();

            }

        else if (hasContainer2) {

            bookDetailsFragment = new BookDetailsFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment)
                    .commit();

        }
    }

    @Override
    public void onBackPressed() {

        hasList = (BookListFragment)getSupportFragmentManager().findFragmentByTag("LISTADDED") != null;

        if (getSupportFragmentManager().getBackStackEntryCount() > 0 && !hasList) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container1, bookListFragment, "LISTADDED")
                    .commit();
        }

        super.onBackPressed();
    }

    public void fragmentClick(int position) {

        if (!hasContainer2) {

            bookDetailsFragment = BookDetailsFragment.newInstance(bookList.get(position));

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container1, bookDetailsFragment, "DETAILSADDED")
                    .addToBackStack(null)
                    .commit();

        } else {

            bookDetailsFragment.displayBook(bookList.get(position));

            bookDetailsFragment = BookDetailsFragment.newInstance(bookList.get(position));

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment, "DETAILSADDED")
                    .commit();

        }
    }
}