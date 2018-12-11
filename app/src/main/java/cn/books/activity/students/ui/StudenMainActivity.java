package cn.books.activity.students.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.books.R;
import cn.books.base.BaseActivity;
import cn.books.utils.ToastUtil;

public class StudenMainActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.students_add)
    LinearLayout studentsAdd;

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_studen_main);
        ButterKnife.bind(this);
        setTitle("学生信息管理");
        initToolbarNav();
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.students_add)
    public void onViewClicked() {
        startActivity(new Intent(this, StudenADDActivity.class));
    }

}
