package zhiren.gasdetection.TasksToDo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import model.CheckTask;
import retrofit.Api;
import retrofit.RxHelper;
import retrofit.RxSubscriber;
import utils.ToastUtil;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;
import zhiren.gasdetection.adapter.TaskAdapter;

// 安检任务列表页
public class VentilateTaskListActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.etSearch)
    EditText mEtSearch;
    @BindView(R.id.listView)
    ListView mListView;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.tvNoFound)
    TextView mTvNoFound;

    private int id;//用户ID
    private boolean token;//true显示当前用户数据，false显示所有数据
    private int page = 1;
    private String leftStr, rightStr, status;
    private int total;//列表总条数
    private String key;//查找内容
    private List<CheckTask.TaskDataBean> dataList = new ArrayList<>();
    private TaskAdapter mTaskAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_list;
    }

    @Override
    protected void initData() {
        mText.setVisibility(View.GONE);
        mEtSearch.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        token = bundle.getBoolean("token");
        leftStr = bundle.getString("left", "");
        rightStr = bundle.getString("right", "");
        status = bundle.getString("status", "");
        getList(page, "");
    }

    @Override
    protected void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("CheckTask", dataList.get(position));
                startActivity(TaskDetailActivity.class, bundle);
            }
        });
        mEtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    hideSoftInput();
                    page = 1;
                    key = mEtSearch.getText().toString();
                    getList(page, key);
                    return true;
                }
                return false;
            }
        });
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mSmartRefreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page = 1;
                        key = mEtSearch.getText().toString();
                        getList(page, key);
                        mSmartRefreshLayout.finishRefresh();
                    }
                }, 800);
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mSmartRefreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        key = mEtSearch.getText().toString();
                        getList(page, key);
                        mSmartRefreshLayout.finishLoadMore();
                    }
                }, 800);
            }
        });
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

    public void getList(final int page, String key) {
        Api.getDefault().ventilateTaskList(page, id, token, leftStr, rightStr, key, status)
                .compose(RxHelper.<CheckTask>handleResult())
                .subscribe(new RxSubscriber<CheckTask>(this) {
                    @Override
                    protected void _onNext(CheckTask checkTask) {
                        if (checkTask.getCount() == 0) {
                            mTvNoFound.setVisibility(View.VISIBLE);
                            mSmartRefreshLayout.setVisibility(View.GONE);
                        } else {
                            mTvNoFound.setVisibility(View.GONE);
                            mSmartRefreshLayout.setVisibility(View.VISIBLE);
                            Log.d("totaltotal", "page " + page);
                            total = checkTask.getCount();
                            if (total <= 10 * page) {
                                mSmartRefreshLayout.setEnableLoadMore(false);
                                if (page > 1) {
                                    ToastUtil.showToast(VentilateTaskListActivity.this, "数据加载完成");
                                }
                            } else {
                                mSmartRefreshLayout.setEnableLoadMore(true);
                            }
                            if (page == 1) {
                                dataList = checkTask.getTask_data();
                                mTaskAdapter = new TaskAdapter(VentilateTaskListActivity.this, dataList, R.layout.task_list_item);
                                mListView.setAdapter(mTaskAdapter);
                            } else {
                                dataList.addAll(checkTask.getTask_data());
                                mTaskAdapter.notifyDataSetHasChanged();
                            }
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(VentilateTaskListActivity.this, message);
                    }

                    @Override
                    protected boolean showDialog() {
                        return false;
                    }
                });
    }
}
