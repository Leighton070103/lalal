package com.mad.goodgoodstudy.monitor;

import android.app.usage.UsageStatsManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * To read the data usage status.
 * Created by Tong on 2017/5/14.
 */

public class SoftwareMonitor  {
    UsageStatsManager mUsageStatsManager;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SoftwareMonitor() {

    }
}
