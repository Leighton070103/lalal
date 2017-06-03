package com.mad.goodgoodstudy.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.mad.goodgoodstudy.viewholder.MyViewHolder;
import com.mad.goodgoodstudy.listener.OnItemClickListener;

/**
 * This class overrides the recyclerview to enable the items of the recycler view to be swiped to
 * show the menu buttons.
 */
public class ItemSwipeRecyclerView extends RecyclerView {
    private Context mContext;

    private View mEmptyView;

    /**
     * The position of last touched place.
     */
    private int mLastX;
    private int mLastY;

    /**
     * The position of the touched item.
     */
    private int mPosition;

    /**
     * The content of the items.
     */
    private LinearLayout mItemLayout;

    /**
     * The linearlayout that contains the buttons.
     */
    private LinearLayout mItemButtonLayout;

    /**
     * The max distance to slide.
     */
    private int mMaxLength;

    /**
     * Whether the view is dragged vertically.
     */
    private boolean isDragging;

    /**
     * Whether the item is moving by fingers.
     */
    private boolean isItemMoving;

    /**
     * Whether the item is scrolling by itself.
     */
    private boolean isStartScroll;

    /**
     * The state of the menu buttons.
     * 0: dismiss
     * 1: about to dismiss.
     * 2: about to show
     * 3: shown.
     */
    private int mBtnState;

    /**
     * The velocity of the sliding.
     */
    private VelocityTracker mVelocityTracker;
    private Scroller mScroller;
    private OnItemClickListener mListener;

    private AdapterDataObserver mEmptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter adapter =  getAdapter();
            if( adapter != null && mEmptyView != null) {
                if(adapter.getItemCount() == 0) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    ItemSwipeRecyclerView.this.setVisibility(View.GONE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                    ItemSwipeRecyclerView.this.setVisibility(View.VISIBLE);
                }
            }

        }
    };

    /**
     * Constructor of this class.
     * @param context
     */
    public ItemSwipeRecyclerView(Context context) {
        this(context, null);
    }

    /**
     * Constructor of this class.
     * @param context
     * @param attrs
     */
    public ItemSwipeRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemSwipeRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;

        mScroller = new Scroller(context, new LinearInterpolator());
        mVelocityTracker = VelocityTracker.obtain();
    }

    /**
     *
     * @param e
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        mVelocityTracker.addMovement(e);

        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mBtnState == 0) {
                    View view = findChildViewUnder(x, y);
                    if (view == null) {
                        return false;
                    }

                    MyViewHolder viewHolder = (MyViewHolder) getChildViewHolder(view);

                    mItemLayout = viewHolder.pItemLayout;
                    mPosition = viewHolder.getAdapterPosition();

                    mItemButtonLayout = viewHolder.pItemButtonLayout;
                    mMaxLength = mItemButtonLayout.getWidth();
                    mItemButtonLayout.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemLayout.scrollTo(0, 0);
                            mBtnState = 0;
                        }
                    });
                } else if ( mBtnState == 3 ){
                    mScroller.startScroll(mItemLayout.getScrollX(), 0, -mMaxLength, 0, 200);
                    invalidate();
                    mBtnState = 0;
                    return false;
                }else{
                    return false;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                int dx = mLastX - x;
                int dy = mLastY - y;

                int scrollX = mItemLayout.getScrollX();
                if (Math.abs(dx) > Math.abs(dy)) {
                    // Detect the left border
                    isItemMoving = true;
                    if (scrollX + dx <= 0) {
                        mItemLayout.scrollTo(0, 0);
                        return true;
                    } else if (scrollX + dx >= mMaxLength) {
                        //Detect the right border
                        mItemLayout.scrollTo(mMaxLength, 0);
                        return true;
                    }
                    mItemLayout.scrollBy(dx, 0);
                    //item scroll with the finger.
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isItemMoving && !isDragging && mListener != null) {
                    mListener.onItemClick(mItemLayout, mPosition);
                }
                isItemMoving = false;

                mVelocityTracker.computeCurrentVelocity(1000);//Calculate the sliding speed.
                float xVelocity = mVelocityTracker.getXVelocity();//The speed horizontally.
                float yVelocity = mVelocityTracker.getYVelocity();//The vertical spped.

                int deltaX = 0;
                int upScrollX = mItemLayout.getScrollX();

                if (Math.abs(xVelocity) > 100 && Math.abs(xVelocity) > Math.abs(yVelocity)) {
                    if (xVelocity <= -100) {
                        //If the speed to the left is larger than 100, display the buttons.
                        deltaX = mMaxLength - upScrollX;
                        mBtnState = 2;
                    } else if (xVelocity > 100) {
                        //If the speed to the right is larger than 100, dismiss the buttons.
                        deltaX = -upScrollX;
                        mBtnState = 1;
                    }
                } else {
                    if (upScrollX >= mMaxLength / 2) {
                        //If the distance of that the item is sliding to the left is larger than
                        // half of length of the button layout, display the buttons.
                        deltaX = mMaxLength - upScrollX;
                        mBtnState = 2;
                    } else if (upScrollX < mMaxLength / 2) {
                        //Else dismiss the buttons.
                        deltaX = -upScrollX;
                        mBtnState = 1;
                    }
                }

                mScroller.startScroll(upScrollX, 0, deltaX, 0, 200);
                isStartScroll = true;
                invalidate();

                mVelocityTracker.clear();
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(e);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            mItemLayout.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        } else if (isStartScroll) {
            isStartScroll = false;
            if (mBtnState == 1) {
                mBtnState = 0;
            }

            if (mBtnState == 2) {
                mBtnState = 3;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        isDragging = state == SCROLL_STATE_DRAGGING;
    }

    public View getEmptyView() {
        return mEmptyView;
    }

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
    }


    public void setAdapter(Adapter adapter) {
        Adapter oldAdapter = getAdapter();
        if(oldAdapter != null && mEmptyObserver != null){
            oldAdapter.unregisterAdapterDataObserver(mEmptyObserver);
        }
        super.setAdapter(adapter);

        if(adapter != null) {
            adapter.registerAdapterDataObserver(mEmptyObserver);
        }
        mEmptyObserver.onChanged();
    }
    //   public void setOnItemClickListener(OnItemClickListener listener) {
     //   mListener = listener;
  //  }
}