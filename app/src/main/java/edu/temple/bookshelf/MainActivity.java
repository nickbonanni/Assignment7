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
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
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
    AudiobookService.BookProgress bookProgress;
    boolean isConnected;
    int progress;

    Handler seekBarHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            bookProgress = (AudiobookService.BookProgress) msg.obj;
            if (bookProgress != null) {
                progress = bookProgress.getProgress();
            }
            return true;
        }
    });

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (AudiobookService.MediaControlBinder) service;
            binder.setProgressHandler(seekBarHandler);
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

        Fragment fragment2 = fm.findFragmentById(R.id.controlContainer);
        controlFragment = (book == null) ? new ControlFragment() : ControlFragment.newInstance(book);

        if (!(fragment2 instanceof ControlFragment)) {

            fm.beginTransaction()
                    .add(R.id.controlContainer, controlFragment)
                    .commit();
        }

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

        searchButton.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, BookSearchActivity.class);

            startActivityForResult(intent, LAUNCH_BOOK_SEARCH);

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == LAUNCH_BOOK_SEARCH) {
            if (resultCode == Activity.RESULT_OK) {
                bookList = data.getParcelableExtra("booklist");
            }
        }

        if (bookList != null) {
            bookListFragment = BookListFragment.newInstance(bookList);
        }

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

        if (hasContainer2) {

            bookDetailsFragment.displayBook(book);

        } else {

            fm.beginTransaction()
                    .replace(R.id.container1, BookDetailsFragment.newInstance(book))
                    .addToBackStack(null)
                    .commit();
        }

        controlFragment = ControlFragment.newInstance(book);

        fm.beginTransaction()
                .replace(R.id.controlContainer, controlFragment)
                .commit();

    }

    public void onPlayClick() {

        if (isConnected && book != null) {
            binder.play(book.getID());
            controlFragment.seekBar.setMax(book.getDuration());
            updateProgressBar();
        }

    }

    public void onPauseClick() {

        if (isConnected && book != null) {
            binder.pause();
        }

    }

    public void onStopClick() {

        progress = 0;
        binder.stop();
        updateProgressBar();

    }

    public void onSeekBarChange(int position) {

        binder.seekTo(position);

    }

    public void updateProgressBar() {
        seekBarHandler.postDelayed(updateBar, 1000);
    }

    private Runnable updateBar = new Runnable() {
        @Override
        public void run() {

            controlFragment.seekBar.setProgress(progress);

            seekBarHandler.postDelayed(this, 1000);
        }
    };
}