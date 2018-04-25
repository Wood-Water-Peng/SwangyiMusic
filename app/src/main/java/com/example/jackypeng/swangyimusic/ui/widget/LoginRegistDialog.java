package com.example.jackypeng.swangyimusic.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.jackypeng.swangyimusic.R;

/**
 * Created by pj on 2016/11/18.
 */
public class LoginRegistDialog extends Dialog {
    private TextView tv_tip;

    public LoginRegistDialog(Context context) {
        this(context,0);
    }

    public LoginRegistDialog(Context context, int themeResId) {
        super(context, R.style.Theme_Transparent);
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_regist_login, null);
        tv_tip = (TextView) contentView.findViewById(R.id.tv_regist_login_tip);
        setContentView(contentView);
    }

    public void setTextTip(String text) {
        tv_tip.setText(text);
    }


}
