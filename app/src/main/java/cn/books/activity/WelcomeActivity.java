package cn.books.activity;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;

import butterknife.ButterKnife;
import cn.books.R;
import cn.books.activity.login.LoginActivity;
import cn.books.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        HiddenWindos();
        new Handler().postDelayed(() -> {
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
            finish();
        }, 2500);
    }

    @Override
    protected void initData() {

    }

}
