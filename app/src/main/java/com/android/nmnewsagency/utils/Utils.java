package com.android.nmnewsagency.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

public class Utils {

    public static void setStatusBar(Activity activity){
        // Hide Status Bar
        if (Build.VERSION.SDK_INT < 16) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            View decorView = activity.getWindow().getDecorView();
            // Hide Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
