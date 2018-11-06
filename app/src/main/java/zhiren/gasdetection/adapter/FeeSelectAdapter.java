package zhiren.gasdetection.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.byteam.superadapter.SuperAdapter;
import org.byteam.superadapter.SuperViewHolder;

import java.util.ArrayList;
import java.util.List;

import model.LaborFeeData;
import zhiren.gasdetection.R;
import zhiren.gasdetection.interfaces.OnFeeSumListener;

/**
 * Author: andy
 * Time:2018/10/17 0017
 * Description:添加费用使用的适配器
 */

public class FeeSelectAdapter extends SuperAdapter<LaborFeeData.LaborData> {
    private ArrayList<LaborFeeData.LaborData> selectData = new ArrayList<>();
    private  List<LaborFeeData.LaborData> items;
    private OnFeeSumListener listener;
    private boolean isEdit=false;

    public FeeSelectAdapter(Context context, List<LaborFeeData.LaborData> items, int layoutResId) {
        super(context, items, layoutResId);
        this.items=items;
    }

    @Override
    public void onBind(SuperViewHolder holder, int viewType, int layoutPosition, final LaborFeeData.LaborData item) {
        holder.setText(R.id.tvMoney, String.format("%s 元", item.getPrice()));
        holder.setText(R.id.tvItem, item.getName());
        ImageView ivMinus=holder.findViewById(R.id.ivMinus);
        ImageView ivAdd=holder.findViewById(R.id.ivAdd);
        TextView editNum=holder.findViewById(R.id.editNum);
        CheckBox checkBox=holder.findViewById(R.id.checkBox);
        if (item.isEditStatus()) {
          checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
        checkBox.setOnClickListener(v -> {
            if (checkBox.isChecked()) {
                item.setEditStatus(true);
            } else {
                item.setEditStatus(false);
            }
        });

        ivMinus.setOnClickListener(v -> {
            String  num= editNum.getText().toString().trim();
            int  feeNum= Integer.parseInt(num);
            if (feeNum>1) {
                editNum.setText(String.valueOf(feeNum-1));
                item.setNumber(feeNum-1);
                listener.onFeeSum(getFeeSum());
            }
        });
        ivAdd.setOnClickListener(v ->{
            String  num= editNum.getText().toString().trim();
            int  feeNum= Integer.parseInt(num);
            editNum.setText(String.valueOf(feeNum+1));
            item.setNumber(feeNum+1);
            listener.onFeeSum(getFeeSum());
        });

        checkBox.setVisibility(View.GONE);
        if (isEdit) {
          checkBox.setVisibility(View.VISIBLE);
        }
    }


    public void setFeeSumListener(OnFeeSumListener feeSumListener) {
        listener = feeSumListener;
    }

    public double getFeeSum() {
        int sum =0;
        for (int i = 0; i <items.size() ; i++) {
          sum+=(items.get(i).getPrice() *items.get(i).getNumber());
        }
        return sum;
    }

    public  void  setIsEdit(boolean isEdit){
        this.isEdit=isEdit;
    }
}
