package zhiren.gasdetection.TasksToDo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import model.LaborFeeData;
import retrofit.Api;
import retrofit.RxHelper;
import retrofit.RxSubscriber;
import utils.SPHelper;
import utils.ToastUtil;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;
import zhiren.gasdetection.adapter.FeeSelectAdapter;

//  添加费用列表页
public class AddFeeActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.btnAdd)
    Button mBtnAdd;

    private int page = 1;
    private int id;
    private int type;//区分添加费用类型
    private List<LaborFeeData.LaborData> mDataList = new ArrayList<>();
    private FeeSelectAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_fee;
    }

    @Override
    protected void initData() {
        id = SPHelper.getInt(this, "id");
        type = getIntent().getExtras().getInt("type", -1);
        if (type == 0) {
            mText.setText("添加人工服务费用");
        } else if (type == 1) {
            mText.setText("添加材料费用");
        } else {
            mText.setText("添加器具费用");
        }
        mAdapter = new FeeSelectAdapter(this, mDataList, R.layout.select_fee_item);
        mListView.setAdapter(mAdapter);
        getLaborFeeList(page, id);
    }

    @Override
    protected void initListener() {
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mSmartRefreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        getLaborFeeList(page, id);
                        mSmartRefreshLayout.finishLoadMore();
                    }
                }, 800);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.btnAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btnAdd:
                Intent intent = new Intent();
                intent.putExtra("data", mAdapter.getSelectData());
                Log.d("sizesize", mAdapter.getSelectData().size() + "");
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    public void getLaborFeeList(final int page, int id) {
        Api.getDefault().laborFeeList(page, id)
                .compose(RxHelper.<LaborFeeData>handleResult())
                .subscribe(new RxSubscriber<LaborFeeData>(this) {
                    @Override
                    protected void _onNext(LaborFeeData laborFeeData) {
                        int total = laborFeeData.getCount();
                        if (page * 10 >= total) {
                            mSmartRefreshLayout.setEnableLoadMore(false);
                        }
                        mDataList.addAll(laborFeeData.getLabor_data());
                        mAdapter.notifyDataSetHasChanged();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(AddFeeActivity.this, message);
                    }

                    @Override
                    protected boolean showDialog() {
                        return false;
                    }
                });
    }
}
