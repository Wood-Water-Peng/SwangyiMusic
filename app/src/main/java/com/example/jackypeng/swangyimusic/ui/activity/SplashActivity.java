package com.example.jackypeng.swangyimusic.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.jackypeng.swangyimusic.R;
import com.example.jackypeng.swangyimusic.util.PermissionHelper;

/**
 * Created by jackypeng on 2018/3/18.
 */

public class SplashActivity extends Activity {
    private PermissionHelper mPermissionHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initPermission();
    }

    private void initPermission() {
        mPermissionHelper = new PermissionHelper(this);
        mPermissionHelper.setOnApplyPermissionListener(new PermissionHelper.OnApplyPermissionListener() {
            @Override
            public void onAfterApplyAllPermission() {
                runApp();
            }
        });
        if (Build.VERSION.SDK_INT < 23) {
            runApp();
        } else {
            if (mPermissionHelper.isAllRequestedPermissionGranted()) {
                runApp();
            } else {
                mPermissionHelper.applyPermissions();
            }
        }
    }

    private void runApp() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionHelper.onActivityResult(requestCode, resultCode, data);
    }

}
