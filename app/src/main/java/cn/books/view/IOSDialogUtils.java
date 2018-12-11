package cn.books.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cn.books.R;


/**
 * Created by caoyu on 2017/12/2/002.
 */

public class IOSDialogUtils {
    public static IOSDialogUtils IOSDialogUtils;

    public static IOSDialogUtils getInstance() {
        if (IOSDialogUtils == null) {
            IOSDialogUtils = new IOSDialogUtils();
        }
        return IOSDialogUtils;
    }


    public void clickDialogIos(Context context, String msg, String ok) {
        final IOSDialog dialog = new IOSDialog(context, R.style.customDialog, R.layout.iosdialog);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvOk.setText(ok);
        tvOk.setOnClickListener(v -> onButtonClickListener.onPositiveButtonClick(dialog));
        tvCancel.setOnClickListener(v -> onButtonClickListener.onCancelButtonClick(dialog));
    }

    /**
     * 是否显示取消按钮
     *
     * @param context
     * @param msg
     * @param ok
     * @param cancle
     */
    public void clickDialogIos(Context context, String msg, String ok, boolean cancle) {
        final IOSDialog dialog = new IOSDialog(context, R.style.customDialog, R.layout.iosdialog);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvOk.setText(ok);
        tvOk.setOnClickListener(v -> onButtonClickListener.onPositiveButtonClick(dialog));
        if (cancle) {
            tvCancel.setVisibility(View.GONE);
        }
        tvCancel.setOnClickListener(v -> onButtonClickListener.onCancelButtonClick(dialog));
    }

    public void showDialogIos(Context context, String msg) {
        final IOSDialog dialog = new IOSDialog(context, R.style.customDialog, R.layout.iosdialog);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvOk.setVisibility(View.GONE);
        tvCancel.setText("确定");
        tvCancel.setOnClickListener(v -> onButtonClickListener.onCancelButtonClick(dialog));
    }

    private static OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    /**
     * 点击返回键和其它地方 弹框不可消失 (一个消息内容和一个确定按钮的消息框)
     * msg: 消息内容
     * tf： 是否可以点击其它其它地方消失
     */
    public void showDialogIos(Context context, String msg, boolean tf) {
        final IOSDialog dialog = new IOSDialog(context, R.style.customDialog, R.layout.iosdialog);
        dialog.setCancelable(tf);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvCancel.setVisibility(View.GONE);
        tvOk.setText("确定");
        tvOk.setOnClickListener(v -> onButtonClickListener.onPositiveButtonClick(dialog));
    }

    /**
     * 自定义消息内容，两个按钮的名称和是否可以点击其它地方消失弹框
     */
    public void showDialogIos(Context context, String msg, String Cancel, String Ok, boolean tf) {
        final IOSDialog dialog = new IOSDialog(context, R.style.customDialog, R.layout.iosdialog);
        dialog.setCancelable(tf);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        tvCancel.setText(Cancel);
        tvOk.setText(Ok);
        tvOk.setOnClickListener(v -> onButtonClickListener.onPositiveButtonClick(dialog));
        tvCancel.setOnClickListener(v -> onButtonClickListener.onCancelButtonClick(dialog));
    }


    /**
     * 按钮点击回调接口
     */
    public interface OnButtonClickListener {
        /**
         * 确定按钮点击回调方法
         *
         * @param dialog 当前 AlertDialog，传入它是为了在调用的地方对 dialog 做操作，比如 dismiss()
         *               也可以在该工具类中直接  dismiss() 掉，就不用将 AlertDialog 对象传出去了
         */
        void onPositiveButtonClick(IOSDialog dialog);

        /**
         * 取消按钮点击回调方法
         *
         * @param dialog 当前AlertDialog
         */
        void onCancelButtonClick(IOSDialog dialog);
    }
}
