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

import com.mad.goodgoodstudy.activity.EditTaskActivity;
import com.mad.goodgoodstudy.fragment.OneTimeTaskListFragment;
import com.mad.goodgoodstudy.viewholder.MyViewHolder;
import com.mad.goodgoodstudy.R;
import com.mad.goodgoodstudy.model.Task;
import com.mad.goodgoodstudy.util.TimeUtil;

import java.util.List;


public class OneTimeTaskAdapter extends RecyclerView.Adapter<OneTimeTaskAdapter.ViewHolder> {

    private List<Task> mOneTimeTasks;
    private Context mContext;
    private Fragment mFragment;

    /**
     * The constructor, fragment is passed in to make sure the result could be passed back to
     * corresponding fragment
     */
    public OneTimeTaskAdapter(Fragment fragment, List<Task> oneTimeTasks) {
        mOneTimeTasks = oneTimeTasks;
        mContext = fragment.getContext();
        mFragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_time_task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Task oneTimeTask = mOneTimeTasks.get(position);

        holder.pItemTaskNameTv.setText(oneTimeTask.getName());
        holder.pItemDurationTv.setText(TimeUtil.getTimeStrFromDuration( oneTimeTask.getDuration()));
        holder.pItemTaskNameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder( mContext );
                builder.setItems(mContext.getResources().getStringArray(R.array.operation_type),
                        new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog,int which){
                        switch (which){
                            case 0:
                                Intent intent = new Intent(mContext,
                                        EditTaskActivity.class);
                                intent.putExtra(EditTaskActivity.IS_NEW_TASK, false);
                                intent.putExtra(EditTaskActivity.TASK_ID, oneTimeTask.getId());
                                mFragment.startActivityForResult(intent, OneTimeTaskListFragment
                                        .REQUEST_CODE);
                                break;
                            case 1:
                                break;

                        }
                    }
                });
                builder.create().show();

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
                                        mOneTimeTasks.remove(oneTimeTask);
                                        oneTimeTask.delete();
                                        OneTimeTaskAdapter.this.notifyDataSetChanged();
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
        return mOneTimeTasks.size();
    }

    public class ViewHolder extends MyViewHolder {

        public ViewHolder(View view) {
            super(view);
        }

    }
}