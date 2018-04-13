package com.pi.basic.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @描述： @屏幕截取工具类
 * @作者： @蒋诗朋
 * @创建时间： @2017-11-25
 */
public class ScreenShotUtil {
    private static Bitmap takeScreenShot(Activity activity) {
        // View是你需要截图的View
        View view           = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1           = view.getDrawingCache();

        // 获取状态栏高度
        Rect frame          = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay()
                .getHeight();
        // 去掉标题栏
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    private static void savePic(Bitmap b, File filePath) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
        } catch (IOException e) {
            // e.printStackTrace();
        }

    }

    /**
     * 截图
     * @param a
     * @param filePath
     */
    public static final void shoot(Activity a, File filePath) {
        if (filePath == null) {
            return;
        }
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }
        savePic(takeScreenShot(a), filePath);
    }

    /**
     * 获取当前屏幕截图
     * @param filePath
     * @return
     */
    public static final Drawable getScreenDrawble(String filePath){
        final File file = new File(filePath);
        if(file.exists()){
            final Bitmap bitmap;
            bitmap = BitmapFactory.decodeFile(filePath);
            return new BitmapDrawable(bitmap);
        }
        return null;
    }

}
