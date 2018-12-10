package cn.books.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.kaopiz.kprogresshud.KProgressHUD;

import cn.books.R;


/**
 * Activity基类
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private KProgressHUD kProgressHUD;

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    public void initToolbarNav() {
        if (mToolbar == null) {
            try {
                initToolbar();
            } catch (Exception e) {
                return;
            }
        }
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(v -> {
            try {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                onBackPressed();
            } finally {
                onBackPressed();
            }
        });
    }

    /**
     * 加载一个资源图标到Toolbar
     *
     * @param resID
     */
    public void initToolbarIcon(int resID) {
        if (mToolbar == null) {
            try {
                initToolbar();
            } catch (Exception e) {
                return;
            }
        }
        mToolbar.setNavigationIcon(resID);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        initViews(savedInstanceState);
        initData();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
    }

    public void setTitle(String title) {
        if (mToolbar == null) {
            initToolbar();
        }
        mToolbar.setTitle(title);
    }

    //隐藏状栏 全屏
    public void HiddenWindos() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    protected abstract void initVariables(); //上一个界面传过来的数据

    protected abstract void initViews(Bundle savedInstanceState); //初始化界面

    protected abstract void initData(); //初始化数据

    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //加载对话框相关

    public KProgressHUD getProgressDialog() {
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
        } else {
            kProgressHUD = new KProgressHUD(this);
        }
        return kProgressHUD;
    }

    public void showProgressDialog(String message) {
        if (kProgressHUD == null) {
            kProgressHUD = new KProgressHUD(this);
        }
        kProgressHUD.setLabel(message);
        if (!isFinishing() && !kProgressHUD.isShowing()) {
            kProgressHUD.show();
        }
    }

    public void showProgressDialog(String message, boolean cancelable) {
        if (kProgressHUD == null) {
            kProgressHUD = new KProgressHUD(this);
        }
        kProgressHUD.setCancellable(cancelable);
        kProgressHUD.setLabel(message);
        if (!isFinishing() && !kProgressHUD.isShowing()) {
            kProgressHUD.show();
        }
    }

    public void closeProgressDialog() {
        if (kProgressHUD != null && kProgressHUD.isShowing()) {
            kProgressHUD.dismiss();
        }
    }
}