package com.example.jackypeng.swangyimusic.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;

/**
 * Created by jackypeng on 2018/5/4.
 */

public class DownloadTipDialog extends AlertDialog {
    public DownloadTipDialog(@NonNull Context context) {
        super(context);
    }

    public DownloadTipDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public DownloadTipDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
