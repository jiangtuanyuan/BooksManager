package cn.books.activity.main;

import android.Manifest;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.books.R;
import cn.books.activity.booksmanagement.bookschoose.BooksChooseActivity;
import cn.books.activity.booksmanagement.ui.BooksMainActivity;
import cn.books.activity.borrowingmangement.ui.BorrowingMainActivity;
import cn.books.activity.login.LoginActivity;
import cn.books.activity.students.ui.StudenMainActivity;
import cn.books.base.BaseActivity;
import cn.books.utils.SPUtils;
import cn.books.view.IOSDialog;
import cn.books.view.IOSDialogUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
        setTitle("\t\t系部图书管理系统");
        setSupportActionBar(toolbar);
        checkPermissions(this);
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
                startActivity(new Intent(this, BooksMainActivity.class));
                /*Intent groupsIn = new Intent(this, UserChooseActivity.class);
                groupsIn.putExtra("mChooseNums", "more");
                startActivityForResult(groupsIn, 101);
               */
                break;
            case R.id.tv_tsjygl://借阅管理
                startActivity(new Intent(this, BorrowingMainActivity.class));

          /*    Intent intent = new Intent(this, BooksChooseActivity.class);
                intent.putExtra("mChooseNums", "single");
                startActivityForResult(intent, 100);

                Intent intent = new Intent(this, UserChooseActivity.class);
                intent.putExtra("mChooseNums", "single");
                startActivityForResult(intent, 100);
               */
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

    /**
     * 检测权限
     *
     * @param context
     */
    public static void checkPermissions(BaseActivity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new RxPermissions(context).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.VIBRATE,
                    Manifest.permission.INTERNET, Manifest.permission.ACCESS_WIFI_STATE,
                    Manifest.permission.WAKE_LOCK, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA)
                    .subscribe(new Observer<Boolean>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {

                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

}
