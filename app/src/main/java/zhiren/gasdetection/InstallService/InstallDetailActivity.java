package zhiren.gasdetection.InstallService;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import io.github.xudaojie.qrcodelib.CaptureActivity;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;

public class InstallDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.tvRight)
    TextView mTvRight;
    @BindView(R.id.tvNo)
    TextView mTvNo;
    @BindView(R.id.tvType)
    TextView mTvType;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.tvPhone)
    TextView mTvPhone;
    @BindView(R.id.tvAddress)
    TextView mTvAddress;
    @BindView(R.id.tvItem)
    TextView mTvItem;
    @BindView(R.id.etSerial)
    EditText mEtSerial;
    @BindView(R.id.ivScan)
    ImageView mIvScan;
    @BindView(R.id.tvUpPhoto)
    TextView mTvUpPhoto;
    @BindView(R.id.btnEnter)
    Button mBtnEnter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_install_detail;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String result = data.getStringExtra("result");
            Log.d("InstallDetailActivity",result);
            mEtSerial.setText(result);
        }
    }

    @OnClick({R.id.iv_back, R.id.ivScan, R.id.tvUpPhoto, R.id.btnEnter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ivScan:
                startActivityForResult(CaptureActivity.class,0);
                break;
            case R.id.tvUpPhoto:
                break;
            case R.id.btnEnter:
                break;
        }
    }
}
