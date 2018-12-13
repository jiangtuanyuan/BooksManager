package cn.books.activity;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import org.litepal.LitePal;

/**
 * Created by Jiang on 2018/8/20.
 * Application
 */

public class MyApp extends Application {
    public static Context context;

    public static Context getContext() {
        return context;
    }
    private static Handler mMainThreadHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        //初始化LitePal 数据库
        LitePal.initialize(this);
        //主线程的Handler
        mMainThreadHandler = new Handler();
    }
    /**
     * 得到主线程里面的创建的一个hanlder
     */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }
}
