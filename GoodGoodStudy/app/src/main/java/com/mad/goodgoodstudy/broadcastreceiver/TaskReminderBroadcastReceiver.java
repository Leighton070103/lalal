package com.mad.goodgoodstudy.broadcastreceiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;

import com.mad.goodgoodstudy.R;
import com.mad.goodgoodstudy.model.TaskRecord;
import com.mad.goodgoodstudy.monitor.NoiseMonitor;
import com.mad.goodgoodstudy.service.ManageTaskService;
import com.mad.goodgoodstudy.service.TaskExecutionService;

import static com.mad.goodgoodstudy.activity.EditTaskActivity.TASK_ID;
import static com.mad.goodgoodstudy.service.ManageTaskService.RECORD_ID;
import static com.mad.goodgoodstudy.service.ManageTaskService.REMINDER_TYPE;
import static com.mad.goodgoodstudy.service.ManageTaskService.START_TASK;
import static com.mad.goodgoodstudy.service.ManageTaskService.STOP_TASK;

public class TaskReminderBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        long id = intent.getLongExtra(RECORD_ID, -1);
        if( id != -1 ){
            TaskRecord taskRecord = TaskRecord.findById(TaskRecord.class, id);
            switch ( intent.getIntExtra(REMINDER_TYPE, -1)){
                case START_TASK:
                   // showReminder(context, taskRecord, context.getResources().getString(
                       //     R.string.is_started));
                    Intent serviceIntent = new Intent(context, TaskExecutionService.class);
                    context.startService( serviceIntent );
                    Log.d("===TaskReminder", taskRecord.getTask().getName() + " starts!");
                    break;
                case STOP_TASK:
                   // showReminder(context, taskRecord, context.getResources().getString(
                         //   R.string.is_complete));
                    Intent taskIntent = new Intent(context, ManageTaskService.class);
                    context.startService( taskIntent );
                    Log.d("===TaskReminder", taskRecord.getTask().getName() + " completed!");
                    break;
            }
        }
    }
}
