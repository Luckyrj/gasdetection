package zhiren.gasdetection.TasksToDo;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import model.LaborFeeData;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;
import zhiren.gasdetection.adapter.FeeSelectAdapter;

//  三种费用页面
public class FeeListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.tvRight)
    TextView mTvRight;
    @BindView(R.id.ivAddMaterial)
    ImageView mIvAddMaterial;
    @BindView(R.id.recyclerMaterial)
    RecyclerView mRecyclerMaterial;
    @BindView(R.id.llMaterial)
    LinearLayout mLlMaterial;
    @BindView(R.id.ivAddGoods)
    ImageView mIvAddGoods;
    @BindView(R.id.recyclerGoods)
    RecyclerView mRecyclerGoods;
    @BindView(R.id.llTool)
    LinearLayout mLlTool;
    @BindView(R.id.ivAddService)
    ImageView mIvAddService;
    @BindView(R.id.recyclerService)
    RecyclerView mRecyclerService;
    @BindView(R.id.llService)
    LinearLayout mLlService;
    @BindView(R.id.tvMoney)
    TextView mTvMoney;
    @BindView(R.id.tvNext)
    TextView mTvNext;
    @BindView(R.id.tvSelect)
    TextView mTvSelect;
    @BindView(R.id.tvDelete)
    TextView mTvDelete;

    private List<LaborFeeData.LaborData> dataList = new ArrayList<>();
    private FeeSelectAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_purchase_list;
    }

    @Override
    protected void initData() {
        mText.setText("费用列表");
        mRecyclerService.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FeeSelectAdapter(this, dataList, R.layout.purchase_item);
        mRecyclerService.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    dataList.addAll((List<LaborFeeData.LaborData>) data.getSerializableExtra("data"));
                    mAdapter.notifyDataSetHasChanged();
                    break;
                case 1:
                    break;
                case 2:
                    break;
            }
        }
    }

    @OnClick({R.id.iv_back, R.id.tvRight, R.id.ivAddMaterial, R.id.ivAddGoods, R.id.ivAddService, R.id.tvNext, R.id.tvSelect, R.id.tvDelete})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, AddFeeActivity.class);
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tvRight:
                break;
            case R.id.ivAddMaterial:
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1);
                break;
            case R.id.ivAddGoods:
                intent.putExtra("type", 2);
                startActivityForResult(intent, 2);
                break;
            case R.id.ivAddService:
                intent.putExtra("type", 0);
                startActivityForResult(intent, 0);
                break;
            case R.id.tvNext:
                break;
            case R.id.tvSelect:
                break;
            case R.id.tvDelete:
                break;
        }
    }

}
