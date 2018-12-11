package cn.books.activity;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import butterknife.ButterKnife;
import cn.books.R;
import cn.books.activity.login.LoginActivity;
import cn.books.activity.main.MainActivity;
import cn.books.base.BaseActivity;
import cn.books.utils.SPUtils;

public class WelcomeActivity extends BaseActivity {
    private String mTel = "";
    private String mPwd = "";

    private int TIME = 2300;//延时多少秒启动

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        HiddenWindos();
        new Handler().postDelayed(() -> {
            checkLogin();
        }, TIME);
    }

    @Override
    protected void initData() {

    }

    /**
     * 效验本地是否存在账号跟密码
     */
    private void checkLogin() {
        mTel = SPUtils.getInstance().getString(SPUtils.USER_TEL);
        mPwd = SPUtils.getInstance().getString(SPUtils.USER_PWD);

        if (!TextUtils.isEmpty(mTel) && !TextUtils.isEmpty(mPwd)) {
            Intent MainInten = new Intent(this, MainActivity.class);
            startActivity(MainInten);
            finish();
        } else {
            //没有账号密码 跳转到登陆页面
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
