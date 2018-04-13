package com.abt.statusbardemo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.abt.statusbardemo.LauncherActivity;

/**
 * @描述：     @BootBroadcastReceiver
 * @作者：     @黄卫旗
 * @创建时间： @2018-04-04
 */
public class BootBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override  
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ACTION_BOOT)) {
            final Intent bootIntent = new Intent(context, LauncherActivity.class);
            bootIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(bootIntent);
        }
    }

}
