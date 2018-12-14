package cn.books.activity.booksmanagement.bookschoose;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
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
import cn.books.db.Books;

import cn.books.utils.ETChangedUtlis;

/**
 * 图书选择
 */
public class BooksChooseActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, BooksListAdapter.ItemClickListener {
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


    private List<BooksBean> mList = new ArrayList<>();//用户显示选择列表
    private List<BooksBean> mSumList = new ArrayList<>();//用户查询总列表
    private List<BooksBean> mCheckList = new ArrayList<>();//已经选项的用户列表

    private BooksListAdapter adapter;

    private List<Books> mStuList = new ArrayList<>();

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
        setContentView(R.layout.activity_books_choose);
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
            adapter = new BooksListAdapter(this, mList, true);
        } else {
            setTitle("多 选");
            adapter = new BooksListAdapter(this, mList, false);
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
        mStuList.addAll(LitePal.findAll(Books.class));

        for (Books e : mStuList) {
            BooksBean Bean = new BooksBean();
            Bean.setBooks(e);
            Bean.setChecked(false);
            mList.add(Bean);
            mSumList.add(Bean);
        }

        adapter.notifyDataSetChanged();

        RxTextView.textChanges(mSearchEdt)
                .subscribe(charSequence -> {
                    if (charSequence.length() > 0) {
                        mSearchEdtClear.setVisibility(View.VISIBLE);
                        mSearch = charSequence.toString();
                    } else {
                        mSearch = "";
                        mSearchEdtClear.setVisibility(View.INVISIBLE);
                    }
                    Search();
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


    /**
     * 在总数据里面搜索用户名mSumList
     */
    private String mSearch = "";

    private void Search() {
        mList.clear();
        if (!TextUtils.isEmpty(mSearch)) {
            for (BooksBean book : mSumList) {
                if (book.getBooks().getBook_name().contains(mSearch)
                        || book.getBooks().getBook_type().contains(mSearch)
                        || book.getBooks().getBook_author().contains(mSearch)
                        ) {
                    mList.add(book);
                }
            }
        } else {
            mList.addAll(mSumList);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position, BooksBean item) {
        for (int i = 0; i < mCheckList.size(); i++) {
            BooksBean user = mCheckList.get(i);
            //判断是否存在
            if (user.getBooks().getId() == item.getBooks().getId()) {
                mCheckList.remove(i);
            }
        }
        //添加进选择的集合
        if (item.isChecked()) {
            mCheckList.add(item);
        }
    }
}
