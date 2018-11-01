package zhiren.gasdetection;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Author: andy
 * Time:2018/9/13 0013
 * Description:
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
