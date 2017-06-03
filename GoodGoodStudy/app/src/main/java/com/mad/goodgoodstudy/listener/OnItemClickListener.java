package com.mad.goodgoodstudy.listener;

import android.view.View;

public interface OnItemClickListener {
    /**
     *
     *
     * @param view
     * @param position
     */
    void onItemClick(View view, int position);

    /**
     *
     * @param position
     */
    void onDeleteClick(int position);
}
