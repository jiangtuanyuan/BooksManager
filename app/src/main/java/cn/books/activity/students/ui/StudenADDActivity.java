package cn.books.activity.students.ui;


import android.os.Bundle;

import butterknife.ButterKnife;
import cn.books.R;
import cn.books.base.BaseActivity;

public class StudenADDActivity extends BaseActivity {
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_studen_add);
        ButterKnife.bind(this);
        initToolbarNav();
    }

    @Override
    protected void initData() {

    }
}
