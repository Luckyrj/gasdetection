package zhiren.gasdetection.AnJian;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.byteam.superadapter.IMulItemViewType;
import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import model.HistoricalData;
import retrofit.Api;
import retrofit.RxHelper;
import retrofit.RxSubscriber;
import utils.SPHelper;
import utils.ToastUtil;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;

public class HistoryDataActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.listView)
    ListView listView;

    private int customer_id, id;
    private List<HistoricalData.HistoryData> mDataList = new ArrayList<>();
    private HistoryDataAdapter mAdapter;
    private int page = 1;
    private int count;//数据总条数

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history_data;
    }

    @Override
    protected void initData() {
        mText.setText("客户历史数据");
        customer_id = getIntent().getExtras().getInt("customer_id");
        id = SPHelper.getInt(this, "id");
        Log.d("pagepage", customer_id + "");
        mAdapter = new HistoryDataAdapter(this, mDataList, new IMulItemViewType<HistoricalData.HistoryData>() {
            @Override
            public int getViewTypeCount() {
                return 2;
            }

            @Override
            public int getItemViewType(int position, HistoricalData.HistoryData historyData) {
//               在有多页情况下，最后一条数据布局显示加载更多
                if (position == mDataList.size() - 1 && count > mDataList.size()) {
                    return 0;
                } else {
                    return 1;
                }
            }

            @Override
            public int getLayoutId(int viewType) {
                if (viewType == 0) {
                    return R.layout.history_more_item;
                } else {
                    return R.layout.history_item;
                }
            }
        });
        listView.setAdapter(mAdapter);
        getList(page);
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("check_data_id", mDataList.get(position).getId());
                Log.d("pagepage", "data_id: " + mDataList.get(position).getId());
                startActivity(CheckResultActivity.class, bundle);
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    public void getList(final int page) {
        Api.getDefault().checkHistory(page, id, customer_id)
                .compose(RxHelper.<HistoricalData>handleResult())
                .subscribe(new RxSubscriber<HistoricalData>(this) {
                    @Override
                    protected void _onNext(HistoricalData historicalData) {
                        count = historicalData.getCount();
                        mDataList.addAll(historicalData.getHistory_data());
                        mAdapter.notifyDataSetHasChanged();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(HistoryDataActivity.this, message);
                    }

                    @Override
                    protected boolean showDialog() {
                        return false;
                    }
                });
    }

    /**
     * Author: andy
     * Time:2018/10/10 0010
     * Description:历史数据使用的适配器
     */
    public class HistoryDataAdapter extends SuperAdapter<HistoricalData.HistoryData> {

        public HistoryDataAdapter(Context context, List<HistoricalData.HistoryData> items, IMulItemViewType<HistoricalData.HistoryData> mulItemViewType) {
            super(context, items, mulItemViewType);
        }

        @Override
        public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, HistoricalData.HistoryData item) {
            holder.setText(R.id.tvDate, item.getAddtime_show());
            holder.setText(R.id.tvResult, item.getStatus());
            TextView textView = holder.findViewById(R.id.tvResult);
            textView.setTextColor(textView.getText().toString().equals("合格")
                    ? textView.getContext().getResources().getColor(R.color.color_green)
                    : textView.getContext().getResources().getColor(R.color.color_red));
            if (viewType == 0) {
                TextView tvMore = holder.findViewById(R.id.tvMore);
                tvMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        page++;
                        HistoryDataActivity.this.getList(page);
                    }
                });
            }
        }
    }
}
