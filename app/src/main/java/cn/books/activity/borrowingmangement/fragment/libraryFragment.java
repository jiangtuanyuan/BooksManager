package cn.books.activity.borrowingmangement.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.books.R;
import cn.books.activity.MyApp;
import cn.books.activity.borrowingmangement.adapter.BorrowingItemAdapter;
import cn.books.activity.borrowingmangement.base.BorrBaseFragment;
import cn.books.activity.borrowingmangement.base.LoadingPager;
import cn.books.db.Borrowing;

/**
 * Created by 蒋 on 2018/7/4.
 * 借阅中
 */

public class libraryFragment extends BorrBaseFragment implements OnRefreshListener {
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerview;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    private BorrowingItemAdapter mAdapter;
    private List<Borrowing> mList = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public LoadingPager.LoadedResult initData() {
        try {
            requestData();
            return checkResult(mList);
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
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        mAdapter = new BorrowingItemAdapter(getActivity(), mList);
        mRecyclerview.setAdapter(mAdapter);

        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    /**
     * 获取列表数据
     */
    private void requestData() {
        mList.clear();
        mList.addAll(LitePal.where("status = ?", "1").find(Borrowing.class));
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        requestData();
        if (refreshLayout != null) {
            refreshLayout.finishRefresh(2000);
        }

    }
}
