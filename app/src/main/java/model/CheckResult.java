package model;

import java.util.List;

/**
 * Author: andy
 * Time:2018/10/8 0008
 * Description:安检结果
 */

public class CheckResult {

    private Staff staff;
    private Customer customer;
    private Fee fee;
    private CheckData check_data;
    private List<?> l1;
    private List<?> l2;
    private List<?> l3;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Fee getFee() {
        return fee;
    }

    public void setFee(Fee fee) {
        this.fee = fee;
    }

    public CheckData getCheck_data() {
        return check_data;
    }

    public void setCheck_data(CheckData check_data) {
        this.check_data = check_data;
    }

    public List<?> getL1() {
        return l1;
    }

    public void setL1(List<?> l1) {
        this.l1 = l1;
    }

    public List<?> getL2() {
        return l2;
    }

    public void setL2(List<?> l2) {
        this.l2 = l2;
    }

    public List<?> getL3() {
        return l3;
    }

    public void setL3(List<?> l3) {
        this.l3 = l3;
    }

    public static class Staff {

        private String gov_name;
        private String depart_name;
        private String role_name;
        private int id;
        private int govid;
        private int departid;
        private String pwd;
        private String realname;
        private String img;
        private String staffno;
        private String tel;
        private int gender;
        private boolean islock;
        private boolean devicebind;
        private String devicetype;
        private String devicemac;
        private String addtime;
        private String introduce;
        private boolean is_delete;

        public String getGov_name() {
            return gov_name;
        }

        public void setGov_name(String gov_name) {
            this.gov_name = gov_name;
        }

        public String getDepart_name() {
            return depart_name;
        }

        public void setDepart_name(String depart_name) {
            this.depart_name = depart_name;
        }

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
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

        public int getDepartid() {
            return departid;
        }

        public void setDepartid(int departid) {
            this.departid = departid;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getStaffno() {
            return staffno;
        }

        public void setStaffno(String staffno) {
            this.staffno = staffno;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public boolean isIslock() {
            return islock;
        }

        public void setIslock(boolean islock) {
            this.islock = islock;
        }

        public boolean isDevicebind() {
            return devicebind;
        }

        public void setDevicebind(boolean devicebind) {
            this.devicebind = devicebind;
        }

        public String getDevicetype() {
            return devicetype;
        }

        public void setDevicetype(String devicetype) {
            this.devicetype = devicetype;
        }

        public String getDevicemac() {
            return devicemac;
        }

        public void setDevicemac(String devicemac) {
            this.devicemac = devicemac;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public boolean isIs_delete() {
            return is_delete;
        }

        public void setIs_delete(boolean is_delete) {
            this.is_delete = is_delete;
        }
    }

    public static class Customer {

        private String last_time_show;
        private int id;
        private int govid;
        private String name;
        private String customer_no;
        private String tel;
        private String customer_type;
        private String province;
        private String city;
        private String county;
        private String street;
        private String area;
        private String building;
        private String ventilate_status;
        private String voice;
        private String check_house;
        private String last_time;
        private int security_level;
        private boolean is_approve;
        private boolean is_delete;

        public String getLast_time_show() {
            return last_time_show;
        }

        public void setLast_time_show(String last_time_show) {
            this.last_time_show = last_time_show;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCustomer_no() {
            return customer_no;
        }

        public void setCustomer_no(String customer_no) {
            this.customer_no = customer_no;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getCustomer_type() {
            return customer_type;
        }

        public void setCustomer_type(String customer_type) {
            this.customer_type = customer_type;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public String getVentilate_status() {
            return ventilate_status;
        }

        public void setVentilate_status(String ventilate_status) {
            this.ventilate_status = ventilate_status;
        }

        public String getVoice() {
            return voice;
        }

        public void setVoice(String voice) {
            this.voice = voice;
        }

        public String getCheck_house() {
            return check_house;
        }

        public void setCheck_house(String check_house) {
            this.check_house = check_house;
        }

        public String getLast_time() {
            return last_time;
        }

        public void setLast_time(String last_time) {
            this.last_time = last_time;
        }

        public int getSecurity_level() {
            return security_level;
        }

        public void setSecurity_level(int security_level) {
            this.security_level = security_level;
        }

        public boolean isIs_approve() {
            return is_approve;
        }

        public void setIs_approve(boolean is_approve) {
            this.is_approve = is_approve;
        }

        public boolean isIs_delete() {
            return is_delete;
        }

        public void setIs_delete(boolean is_delete) {
            this.is_delete = is_delete;
        }
    }

    public static class Fee {

        private String addtime_show;
        private String customer_id_name;
        private String customer_id_tel;
        private String fee1_show;
        private String staff_id_realname;
        private String staff_id_staffno;
        private int id;
        private int govid;
        private String order_no;
        private String addtime;
        private String business;
        private int customer_id;
        private String pay_status;
        private String pay_by;
        private double fee;
        private double total_fee;
        private int staff_id;
        private int check_data_id;

        public String getAddtime_show() {
            return addtime_show;
        }

        public void setAddtime_show(String addtime_show) {
            this.addtime_show = addtime_show;
        }

        public String getCustomer_id_name() {
            return customer_id_name;
        }

        public void setCustomer_id_name(String customer_id_name) {
            this.customer_id_name = customer_id_name;
        }

        public String getCustomer_id_tel() {
            return customer_id_tel;
        }

        public void setCustomer_id_tel(String customer_id_tel) {
            this.customer_id_tel = customer_id_tel;
        }

        public String getFee1_show() {
            return fee1_show;
        }

        public void setFee1_show(String fee1_show) {
            this.fee1_show = fee1_show;
        }

        public String getStaff_id_realname() {
            return staff_id_realname;
        }

        public void setStaff_id_realname(String staff_id_realname) {
            this.staff_id_realname = staff_id_realname;
        }

        public String getStaff_id_staffno() {
            return staff_id_staffno;
        }

        public void setStaff_id_staffno(String staff_id_staffno) {
            this.staff_id_staffno = staff_id_staffno;
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

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public int getCustomer_id() {
            return customer_id;
        }

        public void setCustomer_id(int customer_id) {
            this.customer_id = customer_id;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getPay_by() {
            return pay_by;
        }

        public void setPay_by(String pay_by) {
            this.pay_by = pay_by;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public double getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(double total_fee) {
            this.total_fee = total_fee;
        }

        public int getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(int staff_id) {
            this.staff_id = staff_id;
        }

        public int getCheck_data_id() {
            return check_data_id;
        }

        public void setCheck_data_id(int check_data_id) {
            this.check_data_id = check_data_id;
        }
    }

    public static class CheckData {

        private String addtime_show;
        private String visit_time_show;
        private String finish_time_show;
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

        public String getVisit_time_show() {
            return visit_time_show;
        }

        public void setVisit_time_show(String visit_time_show) {
            this.visit_time_show = visit_time_show;
        }

        public String getFinish_time_show() {
            return finish_time_show;
        }

        public void setFinish_time_show(String finish_time_show) {
            this.finish_time_show = finish_time_show;
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
