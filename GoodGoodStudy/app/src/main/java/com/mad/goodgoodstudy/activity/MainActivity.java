package com.mad.goodgoodstudy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mad.goodgoodstudy.fragment.ReportFragment;
import com.mad.goodgoodstudy.fragment.TaskListFragment;
import com.mad.goodgoodstudy.fragment.TaskTodayFragment;
import com.mad.goodgoodstudy.fragment.WishTreeFragment;
import com.mad.goodgoodstudy.R;
import com.mad.goodgoodstudy.service.ManageTaskService;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, ManageTaskService.class);
        startService(intent);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        BottomNavigationView bottomNavigationView =(BottomNavigationView) findViewById(
                R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.action_task_today:
                        selectedFragment = TaskTodayFragment.newInstance();
                        break;
                    case R.id.action_task_list:
                        selectedFragment = TaskListFragment.newInstance();
                        break;
                    case R.id.action_report:
                        selectedFragment = ReportFragment.newInstance();
                        break;
                    case R.id.action_wish_tree:
                        selectedFragment = WishTreeFragment.newInstance();
                        break;
                    case R.id.action_exit:
                        finish();
                        break;
                        // to
                }
                if( selectedFragment != null){
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                }
                return true;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, TaskTodayFragment.newInstance());
        transaction.commit();
        bottomNavigationView.getMenu().getItem(0).setChecked(true);


    }

}
