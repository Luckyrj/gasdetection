package zhiren.gasdetection.AnJian;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import model.CheckRecord;
import retrofit.Api;
import retrofit.RxHelper;
import retrofit.RxSubscriber;
import utils.FileToBase64Util;
import utils.ToastUtil;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;
import zhiren.gasdetection.TasksToDo.FeeListActivity;
import zhiren.gasdetection.TasksToDo.SignatureActivity;

// 确认签名页面
public class ClientSignatureActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.tvUpPhoto)
    TextView mTvUpPhoto;
    @BindView(R.id.ivSignature)
    ImageView mIvSignature;
    @BindView(R.id.btn1)
    RadioButton mBtn1;
    @BindView(R.id.btn2)
    RadioButton mBtn2;
    @BindView(R.id.btn3)
    RadioButton mBtn3;
    @BindView(R.id.btnFree)
    Button mBtnFree;
    @BindView(R.id.btnCharge)
    Button mBtnCharge;

    private int check_data_id;
    private String sign_picture = ""; //Base64
    private String check_result; //安检结果

    @Override
    protected int getLayoutId() {
        return R.layout.activity_client_signature;
    }

    @Override
    protected void initData() {
        mText.setText("确认签字");
        check_data_id = getIntent().getExtras().getInt("check_data_id");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    byte[] bytes = data.getByteArrayExtra("bitmap");
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                  Bitmap bitmap = data.getParcelableExtra("bitmap");
                    Log.d("signatureBitmap", "??" + bitmap.toString());
                    mTvUpPhoto.setVisibility(View.GONE);
                    mIvSignature.setVisibility(View.VISIBLE);
                    mIvSignature.setImageBitmap(bitmap);
                    sign_picture = FileToBase64Util.bitmapToBase64(bitmap);
                    break;
            }
        }
    }

    //   确认签字，免费完成还是收费
    public void signCommit(final boolean isCharge, String check_result) {
        Api.getDefault().confirmSignCommit(check_data_id, sign_picture, sign_picture.equals("") ? "" : "_sign.JPEG", isCharge, check_result)
                .compose(RxHelper.<CheckRecord>handleResult())
                .subscribe(new RxSubscriber<CheckRecord>(this) {
                    @Override
                    protected void _onNext(CheckRecord checkRecord) {
                        if (isCharge) {
                            startActivity(FeeListActivity.class);
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ClientSignatureActivity.this);
                            final Dialog dialog = builder.create();
                            View v = LayoutInflater.from(ClientSignatureActivity.this).inflate(R.layout.dialog_scan_qr, null);
                            TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
                            TextView tvEnter = (TextView) v.findViewById(R.id.tvEnter);
                            tvEnter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("check_data_id", check_data_id);
                                    startActivity(CheckResultActivity.class, bundle);
                                }
                            });
                            tvTitle.setText("关注城市燃气安全管家\n" +
                                    "绑定后即可查询本次服务记录");
                            //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
                            dialog.show();
                            dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(ClientSignatureActivity.this, message);
                    }
                });
    }

    @OnClick({R.id.iv_back, R.id.tvUpPhoto, R.id.ivSignature, R.id.btnFree, R.id.btnCharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ivSignature:
            case R.id.tvUpPhoto:
                Intent intent = new Intent(ClientSignatureActivity.this, SignatureActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.btnFree:
                if (mBtn1.isChecked()) {
                    check_result = "需要整改安装";
                } else if (mBtn2.isChecked()) {
                    check_result = "自行整改安装";
                } else if (mBtn3.isChecked()) {
                    check_result = "不整改安装";
                }
                signCommit(false, check_result);
                break;
            case R.id.btnCharge:
                if (mBtn1.isChecked()) {
                    check_result = "需要整改安装";
                } else if (mBtn2.isChecked()) {
                    check_result = "自行整改安装";
                } else if (mBtn3.isChecked()) {
                    check_result = "不整改安装";
                }
                signCommit(true, check_result);
                break;
        }
    }
}
