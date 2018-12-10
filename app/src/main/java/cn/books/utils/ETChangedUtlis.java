package cn.books.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 蒋 on 2018/3/20.
 */

public class ETChangedUtlis {
    /**
     * 作用：将文本框和CheckBox绑定起来 文本框有输入内容 就设置checkBox未显示 反之 CheckBox点击就显示密码或者隐产密码
     *
     * @param editText
     * @param checkBox
     */
    public static void EditTextChangedListener(final EditText editText, final CheckBox checkBox) {
        editText.addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (TextUtils.isEmpty(s.toString())) {
                    checkBox.setVisibility(View.GONE);
                } else {
                    checkBox.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    checkBox.setVisibility(View.GONE);
                } else {
                    checkBox.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        }));
//设置checkBox的单击事件
        checkBox.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                // 可视密码输入
                editText.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                // 非可视密码状态
                editText.setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

    }

    /**
     * TextView和ImageView绑定 点击图片清除TV内容 TV内容有改变显示IV
     *
     * @param textView
     * @param imageView
     */

    public static void EditTextChangedListener(final TextView textView, final ImageView imageView) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (TextUtils.isEmpty(s.toString())) {
                    imageView.setVisibility(View.INVISIBLE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    imageView.setVisibility(View.INVISIBLE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        imageView.setOnClickListener(v -> textView.setText(""));
    }

    /**
     * 文本框和图片绑定显示
     *
     * @param editText
     * @param imageView
     */
    public static void EditTextChangedListener(final EditText editText, final ImageView imageView) {
        editText.addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (TextUtils.isEmpty(s.toString())) {
                    imageView.setVisibility(View.INVISIBLE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    imageView.setVisibility(View.INVISIBLE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        }));
        imageView.setOnClickListener(v -> editText.setText(""));

    }


    /**
     * 文本框和图片、按钮绑定显示,底部布局   消息详情模块使用到
     *
     * @param editText
     * @param imageView
     */
    public static void EditTextChangedListener(final EditText editText, final Button button, final ImageView imageView, final LinearLayout layout) {
        editText.addTextChangedListener((new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!TextUtils.isEmpty(s.toString())) {
                    imageView.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    imageView.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                    layout.setVisibility(View.GONE);
                } else {
                    imageView.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        }));
    }

}
