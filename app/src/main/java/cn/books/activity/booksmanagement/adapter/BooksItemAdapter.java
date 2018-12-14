package cn.books.activity.booksmanagement.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.books.R;
import cn.books.activity.booksmanagement.ui.BooksAddActivity;
import cn.books.db.Books;

import cn.books.utils.ToastUtil;
import cn.books.view.IOSDialog;
import cn.books.view.IOSDialogUtils;

/**
 * Created by 蒋 on 2018/9/22.
 * 学生列表适配器
 */

public class BooksItemAdapter extends RecyclerView.Adapter<BooksItemAdapter.ViewHolder> {

    private Context mContext;
    private List<Books> mList;
    private RequestOptions requestOptions;

    public BooksItemAdapter(Context context, List<Books> mList) {
        this.mContext = context;
        this.mList = mList;
        requestOptions = new RequestOptions()
                .placeholder(R.drawable.img_book1)
                .error(R.drawable.img_book1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.book_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Books bean = mList.get(position);

        Glide.with(mContext)
                .load(bean.getBook_image_path())
                .apply(requestOptions)
                .into(holder.ivBookImage);

        holder.tvBookName.setText(bean.getBook_name());

        holder.tvBookAuthor.setText(bean.getBook_author());

        holder.tvBookType.setText(bean.getBook_type());

        holder.tvBookIntroduce.setText(bean.getBook_introduce());

        holder.llLayout.setOnClickListener(v -> StartEditorUI(bean));

        holder.ivEditor.setOnClickListener(v -> StartEditorUI(bean));

        holder.ivDelete.setOnClickListener(v -> DeleteUser(bean));
    }

    @Override
    public int getItemCount() {
        return mList.size();
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
        @BindView(R.id.ll_layout)
        LinearLayout llLayout;
        @BindView(R.id.iv_editor)
        ImageView ivEditor;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 启动到编辑界面
     *
     * @param e
     */
    private void StartEditorUI(Books e) {
        Intent intent = new Intent(mContext, BooksAddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Books", e);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private void DeleteUser(final Books e) {
        IOSDialogUtils utils = IOSDialogUtils.getInstance();
        utils.showDialogIos(mContext, "是否删除?", "取消", "确定", false);
        utils.setOnButtonClickListener(new IOSDialogUtils.OnButtonClickListener() {
            @Override
            public void onCancelButtonClick(IOSDialog dialog) {

                dialog.dismiss();
            }

            @Override
            public void onPositiveButtonClick(IOSDialog dialog) {
                dialog.dismiss();

                e.delete();
                ToastUtil.showToast("删除成功");

                mList.clear();
                mList.addAll(LitePal.order("id desc").find(Books.class));
                notifyDataSetChanged();
            }
        });

    }


}
