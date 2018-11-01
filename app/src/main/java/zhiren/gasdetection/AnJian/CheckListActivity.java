package zhiren.gasdetection.AnJian;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import model.CheckItems;
import retrofit.Api;
import retrofit.RxHelper;
import retrofit.RxSubscriber;
import utils.SPHelper;
import utils.ToastUtil;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;
import zhiren.gasdetection.adapter.CheckItemsAdapter;

// 安检项目列表页面
public class CheckListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.list)
    ListView mList;
    @BindView(R.id.btn)
    Button mBtn;

    private List<CheckItems.ProjectData> dataList = new ArrayList<>();
    private CheckItemsAdapter mItemsAdapter;
    private int posi;//列表点击的条目
    private String unqualified = "";//不合格条目id组成的字符串
    private int check_data_id;//安检记录id

    @Override
    protected int getLayoutId() {
        return R.layout.activity_check_list;
    }

    @Override
    protected void initData() {
        mText.setText("安检单列表");
        mItemsAdapter = new CheckItemsAdapter(CheckListActivity.this, dataList, R.layout.check_items_item);
        mList.setAdapter(mItemsAdapter);
        check_data_id = getIntent().getExtras().getInt("check_data_id");
        getList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            unqualified = data.getStringExtra("unqualified");
            if (unqualified.length() == 0) {
                dataList.get(posi).setResult("合格");
            } else {
                dataList.get(posi).setResult("不合格");
            }
            mItemsAdapter.notifyDataSetHasChanged();
        }
    }

    @Override
    protected void initListener() {
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posi = position;
                Intent intent = new Intent(CheckListActivity.this, CheckItemDetailActivity.class);
                int itemId = dataList.get(position).getId();
                String itemName = dataList.get(position).getName();
                String itemType = dataList.get(position).getType();
                Log.d("itemId", itemId + "");
                Log.d("itemId", itemType);
                intent.putExtra("itemId", itemId);
                intent.putExtra("check_data_id", check_data_id);
                intent.putExtra("itemName", itemName);
                intent.putExtra("itemType", itemType);
                startActivityForResult(intent, 0);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn:
//              有不合格项就跳转到风险评估页，否则跳转到第二步页面
                if (unqualified.length() > 0) {
                    startActivity(RiskRatingActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("check_data_id", check_data_id);
                    startActivity(PreachActivity.class, bundle);
                }
                break;
        }
    }

    public void getList() {
        int id = SPHelper.getInt(this, "id");
        Api.getDefault().checkProjectList(id)
                .compose(RxHelper.<CheckItems>handleResult())
                .subscribe(new RxSubscriber<CheckItems>(this) {
                    @Override
                    protected void _onNext(CheckItems checkItems) {
                        dataList.addAll(checkItems.getProject_data());
                        mItemsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(CheckListActivity.this, message);
                    }

                    @Override
                    protected boolean showDialog() {
                        return false;
                    }
                });
    }
}
