package cn.books.activity.booksmanagement.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.books.R;
import cn.books.base.BaseActivity;

public class BooksMainActivity extends BaseActivity {

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_books_main);
        setTitle("图书管理");
        initToolbarNav();
    }

    @Override
    protected void initData() {

    }
}
