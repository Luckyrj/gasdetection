package zhiren.gasdetection.adapter;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import model.CheckItemsDetail;
import zhiren.gasdetection.R;

/**
 * Author: andy
 * Time:2018/9/21 0021
 * Description:安检项目详情检查项列表使用的适配器
 */

public class CheckDetailAdapter extends SuperAdapter<CheckItemsDetail.ProjectData> {
    int i = 0;
    String unqualified = ""; //不合格检验项id如 (1,2,3)

    public CheckDetailAdapter(Context context, List<CheckItemsDetail.ProjectData> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final CheckItemsDetail.ProjectData item) {
        i++;
        holder.setText(R.id.tv, i + ". " + item.getName());
        final ToggleButton toggleButton = holder.findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    unqualified += item.getId() + ",";
                }
            }
        });
    }

    public String getUnqualified() {
        return unqualified.length() > 0 ? unqualified.substring(0, unqualified.length() - 1) : unqualified;
    }
}
