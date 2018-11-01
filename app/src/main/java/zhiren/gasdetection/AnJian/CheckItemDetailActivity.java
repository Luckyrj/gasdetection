package zhiren.gasdetection.AnJian;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;
import model.CheckItemsDetail;
import model.CheckRecord;
import retrofit.Api;
import retrofit.RxHelper;
import retrofit.RxSubscriber;
import utils.FileToBase64Util;
import utils.SPHelper;
import utils.ToastUtil;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;
import zhiren.gasdetection.TasksToDo.CategoryActivity;
import zhiren.gasdetection.adapter.CheckDetailAdapter;
import zhiren.gasdetection.adapter.GridImageAdapter;

//  安检检查项详情页
public class CheckItemDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.tvBrand)
    TextView mTvBrand;
    @BindView(R.id.tvVersion)
    TextView mTvVersion;
    @BindView(R.id.tvYear)
    TextView mTvYear;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.etNote)
    EditText mEtNote;
    @BindView(R.id.tvUpVedio)
    TextView mTvUpVedio;
    @BindView(R.id.tvUpPhoto)
    TextView mTvUpPhoto;
    @BindView(R.id.btnEnter)
    Button mBtnEnter;
    @BindView(R.id.etWidth)
    EditText mEtWidth;
    @BindView(R.id.etHeight)
    EditText mEtHeight;
    @BindView(R.id.tvType)
    TextView mTvType;
    @BindView(R.id.rb1)
    RadioButton mRb1;
    @BindView(R.id.rb2)
    RadioButton mRb2;
    @BindView(R.id.rb3)
    RadioButton mRb3;
    @BindView(R.id.llType)
    LinearLayout mLlType;
    @BindView(R.id.recyclerImages)
    RecyclerView recyclerImages;
    @BindView(R.id.recyclerVedio)
    RecyclerView recyclerVedio;

    private int id, itemId, check_data_id; //登录用户id、安检条目ID、安检记录ID
    private String type;//器具类别
    private List<LocalMedia> imageList = new ArrayList<>();// 图片列表
    private List<LocalMedia> vedioList = new ArrayList<>();// 视频列表
    private GridImageAdapter imageAdapter, vedioAdapter;
    private CheckDetailAdapter mCheckAdapter;
    private String brand = "";//器具品牌
    private String spec = "";//器具型号
    private float year = 0;//使用年限

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_detail;
    }

    @Override
    protected void initData() {
        id = SPHelper.getInt(this, "id");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        String name = intent.getStringExtra("itemName");
        type = intent.getStringExtra("itemType");
        itemId = intent.getIntExtra("itemId",0);
        check_data_id = intent.getIntExtra("check_data_id",0);
        mText.setText(name);
//       默认燃气灶有两类，燃气表三类，其他种器具只有一类
        switch (type) {
            case "燃气灶":
                mLlType.setVisibility(View.VISIBLE);
                mRb1.setText("燃气灶");
                mRb2.setText("集成灶");
                break;
            case "燃气表":
                mLlType.setVisibility(View.VISIBLE);
                mRb3.setVisibility(View.VISIBLE);
                mRb1.setText("机械表");
                mRb2.setText("IC卡表");
                mRb3.setText("远程表");
                break;
            case "热水器":
            case "采暖炉":
                break;
        }
        getList(itemId);
        initImageVedioList();
    }


    public void initImageVedioList() {
//      图片使用的适配器
        imageAdapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
//              启动相册并拍照
                PictureSelector.create(CheckItemDetailActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(3)
                        .circleDimmedLayer(true)
                        .forResult(100);
            }
        });
//      视频使用的适配器
        vedioAdapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
//              启动相册并拍照
                PictureSelector.create(CheckItemDetailActivity.this)
                        .openGallery(PictureMimeType.ofVideo())
                        .maxSelectNum(1)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
        imageAdapter.setList(imageList);
        imageAdapter.setSelectMax(3);
        recyclerImages.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerImages.setAdapter(imageAdapter);

        vedioAdapter.setList(vedioList);
        vedioAdapter.setSelectMax(1);
        recyclerVedio.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerVedio.setAdapter(vedioAdapter);

        imageAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // 预览图片 可自定长按保存路径
                //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                PictureSelector.create(CheckItemDetailActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, imageList);
            }
        });
        vedioAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // 预览视频
                PictureSelector.create(CheckItemDetailActivity.this).externalPictureVideo(vedioList.get(0).getPath());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
