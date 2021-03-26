package edu.temple.bookshelf;

import java.util.ArrayList;
import java.util.List;

public class BookList {

    ArrayList<Book> bookList;

    public BookList() {
        bookList = new ArrayList<Book>();
    }

    public void add(Book book) {
        bookList.add(book);
    }

    public void remove(Book book) {
        bookList.remove(book);
    }

    public Book get(int index) {
        return bookList.get(index);
    }

    public int size() {
        return bookList.size();
    }

    public ArrayList<String> getList(int position) {

        ArrayList<String> list = new ArrayList<String>(2);
        Book book = bookList.get(position);
        list.add(book.getTitle());
        list.add(book.getAuthor());
        return list;

    }

}
