package zhiren.gasdetection.AnJian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

/*
* 到访不遇/拒绝入户
* */
public class RefuseActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.tvDate)
    TextView mTvDate;
    @BindView(R.id.rb1)
    RadioButton mRb1;
    @BindView(R.id.rb2)
    RadioButton mRb2;
    @BindView(R.id.rb3)
    RadioButton mRb3;
    @BindView(R.id.etNote)
    EditText mEtNote;
    @BindView(R.id.tvUpPhoto)
    TextView mTvUpPhoto;
    @BindView(R.id.btnEnter)
    Button mBtnEnter;
    @BindView(R.id.iv)
    ImageView mIv;

    private int flag, check_data_id;
    private String pic = "";//图片对应的Base64流
    private String picName = "";
    private String result, dateStr;
    private Date date;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refuse;
    }

    @Override
    protected void initData() {
        flag = getIntent().getExtras().getInt("flag");
        check_data_id = getIntent().getExtras().getInt("check_data_id");
        date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateStr = sdf.format(date);
        mTvDate.setText(dateStr);
        if (flag == 2) {
            mText.setText("到访不遇");
            mRb1.setText("用户不在家");
            mRb2.setText("已粘贴到访部单");
        } else if (flag == 3) {
            mText.setText("拒绝入户");
            mRb1.setText("用户个人原因");
            mRb2.setText("用户不愿安检");
            mRb3.setVisibility(View.VISIBLE);
            mRb3.setText("用户不愿开门");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    mTvUpPhoto.setVisibility(View.GONE);
                    mIv.setVisibility(View.VISIBLE);
                    List<LocalMedia> imageList = PictureSelector.obtainMultipleResult(data);
                    Glide.with(this).load(imageList.get(0).getPath()).into(mIv);
                    File file = new File(imageList.get(0).getPath());
                    pic = FileToBase64Util.fileToBase64(file);
                    picName = file.getName();
                    break;
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.tvUpPhoto, R.id.iv, R.id.btnEnter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv:
            case R.id.tvUpPhoto:
                //             启动相册并拍照
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .circleDimmedLayer(true)
                        .forResult(100);
                break;
            case R.id.btnEnter:
                if (mRb1.isChecked()) {
                    result = mRb1.getText().toString();
                } else if (mRb2.isChecked()) {
                    result = mRb2.getText().toString();
                } else if (mRb3.isChecked()) {
                    result = mRb3.getText().toString();
                }
                String remark = mEtNote.getText().toString().trim();
                commit(result, remark);
                break;
        }
    }

    public void commit(String result, String remark) {
        if (flag == 2) {
            Api.getDefault().miss(check_data_id, result, remark, dateStr, pic, picName)
                    .compose(RxHelper.<CheckRecord>handleResult())
                    .subscribe(new RxSubscriber<CheckRecord>(this) {
                        @Override
                        protected void _onNext(CheckRecord checkRecord) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("check_data_id", check_data_id);
                            startActivity(CheckResultActivity.class, bundle);
                        }

                        @Override
                        protected void _onError(String message) {
                            ToastUtil.showToast(RefuseActivity.this, message);
                        }
                    });
        } else if (flag == 3) {
            Api.getDefault().reject(check_data_id, result, remark, dateStr, pic, picName)
                    .compose(RxHelper.<CheckRecord>handleResult())
                    .subscribe(new RxSubscriber<CheckRecord>(this) {
                        @Override
                        protected void _onNext(CheckRecord checkRecord) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("check_data_id", check_data_id);
                            startActivity(CheckResultActivity.class, bundle);
                        }

                        @Override
                        protected void _onError(String message) {
                            ToastUtil.showToast(RefuseActivity.this, message);
                        }
                    });
        }
    }
}
