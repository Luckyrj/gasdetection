package zhiren.gasdetection.TrainingTest;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;

public class TrainingTestActivity extends BaseActivity {

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
    @BindView(R.id.content)
    FrameLayout mContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_training_test;
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void initListener(){
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
    }
}
