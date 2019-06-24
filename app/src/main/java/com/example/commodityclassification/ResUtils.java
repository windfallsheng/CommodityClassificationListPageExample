package com.example.commodityclassification;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by luzhaosheng
 */
public class ResUtils {

    public static String getJsonStr(Context context, String fileName) throws IOException {
        InputStream in = context.getResources().getAssets().open(fileName);
        int available = in.available();
        byte[] b = new byte[available];
        in.read(b);
        return new String(b, "UTF-8");
    }
}
