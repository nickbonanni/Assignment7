package edu.temple.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookList bookList = new BookList();
        String[] titlesAndAuthors = getResources().getStringArray(R.array.books);

        for (int i = 0; i <= 18; i += 2) {
            Book book = new Book(titlesAndAuthors[i], titlesAndAuthors[i+1]);
            bookList.add(book);
        }


    }

}