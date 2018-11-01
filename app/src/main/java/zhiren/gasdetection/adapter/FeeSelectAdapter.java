package zhiren.gasdetection.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

import model.LaborFeeData;
import zhiren.gasdetection.R;

/**
 * Author: andy
 * Time:2018/10/17 0017
 * Description:添加费用使用的适配器
 */

public class FeeSelectAdapter extends SuperAdapter<LaborFeeData.LaborData> {
    private ArrayList<LaborFeeData.LaborData> selectData = new ArrayList<>();

    public FeeSelectAdapter(Context context, List<LaborFeeData.LaborData> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final LaborFeeData.LaborData item) {
        holder.setText(R.id.tvMoney, String.format("%s 元", item.getPrice()));
        holder.setText(R.id.tvItem, item.getName());
        final CheckBox checkBox = holder.findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectData.add(item);
                } else {
                    selectData.remove(item);
                }
            }
        });

    }

    public ArrayList<LaborFeeData.LaborData> getSelectData() {
        return selectData;
    }
}
