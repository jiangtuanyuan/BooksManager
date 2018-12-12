package cn.books.activity.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.books.R;
import cn.books.utils.ToastUtil;

/**
 * Created by 蒋 on 2018/7/9.
 * 用户列表
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private List<UserBean> userList;
    private Context mContext;
    private LayoutInflater mInflater;

    private boolean single = false;//是否只能选择一个 默认否

    private int ischeckUser = 0;//没用 选择用 0 或者 1

    private boolean isall = false;//是否可以都选一个 这里再添加考勤选择员工用到

    public void setIsall(boolean isall) {
        this.isall = isall;
    }

    public void setIscheckUser(int ischeckUser) {
        this.ischeckUser = ischeckUser;
    }

    public UserListAdapter(Context mContext, List<UserBean> userList, boolean single) {
        this.userList = userList;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.single = single;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.user_list_item, parent, false);
        ViewHolder contentViewHolder = new ViewHolder(contentView);
        return contentViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final UserBean userBean = userList.get(position);
        viewHolder.tvLeftName.setText(userBean.getStu_name().substring(userBean.getStu_name().length() - 1, userBean.getStu_name().length()));

        if (userBean.getStu_sex().equals("女")) {
            viewHolder.tvLeftName.setBackgroundResource(R.drawable.shape_rectangle_pink_bg);
        }

        viewHolder.tvNumber.setText(userBean.getStu_number());
        viewHolder.tvName.setText(userBean.getStu_name());
        viewHolder.tvClass.setText(userBean.getStu_class());

        viewHolder.cbCheckbox.setChecked(userBean.isChecked());

        viewHolder.itemView.setOnClickListener(v -> {
            try {

                if (userBean.isChecked()) {

                    ischeckUser = 0;
                    viewHolder.cbCheckbox.setChecked(false);
                    userBean.setChecked(false);

                } else {
                    //这里需要深深的思考一下
                    if (single) {
                        if (ischeckUser == 1) {
                            ToastUtil.showToast("只能选择一个用户!");
                        } else {
                            ischeckUser = 1;
                            viewHolder.cbCheckbox.setChecked(true);
                            userBean.setChecked(true);
                        }
                    } else {
                        viewHolder.cbCheckbox.setChecked(true);
                        userBean.setChecked(true);
                    }
                }
                mItemClickListener.onItemClick(position, userBean);
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
        @BindView(R.id.tv_left_name)
        TextView tvLeftName;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_class)
        TextView tvClass;
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
        void onItemClick(int position, UserBean item);
    }
}
