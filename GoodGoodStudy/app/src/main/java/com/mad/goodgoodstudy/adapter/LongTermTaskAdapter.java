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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mad.goodgoodstudy.fragment.OneTimeTaskListFragment;
import com.mad.goodgoodstudy.viewholder.MyViewHolder;
import com.mad.goodgoodstudy.R;
import com.mad.goodgoodstudy.activity.EditTaskActivity;
import com.mad.goodgoodstudy.fragment.LongTermTaskListFragment;
import com.mad.goodgoodstudy.model.Task;
import com.mad.goodgoodstudy.util.TimeUtil;

import java.util.List;

import static com.mad.goodgoodstudy.activity.EditTaskActivity.IS_NEW_TASK;
import static com.mad.goodgoodstudy.activity.EditTaskActivity.TASK_ID;


public class LongTermTaskAdapter extends RecyclerView.Adapter<LongTermTaskAdapter.ViewHolder> {

    private List<Task> mLongTermTasks;
    private Fragment mFragment;
    private Context mContext;
    //private final OnListFragmentInteractionListener mListener;

//    public DailyTaskAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
//        mValues = items;
//        mListener = listener;
    //}

    /**
     * The constructor.
     * @param
     */
    public LongTermTaskAdapter(Fragment fragment, List<Task> longTermTasks){
        mLongTermTasks = longTermTasks;
        mContext = fragment.getContext();
        mFragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.long_term_task_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Task longTermTask = mLongTermTasks.get(position);
        holder.pItemTaskNameTv.setText( longTermTask.getName() );
        holder.pItemDurationTv.setText(TimeUtil.getTimeStrFromDuration( longTermTask.getDuration()) );
        holder.pProgressBar.setMax(longTermTask.getPlannedTimes());
        holder.pProgressBar.setProgress(longTermTask.getTimes());
        holder.pEditTv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditTaskActivity.class);
                intent.putExtra(IS_NEW_TASK, false);
                intent.putExtra(TASK_ID, longTermTask.getId());
                mFragment.startActivityForResult(intent, LongTermTaskListFragment.REQUEST_CODE);
            }
        });

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
                                        intent.putExtra(EditTaskActivity.TASK_ID, longTermTask.getId());
                                        mFragment.startActivityForResult(intent, LongTermTaskListFragment
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
                                        mLongTermTasks.remove(longTermTask);
                                        longTermTask.delete();
                                        LongTermTaskAdapter.this.notifyDataSetChanged();
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
        return mLongTermTasks.size();
    }

    public class ViewHolder extends MyViewHolder {
//        public  View mView;
//        public  TextView mIdView;
//        public  TextView mContentView;
//        public DummyItem mItem;
        public ProgressBar pProgressBar;
        public TextView pDeleteTv;
        public TextView pEditTv;

        public ViewHolder(View view) {
            super(view);
//            mView = view;
//            mIdView = (TextView) view.findViewById(R.id.id);
//            mContentView = (TextView) view.findViewById(R.id.content);
            pProgressBar = (ProgressBar) view.findViewById(R.id.item_long_term_task_progress_prg);
            pDeleteTv = (TextView) view.findViewById(R.id.item_delete_tv);

        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
//    }
}
}
