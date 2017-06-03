package com.mad.goodgoodstudy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mad.goodgoodstudy.R;
import com.mad.goodgoodstudy.activity.EditTaskActivity;
import com.mad.goodgoodstudy.adapter.ScheduleAdapter;
import com.mad.goodgoodstudy.application.MyApplication;
import com.mad.goodgoodstudy.model.Schedule;
import com.mad.goodgoodstudy.model.Task;
import com.mad.goodgoodstudy.model.TaskRecord;
import com.mad.goodgoodstudy.service.ManageTaskService;
import com.mad.goodgoodstudy.widget.ItemSwipeRecyclerView;

import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.mad.goodgoodstudy.activity.EditTaskActivity.TASK_ID;

/**
 * Created by Tong on 2017/5/15.
 */

public class TaskTodayFragment extends Fragment {

    private ItemSwipeRecyclerView mScheduleRv;
    private List<TaskRecord> mTaskRecords;
    private FloatingActionButton mAddTaskFab;
    private ScheduleAdapter mAdapter;
    private Spinner mShowStatusSpn;

    public static final int REQUEST_CODE = 070103;


    public static TaskTodayFragment newInstance() {
            TaskTodayFragment fragment = new TaskTodayFragment();
            return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
    }


        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_task_today, container, false);

            mTaskRecords = new LinkedList<TaskRecord>();
            mTaskRecords.addAll( TaskRecord.getTaskRecordBySchedule(Schedule.getDailySchedule()));
            mScheduleRv = (ItemSwipeRecyclerView) v.findViewById(R.id.task_today_schedule_rv);
            mScheduleRv.setLayoutManager(new LinearLayoutManager(getContext()));
            mScheduleRv.setEmptyView(v.findViewById(R.id.empty_hint_tv));
            mAdapter = new ScheduleAdapter( TaskTodayFragment.this, mTaskRecords );
            mScheduleRv.setAdapter( mAdapter );
            mAddTaskFab = (FloatingActionButton) v.findViewById(R.id.fragment_task_today_add_fab);
            mShowStatusSpn = (Spinner) v.findViewById(R.id.task_today_show_status_spn);
            mShowStatusSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch ( position ){
                        case 0:
                            mTaskRecords.removeAll(mTaskRecords);
                            mTaskRecords.addAll( TaskRecord.getTaskRecordBySchedule(
                                    Schedule.getDailySchedule()));
                            mAdapter.notifyDataSetChanged();
                            break;
                        case 1:
                            mTaskRecords.removeAll(mTaskRecords);
                            mTaskRecords.addAll( TaskRecord.getIncompleteTaskRecords(
                                    Schedule.getDailySchedule()));
                            mAdapter.notifyDataSetChanged();
                            break;

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            mAddTaskFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), EditTaskActivity.class);
                    intent.putExtra( EditTaskActivity.IS_NEW_TASK, true );
                    TaskTodayFragment.this.startActivityForResult(intent, REQUEST_CODE);
                }
            });


            return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            long id = data.getLongExtra(TASK_ID, -1);
            if( id != -1 ){
                Task task = Task.findById(Task.class, id);
                if(task.isDailyTask()){
                    TaskRecord.updateTaskRecordsWithTask(Schedule.getDailySchedule(), mTaskRecords,
                            task);
                    mAdapter.notifyDataSetChanged();

                    MyApplication myApplication = (MyApplication) getActivity().getApplication();
                    if((!myApplication.isCurrentTaskExist())){
                        Intent intent = new Intent(getActivity(), ManageTaskService.class);
                        getActivity().startService(intent);
                    }
                }
            }
        }
    }
}