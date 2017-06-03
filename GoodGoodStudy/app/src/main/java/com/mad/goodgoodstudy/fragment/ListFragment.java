package com.mad.goodgoodstudy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.goodgoodstudy.R;
import com.mad.goodgoodstudy.adapter.OneTimeTaskAdapter;
import com.mad.goodgoodstudy.model.Task;
import com.mad.goodgoodstudy.widget.ItemSwipeRecyclerView;

import java.util.List;

/**
 * Created by Tong on 2017/6/2.
 */

public abstract class ListFragment extends Fragment {
    protected ItemSwipeRecyclerView mTaskListRv;
    protected RecyclerView.Adapter mAdapter;
    protected List<Task> mTasks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_task_list, container, false);

        mTaskListRv = (ItemSwipeRecyclerView) view.findViewById(R.id.task_list_rv);
        mTaskListRv.setEmptyView( view.findViewById(R.id.task_list_empty_view_tv) );
        mTaskListRv.setAdapter( mAdapter );
        mTaskListRv.setLayoutManager( new LinearLayoutManager(getContext()));

        return view;
    }

}