//               视频回调
                case PictureConfig.CHOOSE_REQUEST:
                    mTvUpVedio.setVisibility(View.GONE);
                    recyclerVedio.setVisibility(View.VISIBLE);
                    vedioList = PictureSelector.obtainMultipleResult(data);
                    vedioAdapter.setList(vedioList);
                    vedioAdapter.notifyDataSetChanged();
                    break;
//               图片回调
                case 100:
                    mTvUpPhoto.setVisibility(View.GONE);
                    recyclerImages.setVisibility(View.VISIBLE);
                    imageList = PictureSelector.obtainMultipleResult(data);
                    imageAdapter.setList(imageList);
                    imageAdapter.notifyDataSetChanged();
                    break;
//               选择品牌型号回调
                case 0:
                    brand = data.getStringExtra("brand");
                    spec = data.getStringExtra("spec");
                    mTvBrand.setText(brand);
                    mTvVersion.setText(spec);
                    break;
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.tvUpVedio, R.id.tvUpPhoto,
            R.id.btnEnter, R.id.tvBrand, R.id.tvVersion, R.id.tvYear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tvUpVedio:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofVideo())
                        .selectionMode(PictureConfig.SINGLE)
                        .videoMaxSecond(60)
                        .forResult(PictureConfig.CHOOSE_REQUEST)
                ;
                break;
            case R.id.tvUpPhoto:
