package com.mad.goodgoodstudy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.goodgoodstudy.R;

/**
 * Created by Tong on 2017/5/15.
 */

public class ReportFragment extends Fragment {
    private FragmentTabHost mTabHost;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report, container, false);
        mTabHost = (FragmentTabHost) view.findViewById(R.id.report_tab_host);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.layout.content_report);

        mTabHost.addTab(mTabHost.newTabSpec("simple").setIndicator("Daily"),
                DailyReportFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec("ala").setIndicator("Task"),
                TaskReportFragment.class, null);
        return view;
    }

    public static ReportFragment newInstance() {
        ReportFragment fragment = new ReportFragment();
        return fragment;
    }

}
