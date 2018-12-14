package cn.books.activity.borrowingmangement.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
import cn.books.activity.borrowingmangement.ui.BorrowingAddActivity;
import cn.books.db.Books;
import cn.books.db.Borrowing;
import cn.books.utils.ToastUtil;
import cn.books.view.IOSDialog;
import cn.books.view.IOSDialogUtils;

/**
 * Created by 蒋 on 2018/9/22.
 * 借阅列表适配器
 */

public class BorrowingItemAdapter extends RecyclerView.Adapter<BorrowingItemAdapter.ViewHolder> {

    private Context mContext;
    private List<Borrowing> mList;
    private RequestOptions requestOptions;

    public BorrowingItemAdapter(Context context, List<Borrowing> mList) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.boorrowing_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Borrowing bean = mList.get(position);

        Glide.with(mContext)
                .load(bean.getBook_image_path())
                .apply(requestOptions)
                .into(holder.ivBookImage);

        holder.tvBookName.setText(bean.getBook_name());

        holder.tvBookBorrower.setText(bean.getStuden_name());

        holder.tvBorrowerTime.setText(bean.getStu_borr_time());

        switch (bean.getStatus()) {
            case 1:
                holder.tvBookStatus.setText("借阅中");
                holder.tvBookStatus.setTextColor(ContextCompat.getColor(mContext, R.color.green));

                break;
            case 2:
                holder.tvBookStatus.setText("已归还");
                holder.tvBookStatus.setTextColor(ContextCompat.getColor(mContext, R.color.blue));

                break;
            case 3:
                holder.tvBookStatus.setText("已逾期");
                holder.tvBookStatus.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                break;
            default:
                break;
        }

        holder.llLayout.setOnLongClickListener(v -> {
            if (bean.getStatus() == 1) {
                GuihuanBokks(bean);
            }
            return true;
        });

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
        @BindView(R.id.tv_book_borrower)
        TextView tvBookBorrower;
        @BindView(R.id.tv_borrower_time)
        TextView tvBorrowerTime;
        @BindView(R.id.tv_book_status)
        TextView tvBookStatus;
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
    private void StartEditorUI(Borrowing e) {

        Intent intent = new Intent(mContext, BorrowingAddActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Borrowing", e);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private void DeleteUser(final Borrowing e) {
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

                switch (e.getStatus()) {
                    case 1:
                        mList.addAll(LitePal.where("status = ?", "1").find(Borrowing.class));
                        break;
                    case 2:
                        mList.addAll(LitePal.where("status = ?", "2").find(Borrowing.class));
                        break;
                    case 3:
                        mList.addAll(LitePal.where("status = ?", "3").find(Borrowing.class));
                        break;
                    default:
                        break;
                }

                notifyDataSetChanged();
            }
        });

    }

    private void GuihuanBokks(final Borrowing e) {
        IOSDialogUtils utils = IOSDialogUtils.getInstance();
        utils.showDialogIos(mContext, "是否归还?", "取消", "确定", false);
        utils.setOnButtonClickListener(new IOSDialogUtils.OnButtonClickListener() {
            @Override
            public void onCancelButtonClick(IOSDialog dialog) {

                dialog.dismiss();
            }

            @Override
            public void onPositiveButtonClick(IOSDialog dialog) {
                dialog.dismiss();

                e.setStatus(2);
                e.update(e.getId());
                ToastUtil.showToast("归还成功!");

                mList.clear();
                mList.addAll(LitePal.where("status = ?", "1").find(Borrowing.class));
                notifyDataSetChanged();
            }
        });

    }

}
