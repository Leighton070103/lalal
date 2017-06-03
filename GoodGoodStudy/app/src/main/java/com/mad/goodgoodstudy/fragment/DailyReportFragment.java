package com.mad.goodgoodstudy.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mad.goodgoodstudy.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DailyReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyReportFragment extends Fragment {

    private String mParam1;
    private String mParam2;

  //  private OnFragmentInteractionListener mListener;

    public DailyReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DailyReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyReportFragment newInstance(String param1, String param2) {
        DailyReportFragment fragment = new DailyReportFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily_report, container, false);
    }

}
