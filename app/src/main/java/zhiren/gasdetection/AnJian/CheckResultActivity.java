package zhiren.gasdetection.AnJian;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qs.helper.printer.BlueToothService;
import com.qs.helper.printer.BtService;
import com.qs.helper.printer.ClsUtils;
import com.qs.helper.printer.PrintService;
import com.qs.helper.printer.PrinterClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import model.CheckResult;
import retrofit.Api;
import retrofit.RxHelper;
import retrofit.RxSubscriber;
import utils.ToastUtil;
import utils.UrlHelper;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;
import zhiren.gasdetection.UserLogin.MainActivity;

import static utils.PrinterUtil.printText;

public class CheckResultActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.tvNo)
    TextView mTvNo;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tvPhone)
    TextView mTvPhone;
    @BindView(R.id.tvTableNo)
    TextView mTvTableNo;
    @BindView(R.id.tvType)
    TextView mTvType;
    @BindView(R.id.tvCommunity)
    TextView mTvCommunity;
    @BindView(R.id.tvStaffName)
    TextView mTvStaffName;
    @BindView(R.id.tvStaffNo)
    TextView mTvStaffNo;
    @BindView(R.id.tvStartTime)
    TextView mTvStartTime;
    @BindView(R.id.tvFinishTime)
    TextView mTvFinishTime;
    @BindView(R.id.tvRuHu)
    TextView mTvRuHu;
    @BindView(R.id.tvZhengGai)
    TextView mTvZhengGai;
    @BindView(R.id.tvFirst)
    TextView mTvFirst;
    @BindView(R.id.tvFirstSolve)
    TextView mTvFirstSolve;
    @BindView(R.id.tvSecond)
    TextView mTvSecond;
    @BindView(R.id.tvSecondSolve)
    TextView mTvSecondSolve;
    @BindView(R.id.tvThird)
    TextView mTvThird;
    @BindView(R.id.tvThirdSolve)
    TextView mTvThirdSolve;
    @BindView(R.id.tvPay)
    TextView mTvPay;
    @BindView(R.id.tvSum)
    TextView mTvSum;
    @BindView(R.id.tvPayType)
    TextView mTvPayType;
    @BindView(R.id.tvExpire1)
    TextView mTvExpire1;
    @BindView(R.id.tvExpire2)
    TextView mTvExpire2;
    @BindView(R.id.tvExpire3)
    TextView mTvExpire3;
    @BindView(R.id.ivSign)
    ImageView mIvSign;
    @BindView(R.id.btnBack)
    Button mBtnBack;
    @BindView(R.id.btnPrint)
    Button mBtnPrint;

    private BluetoothAdapter btAdapt;
    private Handler handler = null;
    private Handler mhandler = null;
    private BlueToothService service;
    protected static final String TAG = "CheckResultActivity";
    List<String> deviceList = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    boolean isconnect = false;
    public static PrinterClass pl = null;// 打印机操作类
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    private BottomSheetDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_result;
    }

    @Override
    protected void initData() {
        getDetail();
        dialog = new BottomSheetDialog(this);
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.bottom_dialog, null);
        ListView listView = dialogView.findViewById(R.id.list);
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, deviceList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new ItemClickEvent());
        dialog.setContentView(dialogView);

        mText.setText("安检结果");
        btAdapt = BluetoothAdapter.getDefaultAdapter();// 初始化本机蓝牙功能

        if (btAdapt.getState() == BluetoothAdapter.STATE_OFF) {// 如果蓝牙还没开启
            new AlertDialog.Builder(this)
                    .setMessage("开始打印前，请开启手机蓝牙！")
                    .setCancelable(false)
                    .setPositiveButton("打开蓝牙", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            btAdapt.enable();
                        }
                    }).create().show();
        }

        mhandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_READ:
                        byte[] readBuf = (byte[]) msg.obj;
                        Log.i(TAG, "readBuf:" + readBuf[0]);
                        if (readBuf[0] == 0x13) {
                            PrintService.isFUll = true;
                        } else if (readBuf[0] == 0x11) {
                            PrintService.isFUll = false;
                        } else if (readBuf[0] == 0x08) {
                        } else if (readBuf[0] == 0x01) {
                            // ShowMsg(getResources().getString(R.string.str_printer_state)+":"+getResources().getString(R.string.str_printer_printing));
                        } else if (readBuf[0] == 0x04) {
                        } else if (readBuf[0] == 0x02) {
                        } else {
                            String readMessage = new String(readBuf, 0, msg.arg1);
                            Log.e("", "readMessage" + readMessage);
                            if (readMessage.contains("800"))// 80mm paper
                            {
                                PrintService.imageWidth = 72;
                                Log.e("", "imageWidth:" + "80mm");
                            } else if (readMessage.contains("580"))// 58mm paper
                            {
                                PrintService.imageWidth = 48;
                                Log.e("", "imageWidth:" + "58mm");
                            }
                        }
                        break;
                    case MESSAGE_STATE_CHANGE:// 蓝牙连接状
                        switch (msg.arg1) {
                            case PrinterClass.STATE_CONNECTED:// 已经连接
                                break;
                            case PrinterClass.STATE_CONNECTING:// 正在连接
                                ToastUtil.showToast(CheckResultActivity.this, "STATE_CONNECTING");
                                break;
                            case PrinterClass.STATE_LISTEN:
                            case PrinterClass.STATE_NONE:
                                break;
                            case PrinterClass.SUCCESS_CONNECT:
                                pl.write(new byte[]{0x1b, 0x2b});// 检测打印机型号
                                ToastUtil.showToast(CheckResultActivity.this, "打印机连接成功");
                                break;
                            case PrinterClass.FAILED_CONNECT:
                                ToastUtil.showToast(CheckResultActivity.this, "打印机连接失败");
                                break;
                            case PrinterClass.LOSE_CONNECT:
                                ToastUtil.showToast(CheckResultActivity.this, "打印机断开连接");
                                break;
                        }
                        break;
                    case MESSAGE_WRITE:
                        break;
                }
                super.handleMessage(msg);
            }
        };

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        break;
                    case 1:// 扫描完毕
                        break;
                    case 2:// 停止扫描
                        break;
                }
            }
        };

        CheckResultActivity.pl = new BtService(this, mhandler, handler);
        thread();

        Log.d("btAdaptbtAdapt", "ACTION_PAIRING_REQUEST");

        // 注册Receiver来获取蓝牙设备相关的结果
        String ACTION_PAIRING_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST";
        IntentFilter intent = new IntentFilter();
        intent.addAction(BluetoothDevice.ACTION_FOUND);// 用BroadcastReceiver来取得搜索结果
        intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intent.addAction(ACTION_PAIRING_REQUEST);
        intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(searchDevices, intent);
    }

    //    单独创建一个线程，监听打印机连接状态
    private void thread() {
        // TODO Auto-generated method stub
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    mBtnPrint.post(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            if (pl != null) {
                                if (pl.getState() == PrinterClass.STATE_CONNECTED) {
                                    isconnect = true;
                                } else if (pl.getState() == PrinterClass.STATE_CONNECTING) {
                                } else if (pl.getState() == PrinterClass.STATE_SCAN_STOP) {
                                } else if (pl.getState() == PrinterClass.STATE_SCANING) {
                                }
                            }
                        }
                    });
                }
            }
        };
        thread.start();
    }

    public void getDetail() {
        int check_data_id = getIntent().getExtras().getInt("check_data_id");
        Api.getDefault().getCheckResult(check_data_id)
                .compose(RxHelper.<CheckResult>handleResult())
                .subscribe(new RxSubscriber<CheckResult>(this) {
                    @Override
                    protected void _onNext(CheckResult checkResult) {
                        mTvNo.setText(checkResult.getCustomer().getCustomer_no());
                        mTvName.setText(checkResult.getCustomer().getName());
                        mTvPhone.setText(checkResult.getCustomer().getTel());
//                        mTvTableNo.setText(checkResult.getCustomer());
                        mTvType.setText(checkResult.getCustomer().getCustomer_type());
                        mTvCommunity.setText(checkResult.getCustomer().getArea());

                        mTvStaffName.setText(checkResult.getStaff().getRealname());
                        mTvStaffNo.setText(checkResult.getStaff().getStaffno());
                        mTvStartTime.setText(checkResult.getCheck_data().getVisit_time_show());
                        mTvFinishTime.setText(checkResult.getCheck_data().getFinish_time_show());

                        mTvRuHu.setText(checkResult.getCheck_data().getCheck_detail());
                        mTvZhengGai.setText(checkResult.getCheck_data().getCheck_result());

                        mTvPay.setText(checkResult.getFee().getPay_status() == null ? "免费完成" : checkResult.getFee().getPay_status());
                        mTvSum.setText(checkResult.getFee().getTotal_fee() + "");
                        mTvPayType.setText(checkResult.getFee().getPay_by() == null ? "无" : checkResult.getFee().getPay_by());

//                        mTvExpire1.setText(checkResult.getCheck_data().get);
//                        mTvExpire2.setText();
//                        mTvExpire3.setText();
                        Glide.with(CheckResultActivity.this).load(UrlHelper.URL_IP + "/" + checkResult.getCheck_data().getSign_picture()).into(mIvSign);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(CheckResultActivity.this, message);
                    }

                    @Override
                    protected boolean showDialog() {
                        return false;
                    }
                });
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(searchDevices);
        super.onDestroy();
    }

    private BroadcastReceiver searchDevices = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Bundle b = intent.getExtras();
            Object[] lstName = b.keySet().toArray();
            Log.d("btAdaptbtAdapt", "action:" + action);

            // 显示所有收到的消息及其细节
            for (int i = 0; i < lstName.length; i++) {
                String keyName = lstName[i].toString();
                Log.e(keyName, String.valueOf(b.get(keyName)));
            }
            BluetoothDevice device = null;
            // 搜索设备时，取得设备的MAC地址
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                    String str = device.getName() + "|" + device.getAddress();
                    Log.d("btAdaptbtAdapt", "ACTION_FOUND:" + str);
                    if (deviceList.indexOf(str) == -1)// 防止重复添加
                        deviceList.add(str); // 获取设备名称和mac地址
                    arrayAdapter.notifyDataSetChanged();
                    try {
                        ClsUtils.setPin(device.getClass(), device, "0000");
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        ClsUtils.cancelPairingUserInput(device.getClass(),
                                device);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                switch (device.getBondState()) {
                    case BluetoothDevice.BOND_BONDING:
                        Log.e("BlueToothTestActivity", "正在配对......");
                        break;
                    case BluetoothDevice.BOND_BONDED:
                        Log.e("BlueToothTestActivity", "完成配对");
                        // connect(device);//连接设备
                        break;
                    case BluetoothDevice.BOND_NONE:
                        Log.e("BlueToothTestActivity", "取消配对");
                    default:
                        break;
                }
            }
        }
    };

    public void searchBlueToothDevice() {
        Log.d("btAdaptbtAdapt", btAdapt.isEnabled() + "");
        if (btAdapt.isDiscovering())
            btAdapt.cancelDiscovery();
        deviceList.clear();
        Object[] lstDevice = btAdapt.getBondedDevices().toArray();
        Log.d("btAdaptbtAdapt", lstDevice.length + "");
        for (int i = 0; i < lstDevice.length; i++) {
            BluetoothDevice device = (BluetoothDevice) lstDevice[i];
            String str = "" + device.getName() + "|"
                    + device.getAddress();
            deviceList.add(str); // 获取设备名称和mac地址
            arrayAdapter.notifyDataSetChanged();
        }
        btAdapt.startDiscovery();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(MainActivity.class);
    }

    @OnClick({R.id.iv_back, R.id.btnBack, R.id.btnPrint})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
            case R.id.btnBack:
                startActivity(MainActivity.class);
                break;
            case R.id.btnPrint:
                if (isconnect) {
                    printText("hello world:" + "\n\n");
                    finish();
                } else {
                    searchBlueToothDevice();
                }
                break;
        }
    }


    class ItemClickEvent implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            if (btAdapt.isDiscovering())
                btAdapt.cancelDiscovery();
            String str = deviceList.get(arg2);
            String[] values = str.split("\\|");
            String address = values[1];
            Log.e("address", address);
            BluetoothDevice btDev = btAdapt.getRemoteDevice(address);
            try {
                isconnect = false;
                CheckResultActivity.pl.disconnect();
                CheckResultActivity.pl.connect(btDev.toString());
                Log.e("address", btDev.toString() + "   btDev");
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
