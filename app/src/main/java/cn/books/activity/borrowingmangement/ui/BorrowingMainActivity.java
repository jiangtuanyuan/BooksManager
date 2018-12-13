package cn.books.activity.borrowingmangement.ui;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.books.R;
import cn.books.activity.borrowingmangement.base.BorrBaseFragment;
import cn.books.activity.borrowingmangement.base.LoadingPager;
import cn.books.activity.borrowingmangement.fragment.ReturnFragment;
import cn.books.activity.borrowingmangement.fragment.libraryFragment;
import cn.books.base.BaseActivity;
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

    private Map<Integer, BorrBaseFragment> mFragmentList = new HashMap<>();

    public DemandAdapter mDemandAdapter;

    @Override
    protected void initVariables() {

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
        vp.setOffscreenPageLimit(1);

        //TabLayout加载viewpager

        mTabLayout.setupWithViewPager(vp);
        mTabLayout.getTabAt(0).setText("借阅中");
        mTabLayout.getTabAt(1).setText("已归还");

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
     * 加载Fragment
     */
    private void initFragment() {
        phpSendFragment = new libraryFragment();
        phpSignFragment = new ReturnFragment();

        mFragmentList.put(0, phpSendFragment);
        mFragmentList.put(1, phpSignFragment);
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
        ToastUtil.showToast("新增借阅");
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
