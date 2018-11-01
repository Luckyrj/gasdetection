package zhiren.gasdetection.AnJian;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.OnClick;
import model.SystemUser;
import retrofit.Api;
import retrofit.RxHelper;
import retrofit.RxSubscriber;
import utils.ToastUtil;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;

//  宣讲页面
public class PreachActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.toggleButton1)
    ToggleButton mToggleButton1;
    @BindView(R.id.toggleButton2)
    ToggleButton mToggleButton2;
    @BindView(R.id.toggleButton3)
    ToggleButton mToggleButton3;
    @BindView(R.id.toggleButton4)
    ToggleButton mToggleButton4;
    @BindView(R.id.btnEnter)
    Button mBtnEnter;

    private int check_data_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preach;
    }

    @Override
    protected void initData() {
        mText.setText("燃气安全知识宣讲");
        check_data_id = getIntent().getExtras().getInt("check_data_id");
    }

    @OnClick({R.id.iv_back, R.id.btnEnter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btnEnter:
                String publicity = "";
                if (mToggleButton1.isChecked()) {
                    publicity += 1;
                } else {
                    publicity += 0;
                }
                if (mToggleButton2.isChecked()) {
                    publicity += 1;
                } else {
                    publicity += 0;
                }
                if (mToggleButton3.isChecked()) {
                    publicity += 1;
                } else {
                    publicity += 0;
                }
                if (mToggleButton4.isChecked()) {
                    publicity += 1;
                } else {
                    publicity += 0;
                }
                Log.d("publicity", publicity);
                Api.getDefault().publicityCommit(check_data_id, publicity)
                        .compose(RxHelper.<SystemUser>handleResult())
                        .subscribe(new RxSubscriber<SystemUser>(this) {
                            @Override
                            protected void _onNext(SystemUser systemUser) {
                                Bundle bundle = new Bundle();
                                bundle.putInt("check_data_id", check_data_id);
                                startActivity(ClientSignatureActivity.class,bundle);
                            }

                            @Override
                            protected void _onError(String message) {
                                ToastUtil.showToast(PreachActivity.this, message);
                            }
                        });
                break;
        }
    }
}
