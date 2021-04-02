package edu.temple.bookshelf;

public class Book {

    String title;
    String author;
    String coverURL;
    int id;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

}
