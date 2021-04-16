package edu.temple.bookshelf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import edu.temple.audiobookplayer.AudiobookService;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface, ControlFragment.ControlFragmentInterface {

    FragmentManager fm;
    BookListFragment bookListFragment;
    BookDetailsFragment bookDetailsFragment;
    ControlFragment controlFragment;
    BookList bookList;
    Book book;
    Button searchButton;

    String SELECTED_BOOK = "selectedBook";
    String CURRENT_LIST = "currentList";

    int LAUNCH_BOOK_SEARCH = 1;
    boolean hasContainer2;

    AudiobookService.MediaControlBinder binder;
    boolean isConnected;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (AudiobookService.MediaControlBinder) service;
            isConnected = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, AudiobookService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            book = savedInstanceState.getParcelable(SELECTED_BOOK);
            bookList = savedInstanceState.getParcelable(CURRENT_LIST);
        }

        bookListFragment = (bookList == null) ? new BookListFragment() : BookListFragment.newInstance(bookList);

        hasContainer2 = findViewById(R.id.container2) != null;
        searchButton = (Button) findViewById(R.id.searchButton);

        fm = getSupportFragmentManager();

        controlFragment = new ControlFragment();
        fm.beginTransaction()
                .add(R.id.controlContainer, controlFragment)
                .commit();

        Fragment fragment1 = fm.findFragmentById(R.id.container1);

        if (fragment1 instanceof BookDetailsFragment) {
            fm.popBackStack();
        } else if (!(fragment1 instanceof BookDetailsFragment) && !(fragment1 instanceof BookListFragment)) {
            fm.beginTransaction()
                    .add(R.id.container1, bookListFragment)
                    .commit();
        }

        bookDetailsFragment = (book == null) ? new BookDetailsFragment() : BookDetailsFragment.newInstance(book);

        if (hasContainer2) {
            fm.beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment)
                    .commit();
        } else if (book != null) {
            fm.beginTransaction()
                    .replace(R.id.container1, bookDetailsFragment)
                    .addToBackStack(null)
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
                bookList = data.getParcelableExtra("booklist");
            }
        }

        bookListFragment = BookListFragment.newInstance(bookList);
        Fragment fragment1 = fm.findFragmentById(R.id.container1);

        if (fragment1 instanceof BookDetailsFragment) {
            fm.popBackStack();
        } else if (!(fragment1 instanceof BookDetailsFragment)) {
            fm.beginTransaction()
                    .replace(R.id.container1, bookListFragment)
                    .commit();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        book = null;
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SELECTED_BOOK, book);
        outState.putParcelable(CURRENT_LIST, bookList);
    }

    public void fragmentClick(int position) {

        book = bookList.get(position);
        Log.e("ERROR", book.getTitle());

        if (hasContainer2) {

            bookDetailsFragment.displayBook(book);

        } else {

            fm.beginTransaction()
                    .replace(R.id.container1, BookDetailsFragment.newInstance(book))
                    .addToBackStack(null)
                    .commit();
        }
    }

    public void onPlayClick() {

        binder.play(book.getID());

    }

    public void onPauseClick() {

        binder.pause();

    }

    public void onStopClick() {

        binder.stop();

    }
}