//              启动相册并拍照
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .maxSelectNum(3)
                        .circleDimmedLayer(true)
                        .forResult(100);
                break;
            case R.id.btnEnter:
                String size = mEtWidth.getText().toString() + "*" + mEtHeight.getText().toString();
                String remark = mEtNote.getText().toString();
                String unqualified = mCheckAdapter.getUnqualified();
                String vedio = "";
                String vedio_name = "";
                String picture1 = "";
                String picture1_name = "";
                String picture2 = "";
                String picture2_name = "";
                String picture3 = "";
                String picture3_name = "";
                if (vedioList.size() > 0) {
                    File vedioFile = new File(vedioList.get(0).getPath());
                    vedio = FileToBase64Util.fileToBase64(vedioFile);
                    vedio_name = vedioFile.getName();
                }
                if (imageList.size() == 1) {
                    File imageFile1 = new File(imageList.get(0).getPath());
                    picture1 = FileToBase64Util.fileToBase64(imageFile1);
                    picture1_name = imageFile1.getName();
                } else if (imageList.size() == 2) {
                    File imageFile1 = new File(imageList.get(0).getPath());
                    File imageFile2 = new File(imageList.get(1).getPath());
                    picture1 = FileToBase64Util.fileToBase64(imageFile1);
                    picture1_name = imageFile1.getName();
                    picture2 = FileToBase64Util.fileToBase64(imageFile2);
                    picture2_name = imageFile2.getName();
                } else if (imageList.size() == 3) {
                    File imageFile1 = new File(imageList.get(0).getPath());
                    File imageFile2 = new File(imageList.get(1).getPath());
                    File imageFile3 = new File(imageList.get(2).getPath());
                    picture1 = FileToBase64Util.fileToBase64(imageFile1);
                    picture1_name = imageFile1.getName();
                    picture2 = FileToBase64Util.fileToBase64(imageFile2);
                    picture2_name = imageFile2.getName();
                    picture3 = FileToBase64Util.fileToBase64(imageFile3);
                    picture3_name = imageFile3.getName();
                }
                Log.d("unqualified12", "size:" + size);
                Log.d("unqualified12", "remark:" + remark);
                Log.d("unqualified12", "unqualified:" + unqualified);
                Log.d("unqualified12", "vedio:" + vedio);
                Log.d("unqualified12", "vedio_name:" + vedio_name);
                Log.d("unqualified12", "picture1:" + picture1);
                Log.d("unqualified12", "picture1_name:" + picture1_name);
                Log.d("unqualified12", "picture2:" + picture2);
                Log.d("unqualified12", "picture2_name:" + picture2_name);
                Log.d("unqualified12", "picture3:" + picture3);
                Log.d("unqualified12", "picture3_name:" + picture3_name);
                checkCommit(size, unqualified, remark, vedio, vedio_name, picture1, picture1_name, picture2, picture2_name, picture3, picture3_name);
                break;
            case R.id.tvVersion:
            case R.id.tvBrand:
                Intent intent = new Intent(this, CategoryActivity.class);
                intent.putExtra("flag", 1);
                intent.putExtra("type", type);
                String type1 = "";//子类型
                if (mRb1.isChecked()) {
                    type1 = mRb1.getText().toString();
                } else if (mRb2.isChecked()) {
                    type1 = mRb2.getText().toString();
                } else if (mRb3.isChecked()) {
                    type1 = mRb3.getText().toString();
                }
                intent.putExtra("type1", type1);
                startActivityForResult(intent, 0);
                break;
            case R.id.tvYear:
                final String[] strings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
                View yearView = LayoutInflater.from(this).inflate(R.layout.bottom_year_dialog, null);
                WheelView wheelView = yearView.findViewById(R.id.wheelView);
                Button btnEnter = yearView.findViewById(R.id.btnEnter);
                btnEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                wheelView.setItems(strings, 0);
                wheelView.setTextColor(getResources().getColor(R.color.color_light_black));
                wheelView.setTextSize(18);
                WheelView.DividerConfig config = new WheelView.DividerConfig();
                config.setRatio(1.0f / 10.0f);//线比率
                config.setColor(getResources().getColor(R.color.color_blue));//线颜色
                config.setAlpha(100);//线透明度
                config.setThick(ConvertUtils.toPx(this, 5));//线粗
                wheelView.setDividerConfig(config);
                wheelView.setOnItemSelectListener(new WheelView.OnItemSelectListener() {
                    @Override
                    public void onSelected(int index) {
                        year = Float.parseFloat(strings[index]);
                        Log.d("year", year + "");
                        mTvYear.setText(String.format("%s 年", strings[index]));
                    }
                });
                bottomSheetDialog.setContentView(yearView);
                bottomSheetDialog.show();
                break;
        }
    }

    public void getList(int itemId) {
        Api.getDefault().checkProjectItemList(id, itemId)
                .compose(RxHelper.<CheckItemsDetail>handleResult())
                .subscribe(new RxSubscriber<CheckItemsDetail>(this) {
                    @Override
                    protected void _onNext(CheckItemsDetail checkItemsDetail) {
                        mCheckAdapter = new CheckDetailAdapter(CheckItemDetailActivity.this,
                                checkItemsDetail.getProject_data(), R.layout.check_item);
                        mRecyclerView.setAdapter(mCheckAdapter);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(CheckItemDetailActivity.this, message);
                    }

                    @Override
                    protected boolean showDialog() {
                        return false;
                    }
                });
    }

    public void checkCommit(String size, final String unqualified, String remark, String video, String video_name, String picture1, String picture1_name, String picture2, String picture2_name, String picture3, String picture3_name) {
        Log.d("unqualified12", "check_data_id:" + check_data_id);
        Log.d("unqualified12", "itemId:" + itemId);
        Log.d("unqualified12", "brand:" + brand);
        Log.d("unqualified12", "spec:" + spec);
        Log.d("unqualified12", "year:" + year);
        Api.getDefault().checkItemCommit(check_data_id, itemId, brand, spec, year, size, unqualified, remark, video, video_name, picture1, picture1_name, picture2, picture2_name, picture3, picture3_name)
                .compose(RxHelper.<CheckRecord>handleResult())
                .subscribe(new RxSubscriber<CheckRecord>(this) {
                    @Override
                    protected void _onNext(CheckRecord checkRecord) {
//                        Bundle bundle=new Bundle();
//                        bundle.putInt("check_data_id",check_data_id);
//                        bundle.putString("unqualified",unqualified);
//                        bundle.putBoolean("flag",true);
//                        startActivity(CheckListActivity.class,bundle);
                        Intent intent = new Intent();
                        intent.putExtra("unqualified", unqualified);
                        setResult(RESULT_OK, intent);
                        CheckItemDetailActivity.this.finish();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(CheckItemDetailActivity.this, message);
                    }
                });
    }

}
