package edu.temple.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BookSearchActivity extends AppCompatActivity {

    EditText editTextSearch;
    Button searchButton2;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        this.setFinishOnTouchOutside(false);

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        searchButton2 = (Button) findViewById(R.id.searchButton2);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        searchButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}