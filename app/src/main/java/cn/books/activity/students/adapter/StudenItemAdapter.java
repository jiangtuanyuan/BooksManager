package cn.books.activity.students.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.books.R;
import cn.books.activity.students.ui.StudenADDActivity;
import cn.books.db.Students;
import cn.books.utils.ToastUtil;

/**
 * Created by 蒋 on 2018/9/22.
 * 学生列表适配器
 */

public class StudenItemAdapter extends RecyclerView.Adapter<StudenItemAdapter.ViewHolder> {
    private Context mContext;
    private List<Students> mList;

    public StudenItemAdapter(Context context, List<Students> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.studen_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Students bean = mList.get(position);

        holder.tvLeftName.setText(bean.getStu_name().substring(bean.getStu_name().length() - 1, bean.getStu_name().length()));

        holder.tvName.setText(bean.getStu_name());

        holder.tvClass.setText(bean.getStu_class());


        if (bean.getStu_sex().equals("女")) {
            holder.tvSex.setBackgroundResource(R.drawable.ic_sex_famale);
            holder.tvLeftName.setBackgroundResource(R.drawable.shape_rectangle_pink_bg);

        } else {
            holder.tvSex.setBackgroundResource(R.drawable.ic_sex_male);
            holder.tvLeftName.setBackgroundResource(R.drawable.shape_rectangle_blue_bg);
        }

        holder.tvNumber.setText(bean.getStu_number());


        holder.mLllayout.setOnClickListener(v -> StartEditorUI(bean));

        holder.btEditor.setOnClickListener(v -> StartEditorUI(bean));

        holder.btDelete.setOnClickListener(v -> DeleteUser(bean));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_left_name)
        TextView tvLeftName;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_class)
        TextView tvClass;
        @BindView(R.id.tv_sex)
        ImageView tvSex;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.bt_editor)
        ImageView btEditor;
        @BindView(R.id.bt_delete)
        ImageView btDelete;
        @BindView(R.id.ll_layout)
        LinearLayout mLllayout;

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
    private void StartEditorUI(Students e) {
        Intent intent = new Intent(mContext, StudenADDActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Students", e);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    private void DeleteUser(final Students e) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("删除之后不可恢复!");
        builder.setTitle("是否删除?");

        builder.setPositiveButton("确定", (dialog, which) -> {
            dialog.dismiss();
            e.delete();
            ToastUtil.showToast("删除成功");

            mList.clear();
            mList.addAll(LitePal.order("id desc").find(Students.class));
            notifyDataSetChanged();
        });
        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

}
