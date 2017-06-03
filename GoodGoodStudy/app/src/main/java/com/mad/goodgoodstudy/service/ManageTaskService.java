package com.mad.goodgoodstudy.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import com.mad.goodgoodstudy.application.MyApplication;
import com.mad.goodgoodstudy.broadcastreceiver.TaskReminderBroadcastReceiver;
import com.mad.goodgoodstudy.model.Schedule;
import com.mad.goodgoodstudy.model.TaskRecord;
import com.mad.goodgoodstudy.monitor.NoiseMonitor;
import com.mad.goodgoodstudy.util.TimeUtil;

import java.util.Collections;
import java.util.List;

import static com.mad.goodgoodstudy.model.TaskRecord.getNextTaskRecord;

public class ManageTaskService extends Service {



    public static final int START_REQUEST_CODE = 0;


    public static final String MANAGE_TASK = "====Manage task";
    public static final String RECORD_ID = "Record id";
    public static final String REMINDER_TYPE = "Task reminder type";
    public static final int START_TASK = 0;
    public static final int STOP_TASK = 1;


    public ManageTaskService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    /**
     * Called when the service receive the start intent.
     * Set the current task by calling getNextTaskRecord method.
     * If the current task exists, get it prepared.
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        MyApplication application = (MyApplication) this.getApplication();
        if( application.isCurrentTaskExist() && application.getCurrentTaskRecord().isExecuting()) {
            return START_REDELIVER_INTENT;
        }

        application.setCurrentTaskRecord(TaskRecord.getNextTaskRecord());

        if( application.isCurrentTaskExist() ){
            prepare( application.getCurrentTaskRecord() );
        }
        return START_REDELIVER_INTENT;
    }


    /**
     * Set the start and end alarm manager for the task record.
     * When there comes the specific time, the alarm manager send intent to the
     * reminderbroadcastreceiver.
     * @param taskRecord
     */
    public void prepare(TaskRecord taskRecord) {

        newAlarmManager( taskRecord.getStartTime().getSeconds(), new Intent(getApplicationContext(),
                        TaskReminderBroadcastReceiver.class).putExtra(REMINDER_TYPE, START_TASK)
                        .putExtra( RECORD_ID, taskRecord.getId()),
                START_REQUEST_CODE);
        Log.d(MANAGE_TASK, taskRecord.getTask().getName() + "is prepared at " +
                taskRecord.getStartTime().toString());
    }

    /**
     * Create new alarm manager according to different request code.
     * @param time
     * @param intent
     * @param requestCode
     */
    public void newAlarmManager(long time, Intent intent, int requestCode){

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager startAlarmManager = (AlarmManager) getApplicationContext()
                .getSystemService(Context.ALARM_SERVICE);
        startAlarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }
}