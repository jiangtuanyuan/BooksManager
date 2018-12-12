package cn.books.activity.students.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.books.R;
import cn.books.base.BaseActivity;
import cn.books.db.Students;
import cn.books.utils.SPUtils;
import cn.books.utils.ToastUtil;

public class StudenADDActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_stu_number)
    EditText etStuNumber;
    @BindView(R.id.iv_stu_number_clear)
    ImageView ivStuNumberClear;
    @BindView(R.id.et_stu_name)
    EditText etStuName;
    @BindView(R.id.iv_stu_name_clear)
    ImageView ivStuNameClear;
    @BindView(R.id.et_stu_class)
    EditText etStuClass;
    @BindView(R.id.iv_stu_class_clear)
    ImageView ivStuClassClear;
    @BindView(R.id.et_stu_tel)
    EditText etStuTel;
    @BindView(R.id.iv_stu_tel_clear)
    ImageView ivStuTelClear;
    @BindView(R.id.rb_sex_male)
    RadioButton rbSexMale;
    @BindView(R.id.rb_sex_famale)
    RadioButton rbSexFamale;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.bt_register)
    Button btRegister;

    private Students students = null;

    @Override
    protected void initVariables() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                students = (Students) bundle.getSerializable("Students");
            }
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_studen_add);
        ButterKnife.bind(this);
        initToolbarNav();
    }

    @Override
    protected void initData() {
        if (students != null) {
            etStuNumber.setText(students.getStu_number());
            etStuName.setText(students.getStu_name());
            etStuClass.setText(students.getStu_class());
            etStuTel.setText(students.getStu_tel());
            if (students.getStu_sex().equals("女")) {
                rbSexFamale.setChecked(true);
            } else {
                rbSexMale.setChecked(true);
            }
            btRegister.setText("修 改");
        }

    }

    @OnClick(R.id.bt_register)
    public void onViewClicked() {
        isCheck();
    }

    private void isCheck() {
        if (TextUtils.isEmpty(etStuNumber.getText().toString())) {
            ToastUtil.showToast("学号不能为空!");
            return;
        }
        if (TextUtils.isEmpty(etStuName.getText().toString())) {
            ToastUtil.showToast("姓名不能为空!");
            return;
        }

        if (TextUtils.isEmpty(etStuClass.getText().toString())) {
            ToastUtil.showToast("班级不能为空!");
            return;
        }
        if (TextUtils.isEmpty(etStuTel.getText().toString())) {
            ToastUtil.showToast("手机号码不能为空!");
            return;
        }
        if (etStuTel.getText().toString().length() != 11) {
            ToastUtil.showToast("手机号码必须位11位数!");
            return;
        }

        if (students == null) {
            Students mstudents = new Students();
            mstudents.setStu_number(etStuNumber.getText().toString());//学号

            mstudents.setStu_name(etStuName.getText().toString());//姓名

            mstudents.setStu_class(etStuClass.getText().toString());//班级

            mstudents.setStu_tel(etStuTel.getText().toString());//手机

            if (rbSexFamale.isChecked()) {
                mstudents.setStu_sex("女");
            } else {
                mstudents.setStu_sex("男");
            }
            mstudents.setAdd_admin_id(SPUtils.getInstance().getInt(SPUtils.USER_ID, 0));
            mstudents.setAdd_admin_name(SPUtils.getInstance().getString(SPUtils.USER_NAME));
            mstudents.save();
            ToastUtil.showToast("新增成功!");
            finish();
        } else {
            //修改
            students.setStu_number(etStuNumber.getText().toString());//学号
            students.setStu_name(etStuName.getText().toString());//姓名
            students.setStu_class(etStuClass.getText().toString());//班级
            students.setStu_tel(etStuTel.getText().toString());//手机
            if (rbSexFamale.isChecked()) {
                students.setStu_sex("女");
            } else {
                students.setStu_sex("男");
            }
            students.setAdd_admin_id(SPUtils.getInstance().getInt(SPUtils.USER_ID, 0));
            students.setAdd_admin_name(SPUtils.getInstance().getString(SPUtils.USER_NAME));

            students.update(students.getId());

            ToastUtil.showToast("修改成功!");
            finish();
        }
    }

}
