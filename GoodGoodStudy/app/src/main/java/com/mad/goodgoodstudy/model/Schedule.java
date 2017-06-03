package com.mad.goodgoodstudy.model;

import android.support.v7.widget.RecyclerView;

import com.mad.goodgoodstudy.util.TimeUtil;
import com.orm.StringUtil;
import com.orm.SugarRecord;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.mad.goodgoodstudy.model.TaskRecord.formScheduleTaskRecord;
import static com.mad.goodgoodstudy.model.TaskRecord.updateTaskRecordFromTask;

/**
 * Created by Tong on 2017/5/15.
 */

public class Schedule extends SugarRecord<Schedule> {

    private String mDate;
    private String mReportContent;

    //

    public Schedule(){

    }
    public Schedule(String date){
        mDate = date;
        this.save();
    }

    public static Schedule getDailySchedule(){
        Schedule schedule;
        String date = TimeUtil.getCurrentDateStr();
        try {
             schedule = Schedule.find( Schedule.class, StringUtil.toSQLName("mDate") + " = ?",
                    TimeUtil.getCurrentDateStr()).get(0);
        }
        catch ( NullPointerException e ){
            schedule = new Schedule(TimeUtil.getCurrentDateStr());
            return schedule;
        }
        catch ( IndexOutOfBoundsException e ){
            schedule = new Schedule(TimeUtil.getCurrentDateStr());
            return schedule;
        }
        //return schedule;
        return schedule;

    }

}