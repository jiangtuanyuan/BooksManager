package cn.books.activity.borrowingmangement.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import cn.books.activity.borrowingmangement.ui.BorrowingMainActivity;

/**
 * $name
 *
 * @author ${LiuTao}
 * @date 2018/1/16/016
 */

public abstract class BorrBaseFragment extends Fragment {
    private LoadingPager mLoadingPager;
    protected Context mContext = null;

    public LoadingPager getLoadingPager() {
        return mLoadingPager;
    }

    public BorrowingMainActivity borrowingMainActivity;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.borrowingMainActivity = (BorrowingMainActivity) context;
        this.borrowingMainActivity.xxx = 1;
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.borrowingMainActivity = (BorrowingMainActivity) context;
        this.borrowingMainActivity.xxx = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            mContext = getContext();
            mLoadingPager = new LoadingPager(mContext) {
                @Override
                public LoadedResult initData() {
                    Log.e("TAG", "------base初始化数据-----");
                    return BorrBaseFragment.this.initData();
                }

                @Override
                public View initSuccessView() {
                    Log.e("TAG", "------base初始化视图-----");
                    return BorrBaseFragment.this.initSuccessView();
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        mLoadingPager.triggerLoadData();
        return mLoadingPager;
    }

    public abstract LoadingPager.LoadedResult initData();

    public abstract View initSuccessView();

    /**
     * @des 校验请求回来的数据
     */
    public LoadingPager.LoadedResult checkResult(Object resObj) {
        if (resObj == null) {
            return LoadingPager.LoadedResult.EMPTY;
        }
        //resObj -->List
        if (resObj instanceof List) {
            if (((List) resObj).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        //resObj -->Map
        if (resObj instanceof Map) {
            if (((Map) resObj).size() == 0) {
                return LoadingPager.LoadedResult.EMPTY;
            }
        }
        return LoadingPager.LoadedResult.SUCCESS;
    }

}
