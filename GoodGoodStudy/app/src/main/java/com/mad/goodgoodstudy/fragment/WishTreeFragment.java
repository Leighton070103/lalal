package com.mad.goodgoodstudy.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mad.goodgoodstudy.model.WishTree;
import com.mad.goodgoodstudy.R;

/**
 * Created by Tong on 2017/5/15.
 */

public class WishTreeFragment extends Fragment {

    private WishTree mWishTree;
    private ProgressBar mTreeGrowthPrb;
    private ImageView mTreeIv;
    private LinearLayout mEmptyLlayout;
    private TextView mTreeNameTv;
    private RelativeLayout mTreeContentRlayout;
    private FloatingActionButton mWateringFab;
    private FloatingActionButton mCutDownFab;
    private ImageButton mAddNewTreeImgbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wish_tree, container, false);
        mTreeGrowthPrb = (ProgressBar) v.findViewById(R.id.wish_tree_grown_prb);
        mTreeIv = (ImageView) v.findViewById(R.id.fragment_wish_tree_iv);
        mEmptyLlayout = (LinearLayout) v.findViewById(R.id.fragment_wish_tree_empty_llayout);
        mTreeNameTv = (TextView) v.findViewById(R.id.fragment_wish_tree_name_tv);
        mTreeContentRlayout = (RelativeLayout) v.findViewById(R.id.wish_tree_content_rlayout);
        mWateringFab = (FloatingActionButton) v.findViewById(R.id.fragment_wish_tree_water_fab);
        mCutDownFab = (FloatingActionButton) v.findViewById(R.id.fragment_wish_tree_cut_down_fab);
        mAddNewTreeImgbtn = (ImageButton) v.findViewById(R.id.fragment_wish_tree_add_imgbtn);

        mWishTree = WishTree.getCurrentWishTree();


        if( mWishTree != null ){
            switch (mWishTree.getGrownStage()){
                case WishTree.FIRST_STAGE:
                    mTreeIv.setImageResource(R.drawable.wish_tree_first_stage);
                    break;
                case WishTree.SECOND_STAGE:
                    mTreeIv.setImageResource(R.drawable.wish_tree_second_stage);
                    break;
                case WishTree.THIRD_STAGE:
                    mTreeIv.setImageResource(R.drawable.wish_tree_third_stage);
                    break;
            }
            mTreeGrowthPrb.setMax( mWishTree.getEnergyLevel() );
            mTreeGrowthPrb.setProgress( mWishTree.getGrowth() );
            mTreeNameTv.setText( mWishTree.getName() );
        }
        else {
            dismissTreeContent();
        }

        return v;
    }

    public void dismissTreeContent(){
        mTreeContentRlayout.setVisibility(View.GONE);
        mWateringFab.setVisibility(View.GONE);
        mCutDownFab.setVisibility(View.GONE);
        mEmptyLlayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static WishTreeFragment newInstance() {
        WishTreeFragment fragment = new WishTreeFragment();
        return fragment;
    }

}