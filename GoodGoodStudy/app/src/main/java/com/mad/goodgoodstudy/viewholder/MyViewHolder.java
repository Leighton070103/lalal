package com.mad.goodgoodstudy.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mad.goodgoodstudy.R;

import java.util.LinkedList;

public abstract class MyViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout pItemButtonLayout;
    public LinearLayout pItemContentLayout;
    public LinearLayout pItemLayout;
    public TextView pItemTaskNameTv;
    public TextView pItemDurationTv;
    public TextView pDeleteTv;

    public MyViewHolder(View itemView) {
        super(itemView);

        pDeleteTv = (TextView) itemView.findViewById(R.id.item_delete_tv);
        pItemButtonLayout = (LinearLayout) itemView.findViewById(R.id.item_swipe_button_llayout);
        pItemContentLayout = (LinearLayout) itemView.findViewById(R.id.item_content_llayout);
        pItemLayout = (LinearLayout) itemView.findViewById(R.id.item_llayout);
        pItemTaskNameTv = (TextView) itemView.findViewById(R.id.item_task_name_tv);
        pItemDurationTv = (TextView) itemView.findViewById(R.id.item_task_duration_tv);
//        layout = (LinearLayout) itemView.findViewById(R.id.item_layout);
    }
}
