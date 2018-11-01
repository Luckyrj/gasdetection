package zhiren.gasdetection.UserLogin;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.util.LogUtils;
import utils.ToastUtil;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;

public class CustomerActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.etNo)
    EditText mEtNo;
    @BindView(R.id.etName)
    EditText mEtName;
    @BindView(R.id.etPhone)
    EditText mEtPhone;
    @BindView(R.id.tvAddress)
    TextView mTvAddress;
    @BindView(R.id.etRoom)
    EditText mEtRoom;
    @BindView(R.id.btnEnter)
    Button mBtnEnter;

    private AddressPicker picker;//地址选择器
    private String provinceStr, cityStr, countyStr;//省市县

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_customer;
    }

    @Override
    protected void initData() {
        mText.setText("新增客户");
        initPickAddress();
    }

    @OnClick({R.id.iv_back, R.id.tvAddress, R.id.btnEnter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tvAddress:
                picker.show();
                break;
            case R.id.btnEnter:
                break;
        }
    }

    public void initPickAddress() {
        try {
            ArrayList<Province> data = new ArrayList<>();
            String json = ConvertUtils.toString(getAssets().open("city.json"));
            data.addAll(JSON.parseArray(json, Province.class));
            picker = new AddressPicker(this, data);
//          首次默认地址
            picker.setSelectedItem("湖北", "武汉", "江岸");
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    provinceStr = province.getName();
                    cityStr = city.getName();
                    countyStr = county.getName();
                    Log.d("provinceStrprovinceStr", provinceStr);
                    mTvAddress.setText(provinceStr + " " + cityStr + " " + countyStr);
                }
            });
        } catch (Exception e) {
            ToastUtil.showToast(this, LogUtils.toStackTraceString(e));
        }
    }
}
