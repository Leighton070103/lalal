package com.mad.goodgoodstudy.model;

import com.mad.goodgoodstudy.util.StringUtil;
import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Tong on 2017/5/14.
 */

public class WishTree extends SugarRecord<WishTree>{
    public static final Integer[] ENERGY_LEVELS = { 203, 701, 1207 };
    public static final String[] TREE_SIZES = { "small", "medium", "large"};
    public static final int FIRST_STAGE = 1;
    public static final int SECOND_STAGE = 2;
    public static final int THIRD_STAGE = 3;
    private String mName;
    private Integer mEnergyLevel;
    /**
     * The day this wish tree is set up.
     */
    private Date mSetUpDate;
    /**
     * The day this wish tree is grown up.
     */
    private Date mGrownUpDate;
    private Integer mGrowth = 0;
    /**
     * Whether the wish tree is grown up.
     */
    private boolean mGrownUp = false;

    public WishTree(String name, String treeSize ){
        mName = name;

        for(int i = 0; i < ENERGY_LEVELS.length; i++ ){
            if( treeSize.equals(TREE_SIZES[i]) ) mEnergyLevel = ENERGY_LEVELS[i];
        }

        mSetUpDate = new Date();
        this.save();

    }

    public WishTree(){};

    public void water( Integer waterEnergy ){
        setGrowth( getGrowth() + waterEnergy);
        isGrownUp();
    }



    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Integer getEnergyLevel() {
        return mEnergyLevel;
    }

    public void setEnergyLevel(Integer energyLevel) {
        this.mEnergyLevel = energyLevel;
    }

    public Date getSetUpDate() {
        return mSetUpDate;
    }

    public void setSetUpDate(Date setUpDate) {
        this.mSetUpDate = setUpDate;
    }

    public Date getGrownUpDate() {
        return mGrownUpDate;
    }

    public void setGrownUpDate(Date grownUpDate) {
        this.mGrownUpDate = grownUpDate;
    }

    public Integer getGrowth() {
        return mGrowth;
    }

    public void setGrowth(Integer growth) {
        this.mGrowth = growth;
    }

    public boolean isGrownUp() {
        if( getGrowth() >= getEnergyLevel() ) setGrownUp(true);
        return mGrownUp;
    }

    public void setGrownUp(boolean grownUp) {
        this.mGrownUp = grownUp;
    }

    public int getGrownStage(){
        if( getGrowth() < getEnergyLevel() / 3) return FIRST_STAGE;
        if( getGrowth() < getEnergyLevel() * 2 / 3 ) return SECOND_STAGE;
        return THIRD_STAGE;
    }

    public static WishTree getCurrentWishTree(){
        try {
            return WishTree.find( WishTree.class, StringUtil.getQueryStrFromName("mGrownUp"),
                    "false").get(0);
        }
        catch ( NullPointerException e){
            return null;
        }
        catch (IndexOutOfBoundsException e){
            return null;
        }
    }
}
