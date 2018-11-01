package model;

import java.util.List;

/**
 * Author: andy
 * Time:2018/9/20 0020
 * Description:安检项目
 */

public class CheckItems {

    private int count;
    private List<ProjectData> project_data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ProjectData> getProject_data() {
        return project_data;
    }

    public void setProject_data(List<ProjectData> project_data) {
        this.project_data = project_data;
    }

    public static class ProjectData {
        private String account_show;
        private String module_show;
        private Object of_project_show;
        private Object check_advice;
        private int id;
        private int govid;
        private Object picture;
        private String name;
        private String project_no;
        private int module_id;
        private int sort_by;
        private Object advices;
        private Object security_level;
        private String type;
        private int parent_id;
        private String result = "";//合格与否

        public String getAccount_show() {
            return account_show;
        }

        public void setAccount_show(String account_show) {
            this.account_show = account_show;
        }

        public String getModule_show() {
            return module_show;
        }

        public void setModule_show(String module_show) {
            this.module_show = module_show;
        }

        public Object getOf_project_show() {
            return of_project_show;
        }

        public void setOf_project_show(Object of_project_show) {
            this.of_project_show = of_project_show;
        }

        public Object getCheck_advice() {
            return check_advice;
        }

        public void setCheck_advice(Object check_advice) {
            this.check_advice = check_advice;
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

        public Object getPicture() {
            return picture;
        }

        public void setPicture(Object picture) {
            this.picture = picture;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProject_no() {
            return project_no;
        }

        public void setProject_no(String project_no) {
            this.project_no = project_no;
        }

        public int getModule_id() {
            return module_id;
        }

        public void setModule_id(int module_id) {
            this.module_id = module_id;
        }

        public int getSort_by() {
            return sort_by;
        }

        public void setSort_by(int sort_by) {
            this.sort_by = sort_by;
        }

        public Object getAdvices() {
            return advices;
        }

        public void setAdvices(Object advices) {
            this.advices = advices;
        }

        public Object getSecurity_level() {
            return security_level;
        }

        public void setSecurity_level(Object security_level) {
            this.security_level = security_level;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }
    }
}
