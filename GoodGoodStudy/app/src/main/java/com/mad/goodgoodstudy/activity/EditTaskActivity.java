package com.mad.goodgoodstudy.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mad.goodgoodstudy.model.Task;
import com.mad.goodgoodstudy.R;
import com.mad.goodgoodstudy.util.TimeUtil;

public class EditTaskActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String IS_NEW_TASK = "Is new task";
    public static final String TASK_ID = "Task id";
    public static final String TASK_TYPE = "Task type";
    public static final String[] TASK_TYPES = {"Daily", "One time", "Long-term"};


    private Task mTask = null;

    private Button mSaveBtn;
    private Button mCancelBtn;
    private EditText mTaskNameEt;
    private TextView mHeaderTv;
    private TextView mStartTimeTv;
    private TextView mHourTv;
    private TextView mMinTv;
    private TextView mPlannedTimesTv;
    private NumberPicker mHourPicker;
    private NumberPicker mMinPicker;
    private NumberPicker mTimesPicker;
    private Spinner mTaskTypeSpn;
    private CheckBox mNoiseMonitorChk;
    private CheckBox mSoftwareMonitorChk;
    private TableRow mStartTimeTr;
    private TableRow mPlannedTimesTr;
    private TableRow mTaskTypeTr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        initialize();

        Intent intent = getIntent();
        if( intent != null){
            if (intent.getBooleanExtra(IS_NEW_TASK, true)) {

                mTask = new Task();
                mHeaderTv.setText( getResources().getString(R.string.new_task) );

            }
            else {
                initialDataForEdition(intent);
            }
        }


    }

    /**
     * This method is to initialize all necessary fields in this activity.
     */
    public void initialize(){
        mHeaderTv = (TextView) findViewById(R.id.activity_edit_task_header_tv);
        mTaskNameEt = (EditText) findViewById(R.id.activity_edit_task_task_name_et);
        mCancelBtn = (Button) findViewById(R.id.activity_edit_task_cancel_btn);
        mSaveBtn = (Button) findViewById(R.id.activity_edit_task_save_btn);
        mStartTimeTv = (TextView) findViewById(R.id.activity_edit_task_start_time_tv);
        mPlannedTimesTv = (TextView) findViewById(R.id.activity_edit_task_planed_time_tv);
        mHourTv = (TextView) findViewById(R.id.activity_edit_task_hour_tv);
        mMinTv = (TextView) findViewById(R.id.activity_edit_task_min_tv);
        mTaskTypeSpn = (Spinner) findViewById(R.id.activity_edit_task_task_type_spn);
        mStartTimeTr = (TableRow) findViewById(R.id.activity_edit_task_start_time_tr);
        mPlannedTimesTr = (TableRow) findViewById(R.id.activity_edit_task_planned_time_tr);
        mTaskTypeTr = (TableRow) findViewById(R.id.activity_edit_task_task_type_tr);
        mNoiseMonitorChk = (CheckBox) findViewById(R.id.activity_edit_task_noise_moniter_chk);
        mSoftwareMonitorChk = (CheckBox) findViewById(R.id.activity_edit_task_software_moniter_chk);

        mHourPicker = new NumberPicker(EditTaskActivity.this);
        mHourPicker.setMaxValue(24);
        mHourPicker.setMinValue(0);
        mMinPicker = new NumberPicker(EditTaskActivity.this);
        mMinPicker.setMinValue(0);
        mMinPicker.setMaxValue(59);
        mTimesPicker = new NumberPicker(EditTaskActivity.this);
        mTimesPicker.setMinValue(1);
        mTimesPicker.setMaxValue(999);

        mCancelBtn.setOnClickListener(this);
        mHourTv.setOnClickListener(this);
        mMinTv.setOnClickListener(this);
        mStartTimeTv.setOnClickListener(this);
        mHourTv.setOnClickListener( this );
        mPlannedTimesTv.setOnClickListener(this);
        mSaveBtn.setOnClickListener(this);

        mTaskTypeSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: //Daily
                        mStartTimeTr.setVisibility(View.VISIBLE);
                        mPlannedTimesTr.setVisibility(View.GONE);
                        break;
                    case 1://One time
                        mStartTimeTr.setVisibility(View.GONE);
                        mPlannedTimesTr.setVisibility(View.GONE);
                        break;
                    case 2:
                        mStartTimeTr.setVisibility(View.GONE);
                        mPlannedTimesTr.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mTaskTypeSpn.setSelection(0);

            }
        });
    }

    /**
     * To show a dialog with a number picker, and change the value of the text due to according to
     * the number picked.
     * @param numberPicker
     * @param textView
     */
    public void showDialogWithNumberPicker(final NumberPicker numberPicker, final TextView textView){

        AlertDialog.Builder builder = new AlertDialog.Builder(EditTaskActivity.this);
        builder.setView(numberPicker);
        builder.setPositiveButton( getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                textView.setText( numberPicker.getValue() + "" );
            }
        });
        builder.setNegativeButton( getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_edit_task_hour_tv:
                showDialogWithNumberPicker( mHourPicker, mHourTv );
                break;
            case R.id.activity_edit_task_min_tv:
                showDialogWithNumberPicker( mMinPicker, mMinTv);
                break;
            case R.id.activity_edit_task_start_time_tv:
                showTimePickerDialog();
                break;
            case R.id.activity_edit_task_planed_time_tv:
                showDialogWithNumberPicker( mTimesPicker, mPlannedTimesTv);
                break;
            case R.id.activity_edit_task_save_btn:
                save();
                break;
            case R.id.activity_edit_task_cancel_btn:
                finish();
                break;
        }
    }

    /**
     * Save the edit task.
     */
    public void save(){

        if(isAllNecessaryDataComplete( getApplicationContext())){

            switch ( mTaskTypeSpn.getSelectedItemPosition() ){
                case 0:
                    mTask.setStartTime( mStartTimeTv.getText().toString());
                    break;
                case 1:
                    break;
                case 2:
                    mTask.setPlannedTimes( mTimesPicker.getValue() );
                    break;
            }

            mTask.setName( mTaskNameEt.getText().toString() );
            mTask.setDuration( TimeUtil.getDurationFromHourMin(
                    mHourPicker.getValue(), mMinPicker.getValue()));
            mTask.setNoiseMonitor( mNoiseMonitorChk.isChecked() );
            mTask.setSoftwareMonitor( mSoftwareMonitorChk.isChecked());
            mTask.setTaskType( mTaskTypeSpn.getSelectedItemPosition());
            mTask.save();

            if( mTask.isDailyTask() ){
                Toast.makeText(this, getResources().getString(R.string.create_daily_task_hint),
                        Toast.LENGTH_SHORT).show();
            }

            Intent result = new Intent();
            result.putExtra(TASK_ID, mTask.getId());
            setResult(RESULT_OK, result);
            finish();
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.incomplete_hint),
                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Initialize the view from an existing task.
     * @param intent
     */
    public void initialDataForEdition(Intent intent){
        Long id = intent.getLongExtra("Task id", -1);
        String taskType = intent.getStringExtra(TASK_TYPE);
        if( id != -1 ){
            try {

                mTask = Task.findById(Task.class, id);

                switch ( mTask.getTaskType() ){
                    case 0: //Daily task
                        mStartTimeTv.setText( mTask.getStartTime() );
                        break;
                    case 1: //One time task
                        break;
                    case 2: //Long-term task.
                        mPlannedTimesTv.setText( mTask.getPlannedTimes().toString() );
                        mTimesPicker.setValue( mTask.getPlannedTimes() );
                        break;
                }

                mTaskTypeSpn.setSelection( mTask.getTaskType());
                mTaskNameEt.setText( mTask.getName() );
                mHourTv.setText( TimeUtil.getHourFromDuration( mTask.getDuration()) + "" );
                mHourPicker.setValue(TimeUtil.getHourFromDuration( mTask.getDuration()));
                mMinTv.setText( TimeUtil.getMinFromDuration(mTask.getDuration()) + "" );
                mMinPicker.setValue(TimeUtil.getMinFromDuration(mTask.getDuration()));
                mSoftwareMonitorChk.setChecked( mTask.isSoftwareMonitor() );
                mNoiseMonitorChk.setChecked( mTask.isNoiseMonitor() );
                mTaskTypeTr.setVisibility(View.GONE);

            }
            catch ( NullPointerException e ){
                mTask = new Task();
                e.printStackTrace();
            }

        }


    }

    /**
     * To show a time picker dialog, and change the content of corresponding text view.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showTimePickerDialog(){
        Calendar c = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(EditTaskActivity.this,
                new OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        mStartTimeTv.setText( TimeUtil.getTimeStringFromHourMin(hour, minute));
                    }
                }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    public boolean isAllNecessaryDataComplete(Context context){
        boolean isComplete = !(mTaskNameEt.getText().toString().isEmpty()) && !(mHourTv.getText().
                toString().equals(context.getString(R.string.choose))) &&
                !(mMinTv.getText().toString().equals(context.getString(R.string.choose)));
        switch ( mTaskTypeSpn.getSelectedItemPosition()){
            case 0: //Daily task
                isComplete = isComplete && !(mStartTimeTv.getText().toString().isEmpty());
                break;
            case 1: //One time task
                break;
            case 2: //Long-term task.
                isComplete = isComplete && !(mPlannedTimesTv.getText().toString().isEmpty());
                break;
        }
        return isComplete;
    }


}