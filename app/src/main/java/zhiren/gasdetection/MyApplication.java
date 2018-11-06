package zhiren.gasdetection;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.blankj.utilcode.util.Utils;

/**
 * Author: andy
 * Time:2018/9/13 0013
 * Description:
 */

public class MyApplication extends Application {
    private static Context     mContext;
    private static long        mMainThreadId;
    private static Handler mMainHandler;
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        mContext = getApplicationContext();
        mMainThreadId = android.os.Process.myTid();

        mMainHandler = new Handler();
    }


    public static Context getContext() {
        return mContext;
    }



    public static long getMainThreadId() {
        return mMainThreadId;
    }


    public static Handler getMainHandler() {
        return mMainHandler;
    }

}
