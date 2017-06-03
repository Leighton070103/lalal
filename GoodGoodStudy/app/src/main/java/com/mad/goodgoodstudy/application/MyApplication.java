package com.mad.goodgoodstudy.application;

import android.app.Application;

import com.mad.goodgoodstudy.model.TaskRecord;
import com.orm.SugarApp;

/**
 * Created by Tong on 2017/6/3.
 */

public class MyApplication extends SugarApp {

    private TaskRecord mCurrentTaskRecord;

    @Override
    public void onCreate() {
        super.onCreate();
        mCurrentTaskRecord = null;
    }

    public boolean isCurrentTaskExist(){
        return mCurrentTaskRecord != null;
    }

    public TaskRecord getCurrentTaskRecord(){
        return mCurrentTaskRecord;
    }

    public void setCurrentTaskRecord(TaskRecord taskRecord){
        mCurrentTaskRecord = taskRecord;
    }
}
