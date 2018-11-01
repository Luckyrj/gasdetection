package zhiren.gasdetection.InstallService;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;

public class InstallServiceActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.tvRight)
    TextView mTvRight;
    @BindView(R.id.rbMonth)
    RadioButton mRbMonth;
    @BindView(R.id.rbToday)
    RadioButton mRbToday;
    @BindView(R.id.rg)
    RadioGroup mRg;
    @BindView(R.id.list)
    ListView mList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_install_service;
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
