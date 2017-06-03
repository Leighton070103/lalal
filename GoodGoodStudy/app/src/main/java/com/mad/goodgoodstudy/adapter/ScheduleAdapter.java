package com.mad.goodgoodstudy.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mad.goodgoodstudy.viewholder.MyViewHolder;
import com.mad.goodgoodstudy.R;
import com.mad.goodgoodstudy.activity.EditTaskActivity;
import com.mad.goodgoodstudy.fragment.TaskTodayFragment;
import com.mad.goodgoodstudy.model.Task;
import com.mad.goodgoodstudy.model.TaskRecord;
import com.mad.goodgoodstudy.util.TimeUtil;

import java.util.List;

import static com.mad.goodgoodstudy.model.TaskRecord.CANCELED;
import static com.mad.goodgoodstudy.model.TaskRecord.COMPLETE;
import static com.mad.goodgoodstudy.model.TaskRecord.EXECUTING;
import static com.mad.goodgoodstudy.model.TaskRecord.INCOMPLETE;


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<TaskRecord> mTaskRecords;
    private Context mContext;
    private Fragment mFragment;
    //private final OnListFragmentInteractionListener mListener;

//    public DailyTaskAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
//        mValues = items;
//        mListener = listener;
    //}


    /**
     * The constructor.
     */
    public ScheduleAdapter(Fragment fragment, List<TaskRecord> taskRecords){
        //mDailyTasks = DailyTask.listAll(DailyTask.class);
        mContext = fragment.getContext();
        mTaskRecords = taskRecords;
        mFragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_today_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TaskRecord taskRecord = mTaskRecords.get(position);
        Task task = taskRecord.getTask();

        holder.pNumberTv.setText(String.valueOf(position));
        switch ( taskRecord.getCompleteStatus() ){
            case EXECUTING:
                holder.pCompleteStatusTv.setText(mContext.getString(R.string.executing));
                break;
            case INCOMPLETE:
                holder.pCompleteStatusTv.setText(mContext.getString(R.string.incomplete));
                break;
            case COMPLETE:
                holder.pCompleteStatusTv.setText(mContext.getString(R.string.complete));
                break;
            case CANCELED:
                holder.pCompleteStatusTv.setText(mContext.getString(R.string.canceled));
                break;

        }

        holder.pTaskStartTimeTv.setText(TimeUtil.getTimeStrFromDate( taskRecord.getStartTime() ));
        holder.pItemDurationTv.setText( TimeUtil.getTimeStrFromDuration(task.getDuration()));
        holder.pItemTaskNameTv.setText( task.getName() );

        holder.pDeleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskRecords.remove( mTaskRecords.get(position) );
                notifyDataSetChanged();
            }
        });

        holder.pItemTaskNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder( mContext );
                builder.setItems(mContext.getResources().getStringArray(R.array
                        .record_operation_type), new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        switch (which){
                            case 0:
                                Intent intent = new Intent(mContext, EditTaskActivity.class);
                                intent.putExtra(EditTaskActivity.IS_NEW_TASK, false);
                                intent.putExtra(EditTaskActivity.TASK_ID, taskRecord.getTask()
                                        .getId());
                                mFragment.startActivityForResult(intent, TaskTodayFragment
                                        .REQUEST_CODE);
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                        }
                    }
                });
                builder.create().show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mTaskRecords.size();
    }

    public class ViewHolder extends MyViewHolder {

        public TextView pDeleteTv;
        public TextView pNumberTv;
        public TextView pCompleteStatusTv;

        public TextView pTaskStartTimeTv;

        public ViewHolder(View itemView) {
            super(itemView);
            pNumberTv = (TextView) itemView.findViewById(R.id.item_task_number_tv);
            pCompleteStatusTv = (TextView) itemView.findViewById(R.id.
                    task_today_item_complete_status_tv);
            pDeleteTv = (TextView) itemView.findViewById(R.id.item_delete_tv);
            pTaskStartTimeTv = (TextView) itemView.findViewById(R.id.task_today_item_start_time_tv);

        }
    }
}