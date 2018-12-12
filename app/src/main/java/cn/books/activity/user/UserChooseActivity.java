package cn.books.activity.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


import com.jakewharton.rxbinding2.widget.RxTextView;

import org.litepal.LitePal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.books.R;
import cn.books.base.BaseActivity;
import cn.books.db.Students;
import cn.books.utils.ETChangedUtlis;

public class UserChooseActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, UserListAdapter.ItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_edt)
    EditText mSearchEdt;
    @BindView(R.id.search_edt_clear)
    ImageView mSearchEdtClear;
    @BindView(R.id.search_img)
    ImageView searchImg;
    @BindView(R.id.ecyclerView)
    RecyclerView mRecyclerview;

    private String mChooseNums = "more";//单："single" 多:"more"   只能选择一个用户或者多个用户
    private String isalls = "0";//是否可以随便选择一个 默认  0否 1可以

    private UserListAdapter adapter;

    private List<UserBean> mList = new ArrayList<>();//用户显示选择列表
    private List<UserBean> mSumList = new ArrayList<>();//用户查询总列表
    private List<UserBean> mCheckList = new ArrayList<>();//已经选项的用户列表


    private List<Students> mStuList = new ArrayList<>();

    @Override
    protected void initVariables() {
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mChooseNums = bundle.getString("mChooseNums");
            isalls = bundle.getString("isalls");
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_choose);
        ButterKnife.bind(this);
        initToolbarNav();

        ETChangedUtlis.EditTextChangedListener(mSearchEdt, mSearchEdtClear);
    }

    @Override
    protected void initData() {
        mToolbar.inflateMenu(R.menu.menu_ok);
        mToolbar.setOnMenuItemClickListener(this);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        if (mChooseNums.equals("single")) {
            setTitle("单 选");
            adapter = new UserListAdapter(this, mList, true);
        } else {
            setTitle("多 选");
            adapter = new UserListAdapter(this, mList, false);
        }

        if (!TextUtils.isEmpty(isalls)) {
            if (isalls.equals("1")) {
                adapter.setIsall(true);
            }
        }

        adapter.setmItemClickListener(this);
        mRecyclerview.setAdapter(adapter);

        initDBData();//加载数据库的用户数据
    }

    /**
     * 加载数据库的用户数据
     */
    @SuppressLint("CheckResult")
    private void initDBData() {
        mStuList.addAll(LitePal.findAll(Students.class));

        for (Students e : mStuList) {
            UserBean userBean = new UserBean();
            userBean.setStuid(e.getId());
            userBean.setStu_name(e.getStu_name());
            userBean.setStu_number(e.getStu_number());
            userBean.setStu_class(e.getStu_class());
            userBean.setStu_sex(e.getStu_sex());
            mList.add(userBean);
            mSumList.add(userBean);
        }

        adapter.notifyDataSetChanged();

        RxTextView.textChanges(mSearchEdt)
                .subscribe(charSequence -> {
                    if (charSequence.length() > 0) {
                        mSearchEdtClear.setVisibility(View.VISIBLE);
                        Search(charSequence.toString() + "");
                    } else {
                        mList.clear();
                        mList.addAll(mSumList);
                        adapter.notifyDataSetChanged();
                        mSearchEdtClear.setVisibility(View.INVISIBLE);
                    }
                });

    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ok://确定
                Bundle bundle = new Bundle();
                bundle.putString("mChooseNums", mChooseNums);
                bundle.putSerializable("mCheckList", (Serializable) mCheckList);
                setResult(RESULT_OK, getIntent().putExtras(bundle));
                finish();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onItemClick(int position, UserBean item) {
        for (int i = 0; i < mCheckList.size(); i++) {
            UserBean user = mCheckList.get(i);
            //判断是否存在
            if (user.getStuid() == item.getStuid()) {
                mCheckList.remove(i);
            }
        }
        //添加进选择的集合
        if (item.isChecked()) {
            mCheckList.add(item);
        }
    }

    /**
     * 在总数据里面搜索用户名mSumList
     */
    private void Search(String search) {
        mList.clear();
        for (UserBean user : mSumList) {
            if (user.getStu_name().contains(search)) {
                mList.add(user);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
