package cn.books.activity.borrowingmangement.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.books.R;
import cn.books.activity.booksmanagement.bookschoose.BooksBean;
import cn.books.activity.booksmanagement.bookschoose.BooksChooseActivity;
import cn.books.activity.user.UserBean;
import cn.books.activity.user.UserChooseActivity;
import cn.books.base.BaseActivity;
import cn.books.db.Books;
import cn.books.db.Borrowing;
import cn.books.db.Students;
import cn.books.utils.DateUtils;
import cn.books.utils.SPUtils;
import cn.books.utils.ToastUtil;

public class BorrowingAddActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_book_cover)
    ImageView ivBookCover;
    @BindView(R.id.iv_book_image)
    ImageView ivBookImage;
    @BindView(R.id.iv_stu_cover)
    ImageView ivStuCover;
    @BindView(R.id.tv_stu_name)
    TextView tvStuName;
    @BindView(R.id.iv_time_cover)
    ImageView ivTimeCover;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_agent)
    TextView tvAgent;
    @BindView(R.id.btn_book_add)
    Button btnBookAdd;

    private Borrowing mBorrowing = null;

    private RequestOptions requestOptions;

    @Override
    protected void initVariables() {
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                mBorrowing = (Borrowing) bundle.getSerializable("Borrowing");
            }
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_borrowing_add);
        ButterKnife.bind(this);
        initToolbarNav();
        setTitle("新增借阅");
        tvAgent.setText("经 办 人:" + SPUtils.getInstance().getString(SPUtils.USER_NAME));
    }

    @Override
    protected void initData() {
        requestOptions = new RequestOptions()
                .placeholder(R.drawable.img_book1)
                .error(R.drawable.img_book1);

        if (mBorrowing != null) {

            //图片
            ivBookCover.setVisibility(View.GONE);
            ivBookImage.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(mBorrowing.getBook_image_path())
                    .apply(requestOptions)
                    .into(ivBookImage);

            BooksBean booksBean = new BooksBean();
            mBookListS.add(booksBean);


            //用户
            ivStuCover.setVisibility(View.GONE);
            tvStuName.setVisibility(View.VISIBLE);
            tvStuName.setText(mBorrowing.getStuden_name());

            UserBean userBean = new UserBean();
            userBean.setStuid(mBorrowing.getStuden_id());
            userBean.setStu_name(mBorrowing.getStuden_name());
            mCheckListS.add(userBean);


            //归还时间
            ivTimeCover.setVisibility(View.GONE);
            tvTime.setVisibility(View.VISIBLE);
            tvTime.setText(mBorrowing.getStu_return_time());

            //经办人
            tvAgent.setText("经 办 人:" + mBorrowing.getAdd_admin_name());

            btnBookAdd.setText("修 改");
        }

    }

    @OnClick({R.id.iv_book_cover, R.id.iv_book_image, R.id.iv_stu_cover, R.id.tv_stu_name, R.id.iv_time_cover, R.id.tv_time, R.id.btn_book_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_book_cover://选择图书
                if (mBorrowing == null) {
                    Intent Book1intent = new Intent(this, BooksChooseActivity.class);
                    Book1intent.putExtra("mChooseNums", "single");
                    startActivityForResult(Book1intent, 99);
                } else {
                    ToastUtil.showToast("图片不能修改!");
                }
                break;
            case R.id.iv_book_image://选择图书重选

                if (mBorrowing == null) {
                    Intent Book1intent = new Intent(this, BooksChooseActivity.class);
                    Book1intent.putExtra("mChooseNums", "single");
                    startActivityForResult(Book1intent, 99);
                } else {
                    ToastUtil.showToast("图片不能修改!");
                }
                break;
            case R.id.iv_stu_cover://选择学生
                Intent Stu1intent = new Intent(this, UserChooseActivity.class);
                Stu1intent.putExtra("mChooseNums", "single");
                startActivityForResult(Stu1intent, 100);
                break;
            case R.id.tv_stu_name://选择学生重选
                Intent Stu2intent = new Intent(this, UserChooseActivity.class);
                Stu2intent.putExtra("mChooseNums", "single");
                startActivityForResult(Stu2intent, 100);
                break;
            case R.id.iv_time_cover://选择时间
                ShowSelectDate();
                break;
            case R.id.tv_time://选择时间重选
                ShowSelectDate();
                break;
            case R.id.btn_book_add://新增
                CheckData();
                break;
            default:
                break;
        }
    }

    private List<UserBean> mCheckListS = new ArrayList<>();
    private List<BooksBean> mBookListS = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //选择图书
        if (requestCode == 99 && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                mBookListS.clear();
                mBookListS.addAll((List<BooksBean>) bundle.getSerializable("mCheckList"));
                if (mBookListS.size() > 0) {
                    ivBookCover.setVisibility(View.GONE);
                    ivBookImage.setVisibility(View.VISIBLE);

                    Glide.with(this)
                            .load(mBookListS.get(0).getBooks().getBook_image_path())
                            .apply(new RequestOptions()
                                    .placeholder(R.drawable.img_book1)
                                    .error(R.drawable.img_book1))
                            .into(ivBookImage);
                }
            }
        }

        //选择的用户回调 单
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                mCheckListS.clear();
                mCheckListS.addAll((List<UserBean>) bundle.getSerializable("mCheckList"));
                if (mCheckListS.size() > 0) {
                    ivStuCover.setVisibility(View.GONE);
                    tvStuName.setVisibility(View.VISIBLE);

                    tvStuName.setText(mCheckListS.get(0).getStu_name());
                    if (mCheckListS.get(0).getStu_sex().equals("女")) {
                        tvStuName.setBackgroundResource(R.drawable.shape_rectangle_pink_bg);
                    } else {
                        tvStuName.setBackgroundResource(R.drawable.shape_rectangle_blue_bg);
                    }
                }
            }
        }
    }

    /**
     * 选择日期
     */
    private boolean[] Datetype = new boolean[]{true, true, true, true, true, false};//显示类型 默认全部显示
    private String dateS = "";

    private void ShowSelectDate() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(this, (date, v) -> {//选中事件回调
            dateS = getDate(date);
            ivTimeCover.setVisibility(View.GONE);
            tvTime.setVisibility(View.VISIBLE);
            tvTime.setText(dateS);
        })//默认全部显示
                .setContentSize(20)//滚轮文字大小
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setType(Datetype)
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return
     */
    public String getDate(Date date) {
        //可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    /**
     * 新增
     */
    private void CheckData() {
        if (mBookListS.size() == 0) {
            ToastUtil.showToast("请选择图书!");
            return;
        }
        if (mCheckListS.size() == 0) {
            ToastUtil.showToast("请选择借阅人!");
            return;
        }
        if (TextUtils.isEmpty(tvTime.getText().toString())) {
            ToastUtil.showToast("请选择归还时间!");
            return;
        }


        if (mBorrowing == null) {

            Borrowing borrowing = new Borrowing();
            //设置图书
            Books books = mBookListS.get(0).getBooks();

            borrowing.setBook_id(books.getId());
            borrowing.setBook_name(books.getBook_name());
            borrowing.setBook_image_path(books.getBook_image_path());

            //设置学生
            UserBean userBean = mCheckListS.get(0);
            borrowing.setStuden_id(userBean.getStuid());
            borrowing.setStuden_name(userBean.getStu_name());

            //归还时间
            borrowing.setStu_borr_time(DateUtils.getDate());//借阅开始时间 当前时间
            borrowing.setStu_return_time(tvTime.getText().toString());

            //借阅状态
            borrowing.setStatus(1);

            //经办人
            borrowing.setAdd_admin_id(SPUtils.getInstance().getInt(SPUtils.USER_ID, 0));
            borrowing.setAdd_admin_name(SPUtils.getInstance().getString(SPUtils.USER_NAME));

            borrowing.save();
            ToastUtil.showToast("新增成功!");
            finish();
        } else {

            //设置学生
            UserBean userBean = mCheckListS.get(0);
            mBorrowing.setStuden_id(userBean.getStuid());
            mBorrowing.setStuden_name(userBean.getStu_name());

            //归还时间
            mBorrowing.setStu_borr_time(DateUtils.getDate());//借阅开始时间 当前时间
            mBorrowing.setStu_return_time(tvTime.getText().toString());

            //借阅状态
            mBorrowing.setStatus(1);

            //经办人
            mBorrowing.setAdd_admin_id(SPUtils.getInstance().getInt(SPUtils.USER_ID, 0));
            mBorrowing.setAdd_admin_name(SPUtils.getInstance().getString(SPUtils.USER_NAME));

            mBorrowing.update(mBorrowing.getId());
            ToastUtil.showToast("修改成功!");
            finish();
        }


    }

}
