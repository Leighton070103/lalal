package com.mad.goodgoodstudy.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.goodgoodstudy.adapter.DailyTaskAdapter;
import com.mad.goodgoodstudy.R;
import com.mad.goodgoodstudy.adapter.OneTimeTaskAdapter;
import com.mad.goodgoodstudy.model.Task;
import com.mad.goodgoodstudy.widget.ItemSwipeRecyclerView;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.mad.goodgoodstudy.activity.EditTaskActivity.TASK_ID;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the interface.
 */
public class OneTimeTaskListFragment extends ListFragment {



    public static final int REQUEST_CODE = 005253;

    public OneTimeTaskListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mTasks = Task.getAllTaskOfOneType(Task.ONE_TIME_TASK_TYPE);
        mAdapter = new OneTimeTaskAdapter(OneTimeTaskListFragment.this, mTasks);

        return super.onCreateView(inflater,container, savedInstanceState);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == OneTimeTaskListFragment.REQUEST_CODE && resultCode == RESULT_OK){
            long id = data.getLongExtra(TASK_ID, -1);
            if( id != -1 ){
                Task task = Task.findById( Task.class, id);
                if( task.isOneTimeTask()  ){
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
