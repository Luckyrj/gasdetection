package model;

import java.util.List;

/**
 * Author: andy
 * Time:2018/9/21 0021
 * Description:安检产品品牌型号
 */

public class BrandSpec {

    private int count;
    private List<Brand> brand;
    private List<String> type;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Brand> getBrand() {
        return brand;
    }

    public void setBrand(List<Brand> brand) {
        this.brand = brand;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public static class Brand {

        private String brand;
        private List<Specification> specification;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public List<Specification> getSpecification() {
            return specification;
        }

        public void setSpecification(List<Specification> specification) {
            this.specification = specification;
        }

        public static class Specification {

            private String specification;
            private String name1;

            public String getSpecification() {
                return specification;
            }

            public void setSpecification(String specification) {
                this.specification = specification;
            }

            public String getName1() {
                return name1;
            }

            public void setName1(String name1) {
                this.name1 = name1;
            }
        }
    }
}
