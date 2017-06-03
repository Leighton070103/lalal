package com.mad.goodgoodstudy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.goodgoodstudy.R;
import com.mad.goodgoodstudy.activity.EditTaskActivity;

import static com.mad.goodgoodstudy.activity.EditTaskActivity.IS_NEW_TASK;

/**
 * Created by Tong on 2017/5/15.
 */

public class TaskListFragment extends Fragment {


    private FragmentTabHost mTabHost;
    private FloatingActionButton mAddFab;

    public final static int REQUEST_CODE = 040402;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        mTabHost = new FragmentTabHost(getActivity());
//        mTabHost.clearAllTabs();
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        mTabHost = (FragmentTabHost) view.findViewById(R.id.task_list_tabhost);
        mAddFab = (FloatingActionButton) view.findViewById(R.id.fragment_task_list_add_fab);


        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.content_task_list);

        mTabHost.addTab(mTabHost.newTabSpec("simple").setIndicator("Daily"),
                DailyTaskListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("ala").setIndicator("One time"),
                OneTimeTaskListFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("contacts").setIndicator("Long term"),
                LongTermTaskListFragment.class, null);
        mAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditTaskActivity.class);
                intent.putExtra(IS_NEW_TASK, true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        return view;
    }

    public static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
        return fragment;
    }

}
