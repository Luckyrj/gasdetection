package zhiren.gasdetection.UserLogin;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import model.SystemUser;
import retrofit.Api;
import retrofit.RxHelper;
import retrofit.RxSubscriber;
import utils.StringUtil;
import utils.ToastUtil;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;

public class ChangePWActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.etOld)
    EditText mEtOld;
    @BindView(R.id.etNew)
    EditText mEtNew;
    @BindView(R.id.etAgain)
    EditText mEtAgain;
    @BindView(R.id.btn)
    Button mBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pw;
    }

    @Override
    protected void initData() {
        mText.setText("修改密码");
    }

    @OnClick({R.id.iv_back, R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn:
                changePwd();
                break;
        }
    }

    public void changePwd() {
        hideSoftInput();
        String oldPwd = mEtOld.getText().toString();
        String newPwd = mEtNew.getText().toString();
        String againPwd = mEtAgain.getText().toString();
        String tel = getIntent().getExtras().getString("tel");
        if (StringUtil.isEmpty(oldPwd) || StringUtil.isEmpty(newPwd) || StringUtil.isEmpty(againPwd)) {
            ToastUtil.showToast(this, "请输入密码");
            return;
        }
        if (newPwd.length() < 6) {
            ToastUtil.showToast(this, "密码长度不能小于6位");
            return;
        } else {
            if (!newPwd.equals(againPwd)) {
                ToastUtil.showToast(this, "确认新密码有误，请重新输入");
                mEtAgain.setText("");
                return;
            }
        }
        Api.getDefault().updatePwd(tel, oldPwd, newPwd)
                .compose(RxHelper.<SystemUser>handleResult())
                .subscribe(new RxSubscriber<SystemUser>(this) {
                    @Override
                    protected void _onNext(SystemUser systemUser) {
                        ToastUtil.showToast(ChangePWActivity.this, "修改成功");
                        finish();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(ChangePWActivity.this, message);
                    }
                });
    }
}
