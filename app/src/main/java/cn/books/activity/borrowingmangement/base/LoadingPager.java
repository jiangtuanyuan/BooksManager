package cn.books.activity.borrowingmangement.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import cn.books.R;
import cn.books.activity.MyApp;
import cn.books.threadproxy.ThreadPoolProxyFactory;

/**
 * 1.提供视图-->4种视图中的一种(加载中视图,错误视图,空视图,成功视图)
 * 2.加载数据
 * 3.数据和视图的绑定
 *
 * @author LiuTao
 */
public abstract class LoadingPager extends FrameLayout {
    public static final int STATE_LOADING = 0;//加载中
    public static final int STATE_ERROR = 1;//错误
    public static final int STATE_SUCCESS = 2;//成功
    public static final int STATE_EMPTY = 3;//空
    public int mCurState = STATE_LOADING;//默认是加载中的情况
    private Context mContext;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;
    private LoadDataTask mLoadDataTask;

    public LoadingPager(Context context) {
        super(context);
        this.mContext = context;
        initCommonView();
    }

    public void initCommonView() {
        mLoadingView = View.inflate(mContext, R.layout.pager_loading, null);
        this.addView(mLoadingView);

        mErrorView = View.inflate(mContext, R.layout.pager_error, null);
        this.addView(mErrorView);
        mErrorView.findViewById(R.id.error_btn_retry).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerLoadData();
            }
        });
        mEmptyView = View.inflate(mContext, R.layout.pager_empty, null);
        this.addView(mEmptyView);
        refreshViewByState();
    }

    private void refreshViewByState() {
        if (mCurState == STATE_LOADING) {
            mLoadingView.setVisibility(View.VISIBLE);
        } else {
            mLoadingView.setVisibility(View.GONE);
        }
        if (mCurState == STATE_ERROR) {
            mErrorView.setVisibility(View.VISIBLE);
        } else {
            mErrorView.setVisibility(View.GONE);
        }
        if (mCurState == STATE_EMPTY) {
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setVisibility(View.GONE);
        }

        if (mSuccessView == null && mCurState == STATE_SUCCESS) {
            mSuccessView = initSuccessView();
            this.addView(mSuccessView);
        }
        if (mSuccessView != null) {
            if (mCurState == STATE_SUCCESS) {
                mSuccessView.setVisibility(View.VISIBLE);
            } else {
                mSuccessView.setVisibility(View.GONE);
            }
        }
    }

    //触发加载数据
    public void triggerLoadData() {
        if (mCurState != STATE_SUCCESS) {
            if (mLoadDataTask == null) {
                mCurState = STATE_LOADING;
                refreshViewByState();
                mLoadDataTask = new LoadDataTask();
                ThreadPoolProxyFactory.getNormalThreadPoolProxy().submit(mLoadDataTask);
            }
        }
    }

    class LoadDataTask implements Runnable {
        @Override
        public void run() {
            LoadedResult loadedResult = initData();
            mCurState = loadedResult.getState();
            MyApp.getMainThreadHandler().post((Runnable) () -> {
                refreshViewByState();//mCurState-->Int
            });
            mLoadDataTask = null;
        }
    }

    public abstract LoadedResult initData();

    public abstract View initSuccessView();

    public enum LoadedResult {
        SUCCESS(STATE_SUCCESS), ERROR(STATE_ERROR), EMPTY(STATE_EMPTY);

        private int state;

        public int getState() {
            return state;
        }

        LoadedResult(int state) {
            this.state = state;
        }
    }
}
