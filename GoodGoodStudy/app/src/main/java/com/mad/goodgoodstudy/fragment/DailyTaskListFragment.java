package com.mad.goodgoodstudy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.goodgoodstudy.R;
import com.mad.goodgoodstudy.adapter.DailyTaskAdapter;
import com.mad.goodgoodstudy.adapter.OneTimeTaskAdapter;
import com.mad.goodgoodstudy.model.Task;
import com.mad.goodgoodstudy.util.StringUtil;
import com.mad.goodgoodstudy.widget.ItemSwipeRecyclerView;

import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.mad.goodgoodstudy.activity.EditTaskActivity.TASK_ID;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class DailyTaskListFragment extends ListFragment {

    public static final int REQUEST_CODE = 1207;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DailyTaskListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTasks = Task.getAllTaskOfOneType(Task.DAILY_TASK_TYPE);
        mAdapter = new DailyTaskAdapter(DailyTaskListFragment.this, mTasks);

        return super.onCreateView(inflater,container, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if( requestCode == DailyTaskListFragment.REQUEST_CODE && resultCode == RESULT_OK){
            long id = data.getLongExtra(TASK_ID, -1);
            if( id != -1 ){
                Task task = Task.findById( Task.class, id);
                if( task.isDailyTask()  ){
                    if((!Task.hasTheTask( mTasks, task ))) {
                        mTasks.add(task);
                    }
                    else {
                        Task oldTask = Task.getTheTaskFromListById( mTasks, task.getId() );
                        mTasks.remove(oldTask);
                        mTasks.add(task);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}