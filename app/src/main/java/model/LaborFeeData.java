package model;

import java.io.Serializable;
import java.util.List;

/**
 * Author: andy
 * Time:2018/10/17 0017
 * Description:人工服务费用条目
 */

public class LaborFeeData {

    private int count;
    private List<LaborData> labor_data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<LaborData> getLabor_data() {
        return labor_data;
    }

    public void setLabor_data(List<LaborData> labor_data) {
        this.labor_data = labor_data;
    }

    public static class LaborData implements Serializable {
        private String depart_id_show;
        private String price_show;
        private int id;
        private int govid;
        private int depart_id;
        private String name;
        private boolean is_default;
        private double price;

        public String getDepart_id_show() {
            return depart_id_show;
        }

        public void setDepart_id_show(String depart_id_show) {
            this.depart_id_show = depart_id_show;
        }

        public String getPrice_show() {
            return price_show;
        }

        public void setPrice_show(String price_show) {
            this.price_show = price_show;
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

        public int getDepart_id() {
            return depart_id;
        }

        public void setDepart_id(int depart_id) {
            this.depart_id = depart_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isIs_default() {
            return is_default;
        }

        public void setIs_default(boolean is_default) {
            this.is_default = is_default;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
