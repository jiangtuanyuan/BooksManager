package cn.books.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.books.R;
import cn.books.activity.main.MainActivity;
import cn.books.activity.register.RegisterActivity;
import cn.books.base.BaseActivity;
import cn.books.db.Administrator;
import cn.books.utils.ETChangedUtlis;
import cn.books.utils.SPUtils;
import cn.books.utils.ToastUtil;

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
    @BindView(R.id.tv_register)
    TextView tvRegister;

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


    @OnClick({R.id.submit_btn, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.submit_btn:
                showProgressDialog("登陆中..");

                String tel = userName.getText().toString();
                String pwd = userPwd.getText().toString();
                if (!TextUtils.isEmpty(tel) && !TextUtils.isEmpty(pwd)) {
                    if (Login(tel, pwd)) {
                        closeProgressDialog();
                        //保存信息
                        ToastUtil.showToast("登陆成功");
                        SPUtils.getInstance().putInt(SPUtils.USER_ID, administrators.get(0).getId());
                        SPUtils.getInstance().putString(SPUtils.USER_NAME, administrators.get(0).getName());
                        SPUtils.getInstance().putString(SPUtils.USER_TEL, administrators.get(0).getTel());
                        SPUtils.getInstance().putString(SPUtils.USER_PWD, administrators.get(0).getPwd());

                        Intent MainInten = new Intent(this, MainActivity.class);
                        startActivity(MainInten);

                        this.finish();
                    } else {
                        closeProgressDialog();
                        ToastUtil.showToast("密码错误或者用户不存在!");
                    }
                } else {
                    closeProgressDialog();
                    ToastUtil.showToast("帐号密码不能为空！");
                }
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 登陆
     */
    //本地数据库管理员数据
    private List<Administrator> administrators = new ArrayList<>();

    private boolean Login(String tel, String pwd) {
        administrators.clear();
        administrators.addAll(LitePal.where("Tel = ? and Pwd = ?", tel, pwd).find(Administrator.class));
        if (administrators.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 按返回键，实现按home键
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
