package zhiren.gasdetection.AnJian;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import zhiren.gasdetection.BaseActivity;
import zhiren.gasdetection.R;

//  燃气安全风险等级页面
public class RiskRatingActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.listFirst)
    RecyclerView mListFirst;
    @BindView(R.id.listSecond)
    RecyclerView mListSecond;
    @BindView(R.id.listThird)
    RecyclerView mListThird;
    @BindView(R.id.etNote)
    EditText mEtNote;
    @BindView(R.id.btnEnter)
    Button mBtnEnter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_risk_rating;
    }

    @Override
    protected void initData() {
        mText.setText("燃气安全风险等级");
    }

    @OnClick({R.id.iv_back, R.id.btnEnter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btnEnter:
                break;
        }
    }
}
