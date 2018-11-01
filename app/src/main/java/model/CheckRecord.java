package model;

/**
 * Author: andy
 * Time:2018/9/19 0019
 * Description:安检记录
 */

public class CheckRecord {

    private int id;
    private int govid;
    private int customer_id;
    private String check_detail;
    private String visit_time;
    private String finish_time;
    private String check_result;
    private int staff_id;
    private String addtime;
    private int task_id;
    private String voice;
    private String unqualified;

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

    public String getCheck_result() {
        return check_result;
    }

    public void setCheck_result(String check_result) {
        this.check_result = check_result;
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
}
