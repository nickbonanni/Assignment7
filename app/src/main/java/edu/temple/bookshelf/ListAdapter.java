package edu.temple.bookshelf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ListAdapter extends BaseAdapter {

    Context context;
    BookList bookList;

    public ListAdapter(Context context, BookList bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_adapter,
                    parent, false);
        }

        TextView bookTitle = (TextView) convertView.findViewById(R.id.bookTitle);
        bookTitle.setTextSize(35);
        bookTitle.setText(bookList.get(position).getTitle());

        TextView bookAuthor = (TextView) convertView.findViewById(R.id.bookAuthor);
        bookAuthor.setTextSize(25);
        bookAuthor.setText(bookList.get(position).getAuthor());

        return convertView;

    }
}