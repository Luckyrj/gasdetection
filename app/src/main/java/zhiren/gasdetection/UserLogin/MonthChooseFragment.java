package zhiren.gasdetection.UserLogin;


import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import zhiren.gasdetection.BaseFragment;
import zhiren.gasdetection.R;

/**
 * 日期选择——按月选择
 */
public class MonthChooseFragment extends BaseFragment {
    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.ivDelete)
    ImageView mIvDelete;
    @BindView(R.id.wheelview_container)
    LinearLayout mWheelviewContainer;
    @BindView(R.id.btnFinish)
    Button mBtnFinish;

    private DatePicker datePicker;//日期选择
    private static final String YEAR_MONTH_FORMAT = "%s-%s";
    private int year, month;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_month_choose;
    }

    @Override
    protected void initData() {
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH);//月份为实际月份减1
        Log.d("yearmonth", "year:" + year + "month:" + month);
        datePicker = new DatePicker(getActivity(), DateTimePicker.YEAR_MONTH);
//      设置选项偏移量，可用来要设置显示的条目数，范围为1-5，1显示3行、2显示5行、3显示7行……
        datePicker.setOffset(2);
        datePicker.setUseWeight(true);
        datePicker.setLineSpaceMultiplier(4);
        datePicker.setRangeStart(2016, 1);
        datePicker.setRangeEnd(year, month + 1);
        datePicker.setSelectedItem(year, month + 1);
        datePicker.setResetWhileWheel(false);
        //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
        mWheelviewContainer.addView(datePicker.getContentView());
        mTv.setText(String.format(YEAR_MONTH_FORMAT, datePicker.getSelectedYear(), datePicker.getSelectedMonth()));
        mTv.setTextColor(getResources().getColor(R.color.color_blue));

        datePicker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                mTv.setText(String.format(YEAR_MONTH_FORMAT
                        , year, datePicker.getSelectedMonth()));
                mTv.setTextColor(getResources().getColor(R.color.color_blue));
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                mTv.setText(String.format(YEAR_MONTH_FORMAT
                        , datePicker.getSelectedYear(), month));
                mTv.setTextColor(getResources().getColor(R.color.color_blue));
            }

            @Override
            public void onDayWheeled(int index, String day) {
            }
        });
    }

    @OnClick(R.id.ivDelete)
    public void onDeleteClicked() {
        mTv.setText("选择月份");
        mTv.setTextColor(getResources().getColor(R.color.color_light_black));
    }

    @OnClick(R.id.btnFinish)
    public void onFinishClicked() {
        Bundle bundle = new Bundle();
        bundle.putString("date", mTv.getText().toString());
        startActivity(MyDataActivity.class, bundle);
        getActivity().finish();
    }
}
