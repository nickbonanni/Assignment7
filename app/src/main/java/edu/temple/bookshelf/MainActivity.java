package edu.temple.bookshelf;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface {

    BookListFragment bookListFragment;
    BookDetailsFragment bookDetailsFragment;
    BookList bookList;
    boolean hasContainer2;
    boolean hasList;
    Button searchButton;
    int LAUNCH_BOOK_SEARCH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hasContainer2 = findViewById(R.id.container2) != null;
        searchButton = (Button) findViewById(R.id.searchButton);

        bookList = new BookList();

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

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, BookSearchActivity.class);

                startActivityForResult(intent, LAUNCH_BOOK_SEARCH);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == LAUNCH_BOOK_SEARCH) {
            if (resultCode == Activity.RESULT_OK) {
                // Do something
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                bookList = new BookList();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
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