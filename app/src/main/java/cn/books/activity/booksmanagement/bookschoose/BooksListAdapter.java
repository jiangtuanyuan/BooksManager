package cn.books.activity.booksmanagement.bookschoose;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.books.R;
import cn.books.activity.user.UserBean;
import cn.books.utils.ToastUtil;

/**
 * Created by 蒋 on 2018/7/9.
 * 用户列表
 */

public class BooksListAdapter extends RecyclerView.Adapter<BooksListAdapter.ViewHolder> {
    private List<BooksBean> userList;
    private Context mContext;
    private LayoutInflater mInflater;
    private RequestOptions requestOptions;

    private boolean single = false;//是否只能选择一个 默认否

    private int ischeckUser = 0;//没用 选择用 0 或者 1

    private boolean isall = false;//是否可以都选一个 这里再添加考勤选择员工用到

    public void setIsall(boolean isall) {
        this.isall = isall;
    }

    public void setIscheckUser(int ischeckUser) {
        this.ischeckUser = ischeckUser;
    }

    public BooksListAdapter(Context mContext, List<BooksBean> userList, boolean single) {
        this.userList = userList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.single = single;

        requestOptions = new RequestOptions()
                .placeholder(R.drawable.img_book1)
                .error(R.drawable.img_book1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.book_choose_list_item, parent, false);
        ViewHolder contentViewHolder = new ViewHolder(contentView);
        return contentViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final BooksBean booksBean = userList.get(position);

        //图片
        Glide.with(mContext)
                .load(booksBean.getBooks().getBook_image_path())
                .apply(requestOptions)
                .into(viewHolder.ivBookImage);


        viewHolder.tvBookName.setText(booksBean.getBooks().getBook_name());

        viewHolder.tvBookAuthor.setText(booksBean.getBooks().getBook_author());

        viewHolder.tvBookType.setText(booksBean.getBooks().getBook_type());

        viewHolder.tvBookIntroduce.setText(booksBean.getBooks().getBook_introduce());


        viewHolder.cbCheckbox.setChecked(booksBean.isChecked());

        viewHolder.itemView.setOnClickListener(v -> {
            try {

                if (booksBean.isChecked()) {

                    ischeckUser = 0;
                    viewHolder.cbCheckbox.setChecked(false);
                    booksBean.setChecked(false);

                } else {
                    //这里需要深深的思考一下
                    if (single) {
                        if (ischeckUser == 1) {
                            ToastUtil.showToast("只能选择一个!");
                        } else {
                            ischeckUser = 1;
                            viewHolder.cbCheckbox.setChecked(true);
                            booksBean.setChecked(true);
                        }
                    } else {
                        viewHolder.cbCheckbox.setChecked(true);
                        booksBean.setChecked(true);
                    }
                }
                mItemClickListener.onItemClick(position, booksBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_book_image)
        ImageView ivBookImage;
        @BindView(R.id.tv_book_name)
        TextView tvBookName;
        @BindView(R.id.tv_book_author)
        TextView tvBookAuthor;
        @BindView(R.id.tv_book_type)
        TextView tvBookType;
        @BindView(R.id.tv_book_introduce)
        TextView tvBookIntroduce;
        @BindView(R.id.cb_checkbox)
        CheckBox cbCheckbox;
        View itemView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            itemView = view;
        }
    }

    private ItemClickListener mItemClickListener;

    public void setmItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position, BooksBean item);
    }
}
