package zhiren.gasdetection.UserLogin;


import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import zhiren.gasdetection.BaseFragment;
import zhiren.gasdetection.R;

/**
 * 日期选择——按日选择
 */
public class DayChooseFragment extends BaseFragment {

    @BindView(R.id.rbBegin)
    RadioButton mRbBegin;
    @BindView(R.id.rbEnd)
    RadioButton mRbEnd;
    @BindView(R.id.rg)
    RadioGroup mRg;
    @BindView(R.id.ivDelete)
    ImageView mIvDelete;
    @BindView(R.id.wheelview_container)
    LinearLayout mWheelviewContainer;

    private DatePicker datePicker;//日期选择
    private static final String YEAR_MONTH_DAY_FORMAT = "%s-%s-%s";
    private int year, month, day;
    private boolean begin_date_flag = true;
    private boolean first = true;//初次切换到结束日期的标志

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_day_choose;
    }

    @Override
    protected void initData() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);//月份为实际月份减1
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d("yearmonth", "year:" + year + "month:" + month);
        datePicker = new DatePicker(getActivity(), DateTimePicker.YEAR_MONTH_DAY);
//      设置选项偏移量，可用来要设置显示的条目数，范围为1-5，1显示3行、2显示5行、3显示7行……
        datePicker.setOffset(2);
        datePicker.setUseWeight(true);
        datePicker.setLineSpaceMultiplier(4);
        datePicker.setRangeStart(2016, 1, 1);
        datePicker.setRangeEnd(year, month + 1, day);
        datePicker.setSelectedItem(year, month + 1, day);
        datePicker.setResetWhileWheel(false);
//      得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
        mWheelviewContainer.addView(datePicker.getContentView());
//      开始日期的默认值为当前日期
        mRbBegin.setText(String.format(YEAR_MONTH_DAY_FORMAT,
                datePicker.getSelectedYear(), datePicker.getSelectedMonth(), datePicker.getSelectedDay()));

        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbBegin:
                        mRbBegin.setChecked(true);
                        begin_date_flag = true;
                        break;
                    case R.id.rbEnd:
                        mRbEnd.setChecked(true);
                        begin_date_flag = false;
//                      初次切换到结束日期的时候显示开始日期
                        if (first) {
                            mRbEnd.setText(String.format(YEAR_MONTH_DAY_FORMAT,
                                    datePicker.getSelectedYear(), datePicker.getSelectedMonth(), datePicker.getSelectedDay()));
                            first = false;
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        datePicker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                if (begin_date_flag) {
                    mRbBegin.setText(String.format(YEAR_MONTH_DAY_FORMAT
                            , year, datePicker.getSelectedMonth(), datePicker.getSelectedDay()));
                } else {
                    mRbEnd.setText(String.format(YEAR_MONTH_DAY_FORMAT
                            , year, datePicker.getSelectedMonth(), datePicker.getSelectedDay()));
                }
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                if (begin_date_flag) {
                    mRbBegin.setText(String.format(YEAR_MONTH_DAY_FORMAT
                            , datePicker.getSelectedYear(), month, datePicker.getSelectedDay()));
                } else {
                    mRbEnd.setText(String.format(YEAR_MONTH_DAY_FORMAT
                            , datePicker.getSelectedYear(), month, datePicker.getSelectedDay()));
                }
            }

            @Override
            public void onDayWheeled(int index, String day) {
                if (begin_date_flag) {
                    mRbBegin.setText(String.format(YEAR_MONTH_DAY_FORMAT
                            , datePicker.getSelectedYear(), datePicker.getSelectedMonth(), day));
                } else {
                    mRbEnd.setText(String.format(YEAR_MONTH_DAY_FORMAT
                            , datePicker.getSelectedYear(), datePicker.getSelectedMonth(), day));
                }
            }
        });
    }

    @OnClick(R.id.ivDelete)
    public void onDeleteClicked() {
        mRbBegin.setText("开始日期");
        mRbEnd.setText("结束日期");
    }

    @OnClick(R.id.btnFinish)
    public void onFinishClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("beginDate", mRbBegin.getText().toString());
        bundle.putString("endDate", mRbEnd.getText().toString());
        startActivity(MyDataActivity.class, bundle);
        getActivity().finish();
    }
}
