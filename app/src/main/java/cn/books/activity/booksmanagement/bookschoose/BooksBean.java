package cn.books.activity.booksmanagement.bookschoose;

import java.io.Serializable;

import cn.books.db.Books;

/**
 * Created by 蒋 on 2018/7/7.
 * 图书实体类
 */

public class BooksBean implements Serializable {
    private Books books;
    private boolean checked = false;//是否选择

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
