package zhiren.gasdetection.UserLogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import model.SystemUser;
import utils.ACache;
import utils.LOG;
import utils.UrlHelper;
import utils.glideUtils.GlideImageLoader;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.InstallService.InstallDetailActivity;
import zhiren.gasdetection.R;
import zhiren.gasdetection.TasksToDo.CategoryActivity;
import zhiren.gasdetection.TasksToDo.CheckTaskListActivity;
import zhiren.gasdetection.TasksToDo.VentilateTaskListActivity;
import zhiren.gasdetection.TrainingTest.TrainingTestActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.iv_data)
    ImageView mIvData;
    @BindView(R.id.iv_photo)
    CircleImageView mIvPhoto;
    @BindView(R.id.tvChangePW)
    TextView mTvChangePW;
    @BindView(R.id.tvName)
    TextView mTvName;
    @BindView(R.id.ivSex)
    ImageView mIvSex;
    @BindView(R.id.tvPhone)
    TextView mTvPhone;
    @BindView(R.id.tvNum)
    TextView mTvNum;
    @BindView(R.id.tvCompany)
    TextView mTvCompany;
    @BindView(R.id.tvIntro)
    TextView mTvIntro;
    @BindView(R.id.llAnJian)
    LinearLayout mLlAnJian;
    @BindView(R.id.llDianHuo)
    LinearLayout mLlDianHuo;
    @BindView(R.id.llYinCang)
    LinearLayout mLlYinCang;
    @BindView(R.id.tvTest)
    TextView mTvTest;
    @BindView(R.id.tvGetMaterial)
    TextView mTvGetMaterial;
    @BindView(R.id.tvAddGuest)
    TextView mTvAddGuest;
    @BindView(R.id.tvCheck)
    TextView mTvCheck;
    @BindView(R.id.tvReform)
    TextView mTvReform;
    @BindView(R.id.tvFire)
    TextView mTvFire;
    @BindView(R.id.tvInstall)
    TextView mTvInstall;
    @BindView(R.id.tvInspect)
    TextView mTvInspect;
    @BindView(R.id.tvCommunity)
    TextView mTvCommunity;
    @BindView(R.id.tv_check_count)
    TextView mTvCheckCount;
    @BindView(R.id.tv_ventilate_count)
    TextView mTvVentilateCount;
    @BindView(R.id.tv_change_count)
    TextView mTvChangeCount;

    private String tel;// 手机号作为账号
    private int id;// 用户id

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mTvCheck.setText("户内燃气\n安全检查");
        mTvReform.setText("户内隐患\n整改");
        mTvFire.setText("点火通气");
        mTvInstall.setText("器具安装\n服务");
        mTvInspect.setText("巡线巡检");
        mTvCommunity.setText("微社区");
        SystemUser user = (SystemUser) ACache.get(this).getAsObject("user");
        id = user.getId();
        mTvName.setText(user.getRealname());
        mTvPhone.setText(user.getTel());
        mTvNum.setText(user.getStaffno());
        mTvCompany.setText(user.getCompany());
        mTvIntro.setText(user.getIntroduce());
        mTvCheckCount.setText(user.getCheck_counts());
        mTvVentilateCount.setText(user.getVentilate_counts());
        mTvChangeCount.setText(user.getChange_counts());

        if (user.getSex().equals("男")) {
            mIvSex.setImageResource(R.mipmap.male_icon);
        } else {
            mIvSex.setImageResource(R.mipmap.female_icon);
        }
        String path = UrlHelper.URL_IP + user.getImg();
        GlideImageLoader.getInstance().loadImage(this, path, mIvPhoto);


    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList.size() > 0) {
                        LOG.e(selectList.get(0).getPath());
                        GlideImageLoader.getInstance().loadImage(this, selectList.get(0).getPath(), mIvPhoto);
                    }
                    break;
            }
        }
    }

    @OnClick({R.id.iv_data, R.id.tvChangePW, R.id.llAnJian, R.id.llDianHuo,
            R.id.tvCheck, R.id.tvReform, R.id.tvFire, R.id.tvInstall, R.id.iv_photo,
            R.id.llYinCang, R.id.tvTest, R.id.tvGetMaterial, R.id.tvAddGuest})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_data:
                startActivity(MyDataActivity.class);
                break;
            case R.id.iv_photo:
//              启动相册并拍照
                PictureSelector.create(MainActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .selectionMode(PictureConfig.SINGLE)
                        .circleDimmedLayer(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.tvChangePW:
                bundle.putString("tel", tel);
                startActivity(ChangePWActivity.class, bundle);
                break;
            case R.id.llAnJian:
                bundle.putInt("id", id);
                bundle.putBoolean("token", true);
                bundle.putString("status", "待安检");
                startActivity(CheckTaskListActivity.class, bundle);
                break;
            case R.id.llDianHuo:
                bundle.putInt("id", id);
                bundle.putBoolean("token", true);
                startActivity(VentilateTaskListActivity.class, bundle);
                break;
            case R.id.llYinCang:
                break;
            case R.id.tvGetMaterial:
                break;
            case R.id.tvAddGuest:
                startActivity(CustomerActivity.class);
                break;
            case R.id.tvTest:
                startActivity(TrainingTestActivity.class);
                break;
            case R.id.tvCheck:
                bundle.putInt("id", id);
                startActivity(CategoryActivity.class, bundle);
                break;
            case R.id.tvReform:
//                startActivity(ClientSignatureActivity.class);
                break;
            case R.id.tvFire:
                bundle.putInt("id", id);
                startActivity(CategoryActivity.class, bundle);
                break;
            case R.id.tvInstall:
                startActivity(InstallDetailActivity.class);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
