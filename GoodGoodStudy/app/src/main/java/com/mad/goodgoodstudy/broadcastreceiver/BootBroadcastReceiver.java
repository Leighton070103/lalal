package com.mad.goodgoodstudy.broadcastreceiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;

import com.mad.goodgoodstudy.service.ManageTaskService;

public class BootBroadcastReceiver extends BroadcastReceiver {
    public static final String APP_NAME = "Good good study";
    public static final String BOOT = "Start up";
    static final String action_boot="android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
            Log.d( APP_NAME, BOOT );
            Intent startManageTaskService = new Intent(context, ManageTaskService.class);
            context.startService( startManageTaskService );
//            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setTitle("Reminder")
//                    .setMessage( "kaijiqidongle");
//            AlertDialog dialog = builder.create();
//            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//            dialog.show();


    }
}
