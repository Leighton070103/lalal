package com.mad.goodgoodstudy.service;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.WindowManager;

import com.mad.goodgoodstudy.R;
import com.mad.goodgoodstudy.application.MyApplication;
import com.mad.goodgoodstudy.broadcastreceiver.TaskReminderBroadcastReceiver;
import com.mad.goodgoodstudy.model.Schedule;
import com.mad.goodgoodstudy.model.Task;
import com.mad.goodgoodstudy.model.TaskRecord;
import com.mad.goodgoodstudy.monitor.NoiseMonitor;
import com.mad.goodgoodstudy.util.TimeUtil;

import java.util.Timer;
import java.util.TimerTask;

import static com.mad.goodgoodstudy.service.ManageTaskService.RECORD_ID;
import static com.mad.goodgoodstudy.service.ManageTaskService.REMINDER_TYPE;
import static com.mad.goodgoodstudy.service.ManageTaskService.STOP_TASK;

public class TaskExecutionService extends Service {

    public static final String TASK_EXECUTION = "===== Task execution";

    public TaskExecutionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {



        MyApplication myApplication = (MyApplication) getApplication();
        if(myApplication.isCurrentTaskExist()) {
            final TaskRecord taskRecord = myApplication.getCurrentTaskRecord();
            taskRecord.setCompleteStatus(TaskRecord.EXECUTING);

            final Timer timer = new Timer();


            Log.d(TASK_EXECUTION, taskRecord.getTask().getName() + " started at " +
                    TimeUtil.getCurrentTime().toString() + " supposed " + taskRecord
                    .getStartTime().toString());

            if( taskRecord.getTask().isNoiseMonitor() ){

                final NoiseMonitor noiseMonitor = new NoiseMonitor(taskRecord.getTask().getNoiseLevel());
                noiseMonitor.start();



                timer.schedule( new TimerTask() {
                    public void run() {
                        taskRecord.setRecordComplete();
                        noiseMonitor.stop();
                        Intent completeIntent = new Intent(getApplicationContext(),
                                TaskReminderBroadcastReceiver.class);
                        completeIntent.putExtra(RECORD_ID, taskRecord.getId());
                        completeIntent.putExtra(REMINDER_TYPE, STOP_TASK);
                        sendBroadcast(completeIntent);
                        timer.cancel();
                        Log.d(TASK_EXECUTION, taskRecord.getTask().getName() + " stopped at " +
                                taskRecord.getEndTime().toString());
                    }
                }, taskRecord.getTask().getDuration() * 60 * 1000 );
            }
            else {
                timer.schedule( new TimerTask() {
                    public void run() {
                        taskRecord.setRecordComplete();
                        Intent completeIntent = new Intent(getApplicationContext(),
                                TaskReminderBroadcastReceiver.class);
                        completeIntent.putExtra(REMINDER_TYPE, STOP_TASK);
                        completeIntent.putExtra(RECORD_ID, taskRecord.getId());
                        sendBroadcast(completeIntent);
                        timer.cancel();
                        Log.d(TASK_EXECUTION, taskRecord.getTask().getName() + " stopped at " +
                                taskRecord.getEndTime().toString());
                    }
                }, taskRecord.getTask().getDuration()*60*1000 );
            }
        }
        return START_REDELIVER_INTENT;
    }


    public void completeTaskRecordWithNoiseMonitor(){

    }

    public void showReminder(TaskRecord taskRecord, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reminder")
                .setMessage( taskRecord.getTask().getName() + msg);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }
}
