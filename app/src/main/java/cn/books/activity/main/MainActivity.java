package cn.books.activity.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.books.R;
import cn.books.activity.login.LoginActivity;
import cn.books.activity.students.ui.StudenMainActivity;
import cn.books.activity.user.UserChooseActivity;
import cn.books.base.BaseActivity;
import cn.books.utils.SPUtils;
import cn.books.view.IOSDialog;
import cn.books.view.IOSDialogUtils;

public class MainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_topinfo)
    TextView tvTopinfo;
    @BindView(R.id.tv_tusgl)
    TextView tvTusgl;
    @BindView(R.id.tv_tsjygl)
    TextView tvTsjygl;
    @BindView(R.id.tv_xtxxgl)
    TextView tvXtxxgl;

    private String mUserTel = SPUtils.getInstance().getString(SPUtils.USER_TEL);

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setTitle("  系部图书管理系统");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_exit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.exit:
                LoginExit();


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void initData() {
        tvTopinfo.setText(SPUtils.getInstance().getString(SPUtils.USER_NAME) + ",欢迎您进入系部图书管理系统!");
    }

    @OnClick({R.id.tv_tusgl, R.id.tv_tsjygl, R.id.tv_xtxxgl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_tusgl://图书管理
                Intent groupsIn = new Intent(this, UserChooseActivity.class);
                groupsIn.putExtra("mChooseNums", "more");
                startActivityForResult(groupsIn, 101);
                break;
            case R.id.tv_tsjygl://借阅管理
                Intent intent = new Intent(this, UserChooseActivity.class);
                intent.putExtra("mChooseNums", "single");
                startActivityForResult(intent, 100);
                break;
            case R.id.tv_xtxxgl://学生管理
                startActivity(new Intent(this, StudenMainActivity.class));
                break;
            default:
                break;
        }
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

    /**
     * 退出
     */
    private void LoginExit() {
        IOSDialogUtils utils = IOSDialogUtils.getInstance();
        utils.showDialogIos(this, "是否退出登陆?", "取消", "确定", false);
        utils.setOnButtonClickListener(new IOSDialogUtils.OnButtonClickListener() {
            @Override
            public void onCancelButtonClick(IOSDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveButtonClick(IOSDialog dialog) {
                dialog.dismiss();
                SPUtils.getInstance().clear();
                SPUtils.getInstance().putString(SPUtils.USER_TEL, mUserTel);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

}
