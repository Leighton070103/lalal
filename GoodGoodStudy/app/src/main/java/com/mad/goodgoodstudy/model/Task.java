package com.mad.goodgoodstudy.model;

import com.orm.SugarRecord;

import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tong on 2017/5/14.
 */

public class Task extends SugarRecord<Task>{
//    private Integer mId;
    public static final String TASK_TYPE = "mTaskType";
    private String mName;
    /**
     * Minutes of the task.
     */
    private int mDuration;
    private boolean mNoiseMonitor;
    private boolean mSoftwareMonitor;
    private double mNoiseLevel = 75;
    private Integer mTimes = 0;
    private Integer mPlannedTimes;
    private String mStartTime;
    /**
     * 0 represents daily task
     * 1 represents one time task
     * 2 represents long-term task
     */
    private Integer mTaskType;

    public static final int DAILY_TASK_TYPE = 0;
    public static final int ONE_TIME_TASK_TYPE = 1;
    public static final int LONG_TERM_TASK_TYPE = 2;


    public Integer getTimes() {
        return mTimes;
    }

    public void setTimes(Integer times) {
        this.mTimes = times;
    }

    public double getNoiseLevel() {
        return mNoiseLevel;
    }

    public void setNoiseLevel(double noiseLevel) {
        this.mNoiseLevel = noiseLevel;
    }

    public boolean isSoftwareMonitor() {
        return mSoftwareMonitor;
    }

    public void setSoftwareMonitor(boolean softwareMonitor) {
        this.mSoftwareMonitor = softwareMonitor;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

//    public void setId(Integer id) {
//        this.mId = id;
//    }

    public void addTimes(){
        if( mPlannedTimes > mTimes ) mTimes ++;
        this.save();
    }

    public boolean isNoiseMonitor() {
        return mNoiseMonitor;
    }
    public void setNoiseMonitor(boolean noiseMonitor) {
        this.mNoiseMonitor = noiseMonitor;
    }
    public String getStartTime() {
        return mStartTime;
    }
    public void setStartTime(String startTime) {
        this.mStartTime = startTime;
    }

    public Integer getTaskType() {
        return mTaskType;
    }

    public void setTaskType(Integer taskType) {
        this.mTaskType = taskType;
    }

    public Integer getPlannedTimes() {
        return mPlannedTimes;
    }

    public void setPlannedTimes(Integer plannedTimes) {
        this.mPlannedTimes = plannedTimes;
    }

    /**
     * Find all the tasks of a specific type from the database.
     * If no such tasks in the database, return a new empty list.
     * @param taskType
     * @return
     */
    public static List<Task> getAllTaskOfOneType(int taskType){
        List<Task> tasks = Task.find(Task.class, com.mad.goodgoodstudy.util.StringUtil
                        .getQueryStrFromName(Task.TASK_TYPE) , String.valueOf(taskType));
        if( tasks == null ) return new LinkedList<Task>();
        return tasks;
    }

    public static boolean hasTheTask(List<Task> tasks, Task targetTask){
        return ( getTheTaskFromListById(tasks, targetTask.getId()) != null ) ;
    }

    public static Task getTheTaskFromListById(List<Task> tasks, long taskId){
        for(Task task: tasks){
            if(task.getId() == taskId) return task;
        }
        return null;
    }

    public boolean isDailyTask(){
        return this.getTaskType() == DAILY_TASK_TYPE;
    }

    public boolean isOneTimeTask(){
        return this.getTaskType() == ONE_TIME_TASK_TYPE;
    }

    public boolean isLongTermTask(){
        return  this.getTaskType() == LONG_TERM_TASK_TYPE;
    }
}