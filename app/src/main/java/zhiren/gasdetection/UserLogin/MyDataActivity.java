package zhiren.gasdetection.UserLogin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;

public class MyDataActivity extends BaseActivity {

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
    @BindView(R.id.tvDate)
    TextView mTvDate;
    @BindView(R.id.llDate)
    LinearLayout mLlDate;
    @BindView(R.id.tvAnJian)
    TextView mTvAnJian;
    @BindView(R.id.tvNormal)
    TextView mTvNormal;
    @BindView(R.id.tvRefuse)
    TextView mTvRefuse;
    @BindView(R.id.tvNoMeet)
    TextView mTvNoMeet;
    @BindView(R.id.tvFirst)
    TextView mTvFirst;
    @BindView(R.id.tvSecond)
    TextView mTvSecond;
    @BindView(R.id.tvThird)
    TextView mTvThird;
    @BindView(R.id.tvOne)
    TextView mTvOne;
    @BindView(R.id.tvTwo)
    TextView mTvTwo;
    @BindView(R.id.tvThree)
    TextView mTvThree;
    @BindView(R.id.tvZhengGai)
    TextView mTvZhengGai;
    @BindView(R.id.tvFinishReform)
    TextView mTvFinishReform;
    @BindView(R.id.tvAfterSale)
    TextView mTvAfterSale;
    @BindView(R.id.tvDianHuo)
    TextView mTvDianHuo;
    @BindView(R.id.tvFinishFire)
    TextView mTvFinishFire;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_data;
    }

    @Override
    protected void initData() {
        mText.setText("我的数据");
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText("日期筛选");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mRg.setVisibility(View.GONE);
            mLlDate.setVisibility(View.VISIBLE);
            String date = bundle.getString("date");
            if (date != null) {
                if (date.equals("选择月份")) {
                    mTvDate.setText("本月");
                } else {
                    mTvDate.setText(date);
                }
            } else {
                String beginDate = bundle.getString("beginDate");
                String endDate = bundle.getString("endDate");
                if (beginDate.equals("开始日期") && endDate.equals("结束日期")) {
                    mTvDate.setText("本月");
                } else {
                    if (beginDate.equals("开始日期")) {
                        mTvDate.setText(endDate);
                    } else if (endDate.equals("结束日期")) {
                        mTvDate.setText(beginDate);
                    } else if (!beginDate.equals("开始日期") && !endDate.equals("结束日期")) {
                        //              比较两个字符串的ASC码，小的显示在前
                        if (beginDate.compareTo(endDate) == 0) {
                            mTvDate.setText(beginDate);
                        } else if (beginDate.compareTo(endDate) < 0) {
                            mTvDate.setText(beginDate + " 至 " + endDate);
                        } else {
                            mTvDate.setText(endDate + " 至 " + beginDate);
                        }
                    }
                }
            }
        }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        startActivity(MainActivity.class);
    }

    @OnClick(R.id.tvRight)
    public void onChooseDate() {
        startActivity(DateChooseActivity.class);
    }
}
