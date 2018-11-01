package zhiren.gasdetection.TasksToDo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import model.CheckRecord;
import model.CheckTask;
import retrofit.Api;
import retrofit.RxHelper;
import retrofit.RxSubscriber;
import utils.FileToBase64Util;
import utils.ToastUtil;
import zhiren.gasdetection.AnJian.CheckListActivity;
import zhiren.gasdetection.AnJian.HistoryDataActivity;
import zhiren.gasdetection.AnJian.RefuseActivity;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;

import static utils.GlobalConfig.AUDIO_FORMAT;
import static utils.GlobalConfig.CHANNEL_CONFIG;
import static utils.GlobalConfig.SAMPLE_RATE_INHZ;


public class TaskDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.tvRight)
    TextView mTvRight;
    @BindView(R.id.ivEdit)
    ImageView mIvEdit;
    @BindView(R.id.tvNum)
    TextView mTvNum;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tvTel)
    TextView mTvTel;
    @BindView(R.id.tvCity)
    TextView mTvCity;
    @BindView(R.id.tvStreet)
    TextView mTvStreet;
    @BindView(R.id.tvArea)
    TextView mTvArea;
    @BindView(R.id.tvFire)
    TextView mTvFire;
    @BindView(R.id.ivRecord)
    ImageView mIvRecord;
    @BindView(R.id.btnEnter)
    Button mBtnEnter;
    @BindView(R.id.btnNotMeet)
    Button mBtnNotMeet;
    @BindView(R.id.btnNotAllow)
    Button mBtnNotAllow;
    @BindView(R.id.tvRecord)
    TextView mTvRecord;
    @BindView(R.id.chronometer)
    Chronometer mChronometer;
    @BindView(R.id.ivPlay)
    ImageView mIvPlay;

    /**
     * 需要申请的运行时权限
     */
    private String[] permissions = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    /**
     * 被用户拒绝的权限列表
     */
    private List<String> mPermissionList = new ArrayList<>();
    private int num = 0;//录音按钮点击的次数
    private static final int MY_PERMISSIONS_REQUEST = 1001;
    private boolean isRecording;
    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private FileInputStream fileInputStream;
    private static final String TAG = "TaskDetailActivity";
    private static final String Not_Allow = "拒绝入户";
    private static final String Not_Meet = "到访不遇";
    private static final String Normal_Enter = "正常入户";
    private int taskId, customer_id;
    private int flag;//区别点击的按钮
    private String base64 = "";// 录音文件流
    private String fileName = "";// 录音文件名

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.showToast(this, "权限被用户禁止！");
                }
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.ivEdit, R.id.ivRecord, R.id.ivPlay, R.id.btnEnter
            , R.id.btnNotMeet, R.id.btnNotAllow, R.id.tvRight})
    public void onViewClicked(View view) {
//      录音中状态点击非结束录音按钮弹框提示
        if (isRecording && view.getId() != R.id.ivRecord) {
            new AlertDialog.Builder(this)
                    .setMessage("请先停止录音。")
                    .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create()
                    .show();
        } else {
            switch (view.getId()) {
                case R.id.iv_back:
                    if (num == 0) {
                        finish();
                    } else {
                        new AlertDialog.Builder(this)
                                .setMessage("当前安检未完成，是否退出？")
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .create().show();
                    }
                    break;
                case R.id.ivEdit:
                    new AlertDialog.Builder(this)
                            .setTitle("当前客户信息正在审核，不可编辑。")
                            .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                    break;
                case R.id.ivPlay:
                    playInModeStream();
                    break;
                case R.id.ivRecord:
                    num++;
                    if (num % 2 == 0) {
                        mChronometer.stop();
                        mChronometer.setVisibility(View.GONE);
                        mIvRecord.setImageResource(R.mipmap.record_start_icon);
                        mTvRecord.setText("开始录音");
                        stopRecord();
                    } else {
                        mIvRecord.setImageResource(R.mipmap.record_stop_icon);
                        mTvRecord.setText("录音中");
                        mChronometer.setVisibility(View.VISIBLE);
                        mChronometer.setBase(SystemClock.elapsedRealtime());//计时前时间清零
                        mChronometer.start();
                        startRecord();
                    }
                    break;
                case R.id.btnEnter:
                    flag = 1;
                    addCheckRecord(Normal_Enter, base64, fileName);
                    break;
                case R.id.btnNotMeet:
                    flag = 2;
                    addCheckRecord(Not_Meet, base64, fileName);
                    break;
                case R.id.btnNotAllow:
                    flag = 3;
                    addCheckRecord(Not_Allow, base64, fileName);
                    break;
                case R.id.tvRight:
                    Bundle bundle = new Bundle();
                    bundle.putInt("customer_id", customer_id);
                    startActivity(HistoryDataActivity.class, bundle);
                    break;
            }
        }
    }

    public void startRecord() {
//      开始录音就隐藏播放按钮
        mIvPlay.setVisibility(View.GONE);
        final int minBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE_INHZ, CHANNEL_CONFIG, AUDIO_FORMAT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_INHZ,
                CHANNEL_CONFIG, AUDIO_FORMAT, minBufferSize);

        final byte data[] = new byte[minBufferSize];
        final File file = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "test.pcm");
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }
        if (file.exists()) {
            file.delete();
        }

        audioRecord.startRecording();
        isRecording = true;

        // TODO: 2018/3/10 pcm数据无法直接播放，保存为WAV格式。

        new Thread(new Runnable() {
            @Override
            public void run() {

                FileOutputStream os = null;
                try {
                    os = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                if (null != os) {
                    while (isRecording) {
                        int read = audioRecord.read(data, 0, minBufferSize);
                        // 如果读取音频数据没有出现错误，就将数据写入到文件
                        if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                            try {
                                os.write(data);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    try {
                        Log.i(TAG, "run: close file output stream !");
                        os.close();
                        base64 = FileToBase64Util.fileToBase64(file);
                        fileName = file.getName();
                        Log.d("base64", base64);
                        Log.d("base64", "fileName" + fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void stopRecord() {
        isRecording = false;
//      结束录音就显示播放按钮
        mIvPlay.setVisibility(View.VISIBLE);
//      释放资源
        if (null != audioRecord) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
    }

    /**
     * 播放，使用stream模式
     */
    private void playInModeStream() {
        /*
        * SAMPLE_RATE_INHZ 对应pcm音频的采样率
        * channelConfig 对应pcm音频的声道
        * AUDIO_FORMAT 对应pcm音频的格式
        * */
        int channelConfig = AudioFormat.CHANNEL_OUT_MONO;
        final int minBufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE_INHZ, channelConfig, AUDIO_FORMAT);
        audioTrack = new AudioTrack(
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build(),
                new AudioFormat.Builder().setSampleRate(SAMPLE_RATE_INHZ)
                        .setEncoding(AUDIO_FORMAT)
                        .setChannelMask(channelConfig)
                        .build(),
                minBufferSize,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE);
        audioTrack.play();

        File file = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "test.pcm");
        try {
            fileInputStream = new FileInputStream(file);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        byte[] tempBuffer = new byte[minBufferSize];
                        while (fileInputStream.available() > 0) {
                            int readCount = fileInputStream.read(tempBuffer);
                            if (readCount == AudioTrack.ERROR_INVALID_OPERATION ||
                                    readCount == AudioTrack.ERROR_BAD_VALUE) {
                                continue;
                            }
                            if (readCount != 0 && readCount != -1) {
                                audioTrack.write(tempBuffer, 0, readCount);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (num == 0) {
            finish();
        } else {
            if (isRecording) {
                new AlertDialog.Builder(this)
                        .setMessage("请先停止录音。")
                        .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create()
                        .show();
            } else {
                new AlertDialog.Builder(this)
                        .setMessage("当前安检未完成，是否退出？")
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .create().show();
            }

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_detail;
    }

    @Override
    protected void initData() {
        mText.setText("客户详情");
        mTvRight.setVisibility(View.VISIBLE);
        checkPermissions();
        CheckTask.TaskDataBean taskDataBean = (CheckTask.TaskDataBean) getIntent().getSerializableExtra("CheckTask");
        mTvNum.setText(taskDataBean.getCustomer_no_show());
        mTvName.setText(taskDataBean.getCustomer_name_show());
        mTvTel.setText(taskDataBean.getCustomer_tel_show());
        mTvCity.setText(taskDataBean.getCustomer_province_show() + taskDataBean.getCustomer_city_show());
        mTvStreet.setText(taskDataBean.getCustomer_street_show());
        mTvArea.setText(taskDataBean.getCustomer_address_show());
        mTvFire.setText(taskDataBean.getCustomer_ventilate_status_show().equals("2") ? "未通气" : "已通气");
        taskId = taskDataBean.getId();
        customer_id = taskDataBean.getCustomer_id();
        Log.d("base64", taskId + "");
    }

    private void checkPermissions() {
        // Marshmallow开始才用申请运行时权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) !=
                        PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(permissions[i]);
                }
            }
            if (!mPermissionList.isEmpty()) {
                String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);
                ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST);
            }
        }
    }

    public void addCheckRecord(String checkDetail, String file64, String fileName) {
        Api.getDefault().addCheckData(taskId, checkDetail, file64, fileName)
                .compose(RxHelper.<CheckRecord>handleResult())
                .subscribe(new RxSubscriber<CheckRecord>(this) {
                    @Override
                    protected void _onNext(CheckRecord checkRecord) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("flag", flag);
                        bundle.putInt("check_data_id", checkRecord.getId());
                        switch (flag) {
                            case 1:
                                startActivity(CheckListActivity.class, bundle);
                                break;
                            case 2:
                                startActivity(RefuseActivity.class, bundle);
                                break;
                            case 3:
                                startActivity(RefuseActivity.class, bundle);
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(TaskDetailActivity.this, message);
                    }
                });
    }
}
