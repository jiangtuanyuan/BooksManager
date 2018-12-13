package cn.books.activity.borrowingmangement.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import butterknife.ButterKnife;
import cn.books.R;
import cn.books.activity.MyApp;
import cn.books.activity.borrowingmangement.base.BorrBaseFragment;
import cn.books.activity.borrowingmangement.base.LoadingPager;

/**
 * Created by 蒋 on 2018/7/4.
 * 已归还
 */

public class ReturnFragment extends BorrBaseFragment {
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        try {
            requestData(true);
            return checkResult(null);
        } catch (Exception e) {
            e.printStackTrace();
            return LoadingPager.LoadedResult.ERROR;
        }
    }
    @Override
    public View initSuccessView() {
        View view = LayoutInflater.from(MyApp.context).inflate(R.layout.recycler, null, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(MyApp.context);
        return view;
    }

    /**
     * 获取列表数据
     */
    private void requestData(final boolean isRefresh) {


    }


}
