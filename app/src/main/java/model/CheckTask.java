package model;

import java.io.Serializable;
import java.util.List;

/**
 * Author: andy
 * Time:2018/9/18 0018
 * Description:安检任务
 */

public class CheckTask {

    private int count;
    private List<TaskDataBean> task_data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<TaskDataBean> getTask_data() {
        return task_data;
    }

    public void setTask_data(List<TaskDataBean> task_data) {
        this.task_data = task_data;
    }

    public static class TaskDataBean implements Serializable {
        /**
         * book_time_show : null
         * customer_name_show : 李民基
         * customer_no_show : 198
         * customer_tel_show : 18871581548
         * customer_province_show : 湖北
         * customer_city_show : 武汉
         * customer_county_show : 汉阳
         * customer_street_show : 1
         * customer_area_show : 龙阳湖
         * customer_building_show : 8
         * customer_assign_to_show : 测试
         * customer_address_show : 龙阳湖8
         * customer_ventilate_status_show : 1
         * id : 27
         * govid : 373
         * addtime : 2018-09-18T10:26:40.253
         * assign_to_time : 2018-09-18T10:26:50.953
         * customer_id : 70
         * task_type : 1
         * book_time : 1990-01-01T00:00:00
         * assign_to : 40
         * finish_status : null
         * check_status : null
         * check_house : 待安检
         */

        private Object book_time_show;
        private String customer_name_show;
        private String customer_no_show;
        private String customer_tel_show;
        private String customer_province_show;
        private String customer_city_show;
        private String customer_county_show;
        private String customer_street_show;
        private String customer_area_show;
        private String customer_building_show;
        private String customer_assign_to_show;
        private String customer_address_show;
        private String customer_ventilate_status_show;
        private int id;
        private int govid;
        private String addtime;
        private String assign_to_time;
        private int customer_id;
        private int task_type;
        private String book_time;
        private int assign_to;
        private Object finish_status;
        private Object check_status;
        private String check_house;

        public Object getBook_time_show() {
            return book_time_show;
        }

        public void setBook_time_show(Object book_time_show) {
            this.book_time_show = book_time_show;
        }

        public String getCustomer_name_show() {
            return customer_name_show;
        }

        public void setCustomer_name_show(String customer_name_show) {
            this.customer_name_show = customer_name_show;
        }

        public String getCustomer_no_show() {
            return customer_no_show;
        }

        public void setCustomer_no_show(String customer_no_show) {
            this.customer_no_show = customer_no_show;
        }

        public String getCustomer_tel_show() {
            return customer_tel_show;
        }

        public void setCustomer_tel_show(String customer_tel_show) {
            this.customer_tel_show = customer_tel_show;
        }

        public String getCustomer_province_show() {
            return customer_province_show;
        }

        public void setCustomer_province_show(String customer_province_show) {
            this.customer_province_show = customer_province_show;
        }

        public String getCustomer_city_show() {
            return customer_city_show;
        }

        public void setCustomer_city_show(String customer_city_show) {
            this.customer_city_show = customer_city_show;
        }

        public String getCustomer_county_show() {
            return customer_county_show;
        }

        public void setCustomer_county_show(String customer_county_show) {
            this.customer_county_show = customer_county_show;
        }

        public String getCustomer_street_show() {
            return customer_street_show;
        }

        public void setCustomer_street_show(String customer_street_show) {
            this.customer_street_show = customer_street_show;
        }

        public String getCustomer_area_show() {
            return customer_area_show;
        }

        public void setCustomer_area_show(String customer_area_show) {
            this.customer_area_show = customer_area_show;
        }

        public String getCustomer_building_show() {
            return customer_building_show;
        }

        public void setCustomer_building_show(String customer_building_show) {
            this.customer_building_show = customer_building_show;
        }

        public String getCustomer_assign_to_show() {
            return customer_assign_to_show;
        }

        public void setCustomer_assign_to_show(String customer_assign_to_show) {
            this.customer_assign_to_show = customer_assign_to_show;
        }

        public String getCustomer_address_show() {
            return customer_address_show;
        }

        public void setCustomer_address_show(String customer_address_show) {
            this.customer_address_show = customer_address_show;
        }

        public String getCustomer_ventilate_status_show() {
            return customer_ventilate_status_show;
        }

        public void setCustomer_ventilate_status_show(String customer_ventilate_status_show) {
            this.customer_ventilate_status_show = customer_ventilate_status_show;
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

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getAssign_to_time() {
            return assign_to_time;
        }

        public void setAssign_to_time(String assign_to_time) {
            this.assign_to_time = assign_to_time;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public int getTask_type() {
            return task_type;
        }

        public void setTask_type(int task_type) {
            this.task_type = task_type;
        }

        public String getBook_time() {
            return book_time;
        }

        public void setBook_time(String book_time) {
            this.book_time = book_time;
        }

        public int getAssign_to() {
            return assign_to;
        }

        public void setAssign_to(int assign_to) {
            this.assign_to = assign_to;
        }

        public Object getFinish_status() {
            return finish_status;
        }

        public void setFinish_status(Object finish_status) {
            this.finish_status = finish_status;
        }

        public Object getCheck_status() {
            return check_status;
        }

        public void setCheck_status(Object check_status) {
            this.check_status = check_status;
        }

        public String getCheck_house() {
            return check_house;
        }

        public void setCheck_house(String check_house) {
            this.check_house = check_house;
        }
    }
}
