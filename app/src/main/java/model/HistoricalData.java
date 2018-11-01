package model;

import java.util.List;

/**
 * Author: andy
 * Time:2018/10/10 0010
 * Description:客户历史数据
 */

public class HistoricalData {

    private int count;
    private List<HistoryData> history_data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<HistoryData> getHistory_data() {
        return history_data;
    }

    public void setHistory_data(List<HistoryData> history_data) {
        this.history_data = history_data;
    }

    public static class HistoryData {

        private String addtime_show;
        private String staff_id_name;
        private String staff_id_tel;
        private String task_type;
        private String total_fee1;
        private String status;
        private String total_fee;
        private String fee_pay_status;
        private int id;
        private int govid;
        private int customer_id;
        private String check_detail;
        private String visit_time;
        private String finish_time;
        private String miss_time;
        private String check_result;
        private String voice;
        private String unqualified;
        private String remark;
        private String labor_items;
        private String labor_counts;
        private String labor_prices;
        private String publicity;
        private String sign_picture;
        private String group_picture;
        private boolean is_charge;
        private boolean is_finish;
        private int staff_id;
        private String addtime;
        private int task_id;

        public String getAddtime_show() {
            return addtime_show;
        }

        public void setAddtime_show(String addtime_show) {
            this.addtime_show = addtime_show;
        }

        public String getStaff_id_name() {
            return staff_id_name;
        }

        public void setStaff_id_name(String staff_id_name) {
            this.staff_id_name = staff_id_name;
        }

        public String getStaff_id_tel() {
            return staff_id_tel;
        }

        public void setStaff_id_tel(String staff_id_tel) {
            this.staff_id_tel = staff_id_tel;
        }

        public String getTask_type() {
            return task_type;
        }

        public void setTask_type(String task_type) {
            this.task_type = task_type;
        }

        public String getTotal_fee1() {
            return total_fee1;
        }

        public void setTotal_fee1(String total_fee1) {
            this.total_fee1 = total_fee1;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(String total_fee) {
            this.total_fee = total_fee;
        }

        public String getFee_pay_status() {
            return fee_pay_status;
        }

        public void setFee_pay_status(String fee_pay_status) {
            this.fee_pay_status = fee_pay_status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGovid() {
            return govid;
        }

        public void setGovid(int govid) {
            this.govid = govid;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public String getCheck_detail() {
            return check_detail;
        }

        public void setCheck_detail(String check_detail) {
            this.check_detail = check_detail;
        }

        public String getVisit_time() {
            return visit_time;
        }

        public void setVisit_time(String visit_time) {
            this.visit_time = visit_time;
        }

        public String getFinish_time() {
            return finish_time;
        }

        public void setFinish_time(String finish_time) {
            this.finish_time = finish_time;
        }

        public String getMiss_time() {
            return miss_time;
        }

        public void setMiss_time(String miss_time) {
            this.miss_time = miss_time;
        }

        public String getCheck_result() {
            return check_result;
        }

        public void setCheck_result(String check_result) {
            this.check_result = check_result;
        }

        public String getVoice() {
            return voice;
        }

        public void setVoice(String voice) {
            this.voice = voice;
        }

        public String getUnqualified() {
            return unqualified;
        }

        public void setUnqualified(String unqualified) {
            this.unqualified = unqualified;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getLabor_items() {
            return labor_items;
        }

        public void setLabor_items(String labor_items) {
            this.labor_items = labor_items;
        }

        public String getLabor_counts() {
            return labor_counts;
        }

        public void setLabor_counts(String labor_counts) {
            this.labor_counts = labor_counts;
        }

        public String getLabor_prices() {
            return labor_prices;
        }

        public void setLabor_prices(String labor_prices) {
            this.labor_prices = labor_prices;
        }

        public String getPublicity() {
            return publicity;
        }

        public void setPublicity(String publicity) {
            this.publicity = publicity;
        }

        public String getSign_picture() {
            return sign_picture;
        }

        public void setSign_picture(String sign_picture) {
            this.sign_picture = sign_picture;
        }

        public String getGroup_picture() {
            return group_picture;
        }

        public void setGroup_picture(String group_picture) {
            this.group_picture = group_picture;
        }

        public boolean isIs_charge() {
            return is_charge;
        }

        public void setIs_charge(boolean is_charge) {
            this.is_charge = is_charge;
        }

        public boolean isIs_finish() {
            return is_finish;
        }

        public void setIs_finish(boolean is_finish) {
            this.is_finish = is_finish;
        }

        public int getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(int staff_id) {
            this.staff_id = staff_id;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getTask_id() {
            return task_id;
        }

        public void setTask_id(int task_id) {
            this.task_id = task_id;
        }
    }
}
