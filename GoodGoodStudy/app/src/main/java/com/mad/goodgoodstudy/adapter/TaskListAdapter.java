//package com.mad.goodgoodstudy.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.mad.goodgoodstudy.R;
//import com.mad.goodgoodstudy.activity.EditTaskActivity;
//import com.mad.goodgoodstudy.model.Task;
//import com.mad.goodgoodstudy.util.TimeUtil;
//import com.mad.goodgoodstudy.viewholder.MyViewHolder;
//
//import java.util.List;
//
///**
// * Created by Tong on 2017/6/2.
// */
//
//public abstract class TaskListAdapter<T> extends RecyclerView.Adapter<T extends RecyclerView.ViewHolder>{
//    private List<Task> mTasks;
//    private Context mContext;
//    private Fragment mFragment;
//    /**
//     * The constructor.
//     */
//    public TaskListAdapter(Fragment fragment, List<Task> tasks){
//        //mDailyTasks = DailyTask.listAll(DailyTask.class);
//        mContext = fragment.getContext();
//        mTasks = tasks;
//        mFragment = fragment;
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(final DailyTaskAdapter.ViewHolder holder, final int position) {
//        final Task dailyTask = mDailyTasks.get(position);
//        holder.pItemTaskNameTv.setText( dailyTask.getName() );
//        holder.pItemDurationTv.setText( TimeUtil.getTimeStrFromDuration(dailyTask.getDuration()) );
//        holder.pDeleteTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDailyTasks.remove( mDailyTasks.get(position) );
//                notifyDataSetChanged();
//            }
//        });
//        holder.pEditTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, EditTaskActivity.class);
//                intent.putExtra(EditTaskActivity.IS_NEW_TASK, false);
//                intent.putExtra(EditTaskActivity.TASK_ID, dailyTask.getId());
//
//                mContext.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDailyTasks.size();
//    }
//
//    public class ViewHolder extends MyViewHolder {
//
//        public TextView pDeleteTv;
//        public TextView pEditTv;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            pDeleteTv = (TextView) itemView.findViewById(R.id.item_delete_tv);
//            pEditTv = (TextView) itemView.findViewById(R.id.item_edit_tv);
//
//
//        }
//
//}
