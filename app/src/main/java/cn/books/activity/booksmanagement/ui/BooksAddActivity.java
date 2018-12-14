package cn.books.activity.booksmanagement.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.books.R;
import cn.books.base.BaseActivity;
import cn.books.db.Books;
import cn.books.db.Students;
import cn.books.utils.SPUtils;
import cn.books.utils.ToastUtil;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BooksAddActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.et_book_name)
    EditText etBookName;
    @BindView(R.id.et_book_author)
    EditText etBookAuthor;
    @BindView(R.id.et_book_type)
    EditText etBookType;
    @BindView(R.id.et_book_introduce)
    EditText etBookIntroduce;
    @BindView(R.id.btn_book_add)
    Button btnBookAdd;

    private ArrayList<String> filePaths = new ArrayList<>();
    private RequestOptions requestOptions;

    private Books books = null;

    @Override
    protected void initVariables() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                books = (Books) bundle.getSerializable("Books");
            }
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_books_add);
        ButterKnife.bind(this);
        initToolbarNav();
        setTitle("新增图书");
    }

    @Override
    protected void initData() {
        requestOptions = new RequestOptions()
                .placeholder(R.drawable.img_book1)
                .error(R.drawable.img_book1);

        if (books != null) {
            filePaths.add(books.getBook_image_path());

            Glide.with(this)
                    .load(books.getBook_image_path())
                    .apply(requestOptions)
                    .into(ivCover);

            etBookName.setText(books.getBook_name());
            etBookAuthor.setText(books.getBook_author());
            etBookType.setText(books.getBook_type());
            etBookIntroduce.setText(books.getBook_introduce());

            btnBookAdd.setText("修 改");
        }
    }


    @OnClick({R.id.iv_cover, R.id.btn_book_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_cover://选择封面
                Choosephotos();

                break;
            case R.id.btn_book_add://新增
                CheCkData();
                break;
            default:
                break;
        }
    }

    private void CheCkData() {
        if (filePaths.size() == 0) {
            ToastUtil.showToast("请上传封面!");
            return;
        }
        if (TextUtils.isEmpty(etBookName.getText().toString())) {
            ToastUtil.showToast("请填写书名!");
            return;
        }
        if (TextUtils.isEmpty(etBookAuthor.getText().toString())) {
            ToastUtil.showToast("请填写作者!");
            return;
        }
        if (TextUtils.isEmpty(etBookType.getText().toString())) {
            ToastUtil.showToast("请填写类别!");
            return;
        }
        if (TextUtils.isEmpty(etBookIntroduce.getText().toString())) {
            ToastUtil.showToast("请填写简介!");
            return;
        }
        try {
            if (books == null) {
                Books booksA = new Books();
                booksA.setBook_image_path(filePaths.get(0));
                booksA.setBook_name(etBookName.getText().toString());
                booksA.setBook_author(etBookAuthor.getText().toString());
                booksA.setBook_type(etBookType.getText().toString());
                booksA.setBook_introduce(etBookIntroduce.getText().toString());
                booksA.setAdd_admin_id(SPUtils.getInstance().getInt(SPUtils.USER_ID, 0));
                booksA.setAdd_admin_name(SPUtils.getInstance().getString(SPUtils.USER_NAME));
                booksA.save();
                ToastUtil.showToast("新增成功！");
                finish();
            } else {

                books.setBook_image_path(filePaths.get(0));
                books.setBook_name(etBookName.getText().toString());
                books.setBook_author(etBookAuthor.getText().toString());
                books.setBook_type(etBookType.getText().toString());
                books.setBook_introduce(etBookIntroduce.getText().toString());
                books.setAdd_admin_id(SPUtils.getInstance().getInt(SPUtils.USER_ID, 0));
                books.setAdd_admin_name(SPUtils.getInstance().getString(SPUtils.USER_NAME));
                books.update(books.getId());
                ToastUtil.showToast("修改成功！");
                finish();
            }


        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showToast("新增失败,请重试!");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    filePaths = new ArrayList<>();
                    filePaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                    if (filePaths.size() > 0) {
                        Glide.with(this)
                                .load(filePaths.get(0))
                                .apply(requestOptions)
                                .into(ivCover);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 选择图片发送
     */
    private void Choosephotos() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            FilePickerBuilder.getInstance().setMaxCount(1)
                                    .setSelectedFiles(filePaths)
                                    .setActivityTheme(R.style.AppTheme)
                                    .pickPhoto(BooksAddActivity.this);
                        } else {
                            ToastUtil.showToast("请开启存储权限和媒体相机权限!");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
