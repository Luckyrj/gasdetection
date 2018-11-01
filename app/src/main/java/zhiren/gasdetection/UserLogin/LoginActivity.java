package zhiren.gasdetection.UserLogin;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.DeviceUtils;

import butterknife.BindView;
import butterknife.OnClick;
import model.SystemUser;
import retrofit.Api;
import retrofit.RxHelper;
import retrofit.RxSubscriber;
import utils.ACache;
import utils.CheckNetwork;
import utils.SPHelper;
import utils.StringUtil;
import utils.ToastUtil;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etPhone)
    EditText mEtPhone;
    @BindView(R.id.etPW)
    EditText mEtPW;
    @BindView(R.id.btnLogin)
    Button mBtnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
//      测试条件下，长按自动登录，正式删除
        mBtnLogin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                login("15678945614", "888888");
                return true;
            }
        });
    }

    @OnClick(R.id.btnLogin)
    public void onViewClicked() {
//      收起软键盘
        hideSoftInput();
        boolean connected = CheckNetwork.isNetworkConnected(this);
        if (connected) {
            String phone = mEtPhone.getText().toString();
            String password = mEtPW.getText().toString();
            if (StringUtil.isEmpty(phone) || StringUtil.isEmpty(password)) {
                ToastUtil.showToast(this, "请输入手机号和密码");
            } else {
                login(phone, password);
            }
        } else {
            ToastUtil.showToast(this, "网络未连接");
        }
    }

    public void login(String phone, String password) {
        String macAddress = DeviceUtils.getMacAddress();
        String model = DeviceUtils.getModel();
        Api.getDefault().login(phone, password, macAddress, model)
                .compose(RxHelper.<SystemUser>handleResult())
                .subscribe(new RxSubscriber<SystemUser>(this) {
                    @Override
                    protected void _onNext(SystemUser systemUser) {
                        SPHelper.putInt(LoginActivity.this, "id", systemUser.getId());
                        ACache.get(LoginActivity.this).put("user", systemUser);
                        startActivity(MainActivity.class);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(LoginActivity.this, message);
                    }
                });
    }
}
