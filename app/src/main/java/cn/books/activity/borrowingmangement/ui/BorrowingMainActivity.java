package cn.books.activity.borrowingmangement.ui;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import org.litepal.LitePal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.books.R;
import cn.books.activity.borrowingmangement.base.BorrBaseFragment;
import cn.books.activity.borrowingmangement.base.LoadingPager;
import cn.books.activity.borrowingmangement.fragment.OverdueReturnFragment;
import cn.books.activity.borrowingmangement.fragment.ReturnFragment;
import cn.books.activity.borrowingmangement.fragment.libraryFragment;
import cn.books.base.BaseActivity;
import cn.books.db.Borrowing;
import cn.books.utils.DateUtils;
import cn.books.utils.ToastUtil;

public class BorrowingMainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tablatout)
    TabLayout mTabLayout;
    @BindView(R.id.vp)
    public ViewPager vp;
    @BindView(R.id.document_add)
    LinearLayout mDocumentlAdd;


    public libraryFragment phpSendFragment;//
    public ReturnFragment phpSignFragment;//
    public OverdueReturnFragment overdueReturnFragment;

    public int xxx = 0;
    private Map<Integer, BorrBaseFragment> mFragmentList = new HashMap<>();

    public DemandAdapter mDemandAdapter;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (xxx == 1) {
            phpSendFragment.onRefresh(null);
            phpSignFragment.onRefresh(null);
            overdueReturnFragment.onRefresh(null);
        }
        //设置所有图书状态
        setBooksStatus();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_borrowing_main);
        ButterKnife.bind(this);
        setTitle("借阅管理");
        initToolbarNav();
        initFragment();
    }

    @Override
    protected void initData() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mDemandAdapter = new DemandAdapter(getSupportFragmentManager());
        vp.setAdapter(mDemandAdapter);
        vp.setOffscreenPageLimit(2);

        //TabLayout加载viewpager

        mTabLayout.setupWithViewPager(vp);
        mTabLayout.getTabAt(0).setText("借阅中");
        mTabLayout.getTabAt(1).setText("已归还");
        mTabLayout.getTabAt(2).setText("已逾期");

        //监听ViewPager页面的切换
        final TaskVpChangeListener taskVpChangeListener = new TaskVpChangeListener();
        vp.addOnPageChangeListener(taskVpChangeListener);
        vp.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                taskVpChangeListener.onPageSelected(0);
                vp.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /**
     * 检索有没有过期的
     */
    private List<Borrowing> mBoorrList = new ArrayList<>();

    private void setBooksStatus() {
        mBoorrList.clear();
        mBoorrList.addAll(LitePal.findAll(Borrowing.class));
        for (Borrowing b : mBoorrList) {
            //判断当前时间是否大于归还时间
            //借阅中
            if (b.getStatus() == 1) {
                if (compareTime(b.getStu_return_time())) {
                    //逾期
                    Log.e("Borrowing-status", "逾期");
                    b.setStatus(3);
                    b.update(b.getId());
                } else {
                    Log.e("Borrowing-status", "未逾期");
                }
            }
        }
    }

    /**
     * 比较时间大小  如果time1比time早 就返回true 否者就返回false
     * 例如：time1="2018-04-02 15:45:16"
     * time2="2018-04-02 15:48:16"
     * return true
     */
    public boolean compareTime(String time1) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date t1 = sdf.parse(time1);
            Date t2 = sdf.parse(DateUtils.getDate());
            if (t1.before(t2))
                return true;
        } catch (ParseException e) {
            return false;
        }
        return false;
    }

    /**
     * 加载Fragment
     */
    private void initFragment() {
        phpSendFragment = new libraryFragment();
        phpSignFragment = new ReturnFragment();
        overdueReturnFragment = new OverdueReturnFragment();

        mFragmentList.put(0, phpSendFragment);
        mFragmentList.put(1, phpSignFragment);
        mFragmentList.put(2, overdueReturnFragment);
    }


    /**
     * VP的滑动事件
     */
    class TaskVpChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            //滑动的位置 position
            BorrBaseFragment baseFragment = mFragmentList.get(position);
            if (baseFragment.getLoadingPager() != null) {
                LoadingPager loadingPager = baseFragment.getLoadingPager();
                loadingPager.triggerLoadData();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    @OnClick(R.id.document_add)
    public void onViewClicked() {
        startActivity(new Intent(this, BorrowingAddActivity.class));
    }

    /**
     * VP适配
     */
    public class DemandAdapter extends FragmentStatePagerAdapter {
        public DemandAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        //刷新指定位置的Fragment
        public void notifyFragmentByPosition(int position) {
            notifyDataSetChanged();
        }
    }
}
