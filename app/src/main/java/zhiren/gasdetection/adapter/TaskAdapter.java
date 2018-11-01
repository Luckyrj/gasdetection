package zhiren.gasdetection.adapter;

import android.content.Context;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.List;

import model.CheckTask;
import zhiren.gasdetection.R;

/**
 * Author: andy
 * Time:2018/9/18 0018
 * Description:
 */

public class TaskAdapter extends SuperAdapter<CheckTask.TaskDataBean> {
    int type;

    public TaskAdapter(Context context, List<CheckTask.TaskDataBean> items, int layoutResId) {
        super(context, items, layoutResId);
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, CheckTask.TaskDataBean item) {
        holder.setText(R.id.tvName, item.getCustomer_name_show());
        holder.setText(R.id.tvNum, item.getCustomer_no_show());
        holder.setText(R.id.tvLocate, item.getCustomer_address_show());
        if (item.getCheck_status() == null || item.getCheck_status().equals("不合格")) {
            holder.setImageResource(R.id.image, R.mipmap.check_no_icon);
        } else if (item.getCheck_status().equals("合格")) {
            holder.setImageResource(R.id.image, R.mipmap.check_ok_icon);
        }
    }
}
