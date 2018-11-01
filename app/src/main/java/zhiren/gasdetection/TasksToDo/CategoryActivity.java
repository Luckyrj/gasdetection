package zhiren.gasdetection.TasksToDo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import model.BrandSpec;
import model.Street;
import retrofit.Api;
import retrofit.RxHelper;
import retrofit.RxSubscriber;
import utils.SPHelper;
import utils.ToastUtil;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;
import zhiren.gasdetection.adapter.LeftMenuAdapter;
import zhiren.gasdetection.adapter.RightMenuAdapter;

// 二级分类筛选页面
public class CategoryActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.lvLeft)
    ListView mLvLeft;
    @BindView(R.id.lvRight)
    ListView mLvRight;
    @BindView(R.id.etSearch)
    EditText mEtSearch;
    @BindView(R.id.textLeft)
    TextView mTvLeft;
    @BindView(R.id.textRight)
    TextView mTvRight;
    @BindView(R.id.llContainer)
    LinearLayout mLlContainer;
    @BindView(R.id.tvNoFound)
    TextView mTvNoFound;

    private LeftMenuAdapter leftMenuAdapter;
    private RightMenuAdapter rightMenuAdapter;
    private ArrayList<String> leftList = new ArrayList<>();
    private ArrayList<String> rightList = new ArrayList<>();
    private Map<String, List<String>> map = new HashMap<>();//存储街道与小区的对应关系
    private int Id;//登录用户ID
    private int flag;//区分从哪个页面到分类筛选页的标志

    @Override
    protected int getLayoutId() {
        return R.layout.activity_category;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Id = SPHelper.getInt(this, "id");
        flag = intent.getIntExtra("flag", 0);
        if (flag == 1) {
            mText.setText("选择品牌型号");
            mTvLeft.setText("全部品牌");
            mTvRight.setText("全部型号");
            String type = intent.getStringExtra("type");
            String type1 = intent.getStringExtra("type1");
            getBrandSpec(type, type1);
        } else {
            mText.setVisibility(View.GONE);
            mEtSearch.setVisibility(View.VISIBLE);
            getStreetAndArea(Id);
        }
    }

    @Override
    protected void initListener() {
        mLvLeft.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                leftMenuAdapter.setSelectItem(position);
                leftMenuAdapter.notifyDataSetInvalidated();
                rightList.clear();
                rightList.addAll(map.get(leftList.get(position)));
                rightMenuAdapter.setSelectItem(0);
                rightMenuAdapter.notifyDataSetInvalidated();
            }
        });
        mLvRight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rightMenuAdapter.setSelectItem(position);
                rightMenuAdapter.notifyDataSetInvalidated();
                if (flag == 1) {
                    Intent intent = new Intent();
                    intent.putExtra("brand", leftList.get(leftMenuAdapter.getSelectItem()));
                    intent.putExtra("spec", rightList.get(rightMenuAdapter.getSelectItem()));
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", Id);
                    bundle.putBoolean("token", false);
                    bundle.putString("left", leftList.get(leftMenuAdapter.getSelectItem()));
                    bundle.putString("right", rightList.get(rightMenuAdapter.getSelectItem()));
                    Log.d("CategoryActivity", leftList.get(leftMenuAdapter.getSelectItem()));
                    Log.d("CategoryActivity", rightList.get(rightMenuAdapter.getSelectItem()));
                    startActivity(CheckTaskListActivity.class, bundle);
                }
            }
        });
    }

    public void getStreetAndArea(int id) {
        Api.getDefault().getStreetAndArea(id, false)
                .compose(RxHelper.<Street>handleResult())
                .subscribe(new RxSubscriber<Street>(this) {
                    @Override
                    protected void _onNext(Street street) {
                        for (Street.StreetBean streetBean : street.getStreet()) {
                            String streetStr = streetBean.getStreet();
                            leftList.add(streetStr);
                            List<Street.StreetBean.AreaBean> areaBeans = streetBean.getArea();
                            List<String> areaList = new ArrayList<>();
                            for (Street.StreetBean.AreaBean areaBean : areaBeans) {
                                areaList.add(areaBean.getArea());
                            }
                            map.put(streetStr, areaList);
                        }
                        leftMenuAdapter = new LeftMenuAdapter(CategoryActivity.this, leftList);
                        rightList.addAll(map.get(leftList.get(0)));
                        rightMenuAdapter = new RightMenuAdapter(CategoryActivity.this, rightList);
                        mLvLeft.setAdapter(leftMenuAdapter);
                        mLvRight.setAdapter(rightMenuAdapter);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(CategoryActivity.this, message);
                    }

                    @Override
                    protected boolean showDialog() {
                        return false;
                    }
                });
    }

    public void getBrandSpec(String type, String type1) {
        Api.getDefault().getBrandAndSpec(Id, type, type1)
                .compose(RxHelper.<BrandSpec>handleResult())
                .subscribe(new RxSubscriber<BrandSpec>(this) {
                    @Override
                    protected void _onNext(BrandSpec brandSpec) {
                        if (brandSpec.getCount() == 0) {
                            mLlContainer.setVisibility(View.GONE);
                            mTvNoFound.setVisibility(View.VISIBLE);
                        } else {
                            for (BrandSpec.Brand brand : brandSpec.getBrand()) {
                                leftList.add(brand.getBrand());
                                List<BrandSpec.Brand.Specification> specificationList = brand.getSpecification();
                                List<String> list = new ArrayList<>();
                                for (BrandSpec.Brand.Specification specification : specificationList) {
                                    list.add(specification.getSpecification());
                                }
                                map.put(brand.getBrand(), list);
                            }
                            leftMenuAdapter = new LeftMenuAdapter(CategoryActivity.this, leftList);
                            rightList.addAll(map.get(leftList.get(0)));
                            rightMenuAdapter = new RightMenuAdapter(CategoryActivity.this, rightList);
                            mLvLeft.setAdapter(leftMenuAdapter);
                            mLvRight.setAdapter(rightMenuAdapter);
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showToast(CategoryActivity.this, message);
                    }

                    @Override
                    protected boolean showDialog() {
                        return false;
                    }
                });

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}
