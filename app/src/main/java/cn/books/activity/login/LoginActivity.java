package cn.books.activity.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.books.R;
import cn.books.base.BaseActivity;
import cn.books.utils.ETChangedUtlis;
import cn.books.utils.SPUtils;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.img_btn_account)
    ImageView imgBtnAccount;
    @BindView(R.id.user_pwd)
    EditText userPwd;
    @BindView(R.id.img_btn_pwd)
    CheckBox imgBtnPwd;
    @BindView(R.id.submit_btn)
    Button submitBtn;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ETChangedUtlis.EditTextChangedListener(userName, imgBtnAccount);//控件数据联动
        ETChangedUtlis.EditTextChangedListener(userPwd, imgBtnPwd);
        userName.setText(SPUtils.getInstance().getString(SPUtils.USER_TEL, ""));
        userPwd.setText(SPUtils.getInstance().getString(SPUtils.USER_PWD, ""));

    }

    @Override
    protected void initData() {

    }


}
