package edu.temple.bookshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class BookSearchActivity extends AppCompatActivity {

    EditText editTextSearch;
    Button searchButton2;
    Button cancelButton;
    BookList booklist;
    Intent result;

    Handler downloadHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            try {

                JSONArray jsonBookListArray = new JSONArray((String) msg.obj);

                if (jsonBookListArray.length() > 0) {

                    for (int i = 0; i < jsonBookListArray.length(); i++) {

                        JSONObject jsonBook = jsonBookListArray.getJSONObject(i);

                        String title = jsonBook.getString("title");
                        String author = jsonBook.getString("author");
                        String coverURL = jsonBook.getString("cover_url");
                        int id = jsonBook.getInt("id");

                        Book book = new Book(title, author, coverURL, id);

                        booklist.add(book);

                        Log.e("ERROR", "Adding book " + title);

                    }

                } else {
                    noSearch();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            result.putExtra("booklist", booklist);
            setResult(Activity.RESULT_OK, result);
            finish();

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        this.setFinishOnTouchOutside(false);

        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        searchButton2 = (Button) findViewById(R.id.searchButton2);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        result = new Intent();
        booklist = new BookList();

        String urlString = "https://kamorris.com/lab/cis3515/search.php?term=";

        searchButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userSearch = editTextSearch.getText().toString();
                String searchString = urlString + userSearch;

                new Thread() {

                    @Override
                    public void run() {

                        try {

                            URL url = new URL(searchString);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                            Message msg = Message.obtain();
                            msg.obj = reader.readLine();
                            downloadHandler.sendMessage(msg);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    }
                }.start();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noSearch();

            }
        });
    }

    public void noSearch() {

        setResult(Activity.RESULT_CANCELED, result);
        finish();

    }
}