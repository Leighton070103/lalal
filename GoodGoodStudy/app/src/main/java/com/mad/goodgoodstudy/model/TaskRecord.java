package com.mad.goodgoodstudy.model;

import android.support.annotation.NonNull;

import com.mad.goodgoodstudy.util.StringUtil;
import com.mad.goodgoodstudy.util.TimeUtil;
import com.orm.SugarRecord;

import java.net.PortUnreachableException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tong on 2017/5/15.
 */

public class TaskRecord extends SugarRecord<TaskRecord> implements Comparable<TaskRecord>{
    private Task mTask;
    private Integer mTaskScore;
    private Date mStartTime;
    private Date mEndTime;


    public static final int CANCELED = -1;
    public static final int INCOMPLETE = 0;
    public static final int EXECUTING = 1;
    public static final int COMPLETE = 2;

    /**
     * Represents the complete status of the task record.
     * -1: canceled;
     * 0: incomplete;
     * 1: executing;
     * 2: complete.
     */
    private int mCompleteStatus = 0;
    private ArrayList<Object> mAppStatics;
    private String mContent;
    private Schedule mSchedule;

    public TaskRecord(){}

    public static TaskRecord formScheduleTaskRecord(Schedule schedule, Task task){
        TaskRecord taskRecord = new TaskRecord();
        taskRecord.setSchedule( schedule );

        return updateTaskRecordFromTask(taskRecord, task);
    }

    public static TaskRecord updateTaskRecordFromTask(TaskRecord taskRecord, Task task){
        taskRecord.setTask( task );
        taskRecord.setStartTime( TimeUtil.getDateFromTimeStr(task.getStartTime())  );
        taskRecord.setEndTime( TimeUtil.addTimeToDate( taskRecord.getStartTime(),
                task.getDuration()) );
        taskRecord.save();
        return taskRecord;
    }

    public Task getTask() {
        return mTask;
    }

    public void setTask(Task task) {
        this.mTask = task;
    }

    public Integer getTaskScore() {
        return mTaskScore;
    }

    public void setTaskScore(Integer taskScore) {
        this.mTaskScore = taskScore;
    }

    public Schedule getSchedule() {
        return mSchedule;
    }

    public void setSchedule( Schedule schedule) {
        this.mSchedule = schedule;
    }

    public Date getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Date startTime) {
        this.mStartTime = startTime;
    }

    public Date getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Date endTime) {
        this.mEndTime = endTime;
    }


    /**
     * Compare two task record according to start time.
     * @param o
     * @return
     */
    @Override
    public int compareTo(@NonNull TaskRecord o) {
        return mStartTime.compareTo(o.mStartTime);
    }

    public boolean isComplete() {
        return mCompleteStatus == COMPLETE;
    }

    public boolean isIncomplete(){
        return mCompleteStatus == INCOMPLETE;
    }

    public boolean isExecuting(){
        return mCompleteStatus == EXECUTING;
    }

    public void setCompleteStatus(int completeStatus) {
        this.mCompleteStatus = completeStatus;
        this.save();
    }

    public int getCompleteStatus(){ return this.mCompleteStatus;}



    /**
     * Called when the task is complete.
     */
    public void setRecordComplete(){
        setCompleteStatus(COMPLETE);
        setEndTime(TimeUtil.getCurrentTime());
        if(this.getTask().isLongTermTask()) this.getTask().addTimes();
        this.save();
    }

    /**
     * Get the daily task data from the database and turn them into task records.
     * @param schedule
     * @return
     */
    public static List<TaskRecord> createInitialTaskRecordsFromDailyTask(Schedule schedule){
        List<Task> dailyTasks = Task.getAllTaskOfOneType(Task.DAILY_TASK_TYPE);
        List<TaskRecord> taskRecords = new LinkedList<TaskRecord>();
        if( dailyTasks != null ) {
            for (Task dailyTask : dailyTasks) {
                TaskRecord taskRecord = new TaskRecord();
                taskRecord = taskRecord.formScheduleTaskRecord(schedule, dailyTask);
                taskRecord.save();
                taskRecords.add(taskRecord);
            }
        }
        return taskRecords;
    }

    /**
     * Find the corresponding task record for a specific task.
     * @param taskRecords
     * @param task
     * @return
     */
    public static TaskRecord findTaskRecordByTask(List<TaskRecord> taskRecords, Task task){
        for(TaskRecord taskRecord: taskRecords){
            if(taskRecord.getTask().getId() == task.getId())
                return taskRecord;
        }

        return null;
    }

    /**
     * Get task record by schedule.
     * @param schedule
     * @return
     */
    public static List<TaskRecord> getTaskRecordBySchedule(Schedule schedule){
        List<TaskRecord> taskRecords = TaskRecord.find(TaskRecord.class,
                StringUtil.getQueryStrFromName("mSchedule"), String.valueOf(schedule.getId()));
        List<Task> dailyTasks = Task.getAllTaskOfOneType(Task.DAILY_TASK_TYPE);
        if( taskRecords == null ) taskRecords = new LinkedList<TaskRecord>();
        for(Task task: dailyTasks){
            if( !hasCorrespondingRecord(taskRecords, task)){
                TaskRecord taskRecord = formScheduleTaskRecord(schedule, task);
                taskRecords.add(taskRecord);
            }
        }
        Collections.sort(taskRecords);
        return taskRecords;
    }

    /**
     * Check whether there is a corresponding task record for a specific task.
     * @param taskRecords
     * @param task
     * @return
     */
    public static boolean hasCorrespondingRecord(List<TaskRecord> taskRecords, Task task){
        for( TaskRecord taskRecord: taskRecords ){
            if( taskRecord.getTask().getId() == task.getId() ) return true;
        }
        return false;

    }

    /**
     * Update the task record after new operations.
     * @param schedule
     * @param taskRecords
     * @param task
     */
    public static void updateTaskRecordsWithTask(Schedule schedule, List<TaskRecord> taskRecords,
                                                 Task task){

        TaskRecord taskRecord = TaskRecord.findTaskRecordByTask(taskRecords, task);
        if( taskRecord == null) {
            taskRecord = TaskRecord.formScheduleTaskRecord( schedule, task);
            taskRecords.add(taskRecord);
        }
        else taskRecord = TaskRecord.updateTaskRecordFromTask(taskRecord, task);

        Collections.sort(taskRecords);
    }

    /**
     * Get incomplete task records of the day.
     * @param schedule
     * @return
     */
    public static List<TaskRecord> getIncompleteTaskRecords(Schedule schedule){
        List<TaskRecord> taskRecords = getTaskRecordBySchedule(schedule);
        List<TaskRecord> incompleteTaskRecords = new LinkedList<TaskRecord>();
        if( taskRecords.size() > 0 ){
            for( TaskRecord taskRecord: taskRecords ){
                if( taskRecord.isIncomplete() ) incompleteTaskRecords.add(taskRecord);
            }
        }
        return incompleteTaskRecords;
    }

    /**
     * Get the next task record for executing.
     * @return
     */
    public static TaskRecord getNextTaskRecord(){
        List<TaskRecord> taskRecords =
                TaskRecord.getIncompleteTaskRecords( Schedule.getDailySchedule() );
        if( taskRecords == null || taskRecords.size() == 0 ) return null;
        Collections.sort(taskRecords);
        for (int i = 0; i < taskRecords.size(); i++) {
            if (taskRecords.get(i).getStartTime().compareTo(TimeUtil.getCurrentTime())> 0) {
                return taskRecords.get(i);
            }
        }
        return null;
    }
}