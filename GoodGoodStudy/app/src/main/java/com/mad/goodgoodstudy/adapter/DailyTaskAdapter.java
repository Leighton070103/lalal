package com.mad.goodgoodstudy.adapter;

import android.app.Dialog;
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

import com.mad.goodgoodstudy.activity.EditTaskActivity;
import com.mad.goodgoodstudy.fragment.DailyTaskListFragment;
import com.mad.goodgoodstudy.viewholder.MyViewHolder;
import com.mad.goodgoodstudy.R;
import com.mad.goodgoodstudy.model.Task;
import com.mad.goodgoodstudy.util.TimeUtil;

import java.util.List;


public class DailyTaskAdapter extends RecyclerView.Adapter<DailyTaskAdapter.ViewHolder> {

    private List<Task> mDailyTasks;
    private Context mContext;
    private Fragment mFragment;
    /**
     * The constructor.
     */
    public DailyTaskAdapter(Fragment fragment, List<Task> dailyTasks){

        mContext = fragment.getContext();
        mDailyTasks = dailyTasks;
        mFragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daily_task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Task dailyTask = mDailyTasks.get(position);
        holder.pItemTaskNameTv.setText( dailyTask.getName() );
        holder.pItemDurationTv.setText( TimeUtil.getTimeStrFromDuration(dailyTask.getDuration()) );

        holder.pItemTaskNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,
                        EditTaskActivity.class);
                intent.putExtra(EditTaskActivity.IS_NEW_TASK, false);
                intent.putExtra(EditTaskActivity.TASK_ID, dailyTask.getId());
                mFragment.startActivityForResult(intent, DailyTaskListFragment
                        .REQUEST_CODE);
            }
        });

        holder.pDeleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog alertDialog = new AlertDialog.Builder(mContext).
                        setTitle(mContext.getString(R.string.alert)).
                        setMessage(mContext.getString(R.string.delete_alert_msg)).
                        setPositiveButton( mContext.getString(R.string.confirm),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mDailyTasks.remove(dailyTask);
                                        dailyTask.delete();
                                        DailyTaskAdapter.this.notifyDataSetChanged();
                                    }
                        }).
                        setNegativeButton( mContext.getString(R.string.cancel),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {}
                        }).create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDailyTasks.size();
    }

    public class ViewHolder extends MyViewHolder {


        public TextView pStartTimeTv;

        public ViewHolder(View itemView) {
            super(itemView);

            pStartTimeTv = (TextView) itemView.findViewById(R.id.item_daily_task_start_time_tv);


        }

    }
}