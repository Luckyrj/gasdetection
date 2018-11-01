package model;

import java.util.List;

/**
 * Author: andy
 * Time:2018/9/14 0014
 * Description:街道小区关系
 */
public class Street {

    private int count;
    private List<StreetBean> street;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<StreetBean> getStreet() {
        return street;
    }

    public void setStreet(List<StreetBean> street) {
        this.street = street;
    }

    public static class StreetBean {

        private String street;
        private List<AreaBean> area;

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public List<AreaBean> getArea() {
            return area;
        }

        public void setArea(List<AreaBean> area) {
            this.area = area;
        }

        public static class AreaBean {

            private String area;

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }
        }
    }
}
