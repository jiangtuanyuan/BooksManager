package cn.books.activity.booksmanagement.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.books.R;
import cn.books.activity.booksmanagement.adapter.BooksItemAdapter;
import cn.books.activity.students.adapter.StudenItemAdapter;
import cn.books.activity.students.ui.StudenADDActivity;
import cn.books.activity.user.UserBean;
import cn.books.base.BaseActivity;
import cn.books.db.Books;
import cn.books.db.Students;
import cn.books.utils.ETChangedUtlis;
import cn.books.utils.ToastUtil;

public class BooksMainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.search_edt)
    EditText mSearchEdt;
    @BindView(R.id.search_edt_clear)
    ImageView mSearchEdtClear;
    @BindView(R.id.search_img)
    ImageView searchImg;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.recycler_view)
    RecyclerView mRecycler;
    @BindView(R.id.book_add)
    LinearLayout studentsAdd;

    private BooksItemAdapter adapter;

    private List<Books> mList = new ArrayList<>();//显示的数据

    private List<Books> mSumList = new ArrayList<>();//总数据

    @Override
    protected void onResume() {
        super.onResume();

        mSumList.clear();
        mSumList.addAll(LitePal.order("id desc").find(Books.class));
        mSearch = "";
        Search();
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_books_main);
        ButterKnife.bind(this);
        setTitle("图书管理");
        initToolbarNav();
        setEmployeesRecyclerw();

        ETChangedUtlis.EditTextChangedListener(mSearchEdt, mSearchEdtClear);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {

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

    /**
     * 设置列表
     */
    private void setEmployeesRecyclerw() {
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BooksItemAdapter(this, mList);
        mRecycler.setAdapter(adapter);
        mRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @OnClick(R.id.book_add)
    public void onViewClicked() {
        startActivity(new Intent(this, BooksAddActivity.class));
    }

    /**
     * 在总数据里面搜索用户名mSumList
     */
    private String mSearch = "";

    private void Search() {
        mList.clear();
        if (!TextUtils.isEmpty(mSearch)) {
            for (Books book : mSumList) {
                if (book.getBook_name().contains(mSearch)
                        || book.getBook_type().contains(mSearch)
                        || book.getBook_author().contains(mSearch)
                        ) {
                    mList.add(book);
                }
            }
        } else {
            mList.addAll(mSumList);
        }

        if (mList.size() == 0) {
            tvNodata.setVisibility(View.VISIBLE);
        } else {
            tvNodata.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();

    }

}
