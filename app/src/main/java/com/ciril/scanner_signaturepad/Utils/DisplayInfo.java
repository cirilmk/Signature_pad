package com.ciril.scanner_signaturepad.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

public class DisplayInfo {

    public static Toast mToast;



    public static void showShortToast(final Context ctx, final int stringResId) {
        if (ctx != null)
            ((Activity) ctx).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dismissToast();
                    mToast = Toast.makeText(ctx, stringResId, Toast.LENGTH_SHORT);
                    mToast.show();
                }
            });
    }

    public static void dismissToast() {
        if (mToast != null)
            mToast.cancel();
    }

}
