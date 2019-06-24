package com.example.commodityclassification;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;
import android.view.Window;

public class DisplayUtil {

    public static int getStatusBarHeight(Activity context) {
        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        return statusBarHeight;
    }

    public static int getTitleBarHeight(Activity context) {
        int contentTop = context.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
//statusBarHeight是上面所求的状态栏的高度
        int statusBarHeight = getStatusBarHeight(context);
        int titleBarHeight = contentTop - statusBarHeight;
        return titleBarHeight;
    }


}
