package com.mad.goodgoodstudy.monitor;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * Created by Tong on 2017/5/15.
 */

public class NoiseMonitor {
    private static final String TAG = "NoiseMonitor";
    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
    AudioRecord mAudioRecord;
    private boolean mIsMonitoring;
    private double mNoiseLevel = 30;
    Object mLock;

    public NoiseMonitor() {
        mLock = new Object();
    }

    public NoiseMonitor( double noiseLevel) {
        mLock = new Object();
        mNoiseLevel = noiseLevel;
    }

    /**
     * To start the monitor.
     */
    public void start(){

        if( mIsMonitoring == true) return;

        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        mIsMonitoring = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                mAudioRecord.startRecording();
                short[] buffer = new short[BUFFER_SIZE];
                while (mIsMonitoring) {
                    double volume = dataHandling( buffer );
                    if(isTooNoisy(volume)) //...show alert
                    // 1 time 3 seconds
                    synchronized (mLock) {
                        try {
                            mLock.wait(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    /**
     * Read a set of data from from the buffer.
     * Handle the data by seeking square sums, to avoid the influence of individual extremes
     * and makes the results more stable.
     * @param buffer
     * @return
     */
    private double dataHandling(short[] buffer){

        int dataLength = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
        long squareSum = 0;
        // To get the squareSum.
        for (short b: buffer) {
            squareSum += b*b;
        }
        // Get the final volume
        double volume = 10 * Math.log10(squareSum / (double) dataLength);
        return volume;
    }

    /**
     * To check the noisy is larger than the preSet value.
     * @param volume
     * @return
     */
    public boolean isTooNoisy(double volume){
        if( volume > mNoiseLevel ) return true;
        return false;
    }

    /**
     * To stop the monitor.
     */
    public void stop(){
        mAudioRecord.stop();
        mAudioRecord.release();
        mAudioRecord = null;
    }
}