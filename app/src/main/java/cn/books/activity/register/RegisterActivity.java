package cn.books.activity.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.books.R;
import cn.books.base.BaseActivity;
import cn.books.db.Administrator;
import cn.books.utils.ETChangedUtlis;
import cn.books.utils.ToastUtil;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.iv_user_name_clear)
    ImageView ivUserNameClear;
    @BindView(R.id.et_user_tel)
    EditText etUserTel;
    @BindView(R.id.iv_user_tel_clear)
    ImageView ivUserTelClear;
    @BindView(R.id.et_user_pwd)
    EditText etUserPwd;
    @BindView(R.id.cb_user_pwd)
    CheckBox cbUserPwd;
    @BindView(R.id.et_user_pwd_to)
    EditText etUserPwdTo;
    @BindView(R.id.cb_user_pwd_to)
    CheckBox cbUserPwdTo;
    @BindView(R.id.bt_register)
    Button btRegister;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        ETChangedUtlis.EditTextChangedListener(etUsername, ivUserNameClear);
        ETChangedUtlis.EditTextChangedListener(etUserTel, ivUserTelClear);
        ETChangedUtlis.EditTextChangedListener(etUserPwd, cbUserPwd);
        ETChangedUtlis.EditTextChangedListener(etUserPwdTo, cbUserPwdTo);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.bt_register)
    public void onViewClicked() {
        isCheck();
    }

    /**
     *
     */
    private void isCheck() {
        if (TextUtils.isEmpty(etUsername.getText().toString())) {
            ToastUtil.showToast("用户名不能为空!");
            return;
        }
        if (TextUtils.isEmpty(etUserTel.getText().toString())) {
            ToastUtil.showToast("电话号码不能为空!");
            return;
        }
        if (etUserTel.getText().toString().length() != 11) {
            ToastUtil.showToast("电话号码必须为11位");
            return;
        }

        if (TextUtils.isEmpty(etUserPwd.getText().toString())) {
            ToastUtil.showToast("密码不能为空!");
            return;
        }
        if (TextUtils.isEmpty(etUserPwdTo.getText().toString())) {
            ToastUtil.showToast("确定密码不能为空!");
            return;
        }
        if (!etUserPwd.getText().toString().equals(etUserPwdTo.getText().toString())) {
            ToastUtil.showToast("两次密码不一样!");
            return;
        }


        showProgressDialog("注册中..");
        Administrator administrator = new Administrator();
        administrator.setName(etUsername.getText().toString());
        administrator.setTel(etUserTel.getText().toString());
        administrator.setPwd(etUserPwd.getText().toString());
        administrator.save();
        closeProgressDialog();
        ToastUtil.showToast("注册成功!");
        finish();
    }
}
