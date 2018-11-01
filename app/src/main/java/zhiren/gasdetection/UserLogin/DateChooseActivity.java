package zhiren.gasdetection.UserLogin;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;

public class DateChooseActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.tvRight)
    TextView mTvRight;
    @BindView(R.id.rbMonth)
    RadioButton mRbMonth;
    @BindView(R.id.rbDay)
    RadioButton mRbDay;
    @BindView(R.id.rg)
    RadioGroup mRg;
    @BindView(R.id.content)
    FrameLayout mContent;

    private DayChooseFragment mDayChooseFragment;
    private MonthChooseFragment mMonthChooseFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_date_choose;
    }

    @Override
    protected void initData() {
        setSelection(0);//默认选中按月选择
        mText.setText("请选择日期");
    }

    @Override
    protected void initListener() {
        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbMonth:
                        mRbMonth.setChecked(true);
                        setSelection(0);
                        break;
                    case R.id.rbDay:
                        mRbDay.setChecked(true);
                        setSelection(1);
                        break;
                }
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onBackClicked() {
        finish();
    }

    //    id=0代表按月选择，id=1按日选择
    public void setSelection(int id) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (id == 0) {
            if (mDayChooseFragment != null) {
                transaction.hide(mDayChooseFragment);
            }
            if (mMonthChooseFragment == null) {
                mMonthChooseFragment = new MonthChooseFragment();
                transaction.add(R.id.content, mMonthChooseFragment);
            } else {
                transaction.show(mMonthChooseFragment);
            }
        } else {
            if (mMonthChooseFragment != null) {
                transaction.hide(mMonthChooseFragment);
            }
            if (mDayChooseFragment == null) {
                mDayChooseFragment = new DayChooseFragment();
                transaction.add(R.id.content, mDayChooseFragment);
            } else {
                transaction.show(mDayChooseFragment);
            }
        }
        transaction.commit();
    }
}
