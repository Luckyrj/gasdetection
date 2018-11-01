package model;

import java.util.List;

/**
 * Author: andy
 * Time:2018/9/21 0021
 * Description:安检项目具体条目
 */

public class CheckItemsDetail {

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

        private Object account_show;
        private Object module_show;
        private String of_project_show;
        private Object check_advice;
        private int id;
        private int govid;
        private Object picture;
        private String name;
        private String project_no;
        private int module_id;
        private int sort_by;
        private String advices;
        private Object security_level;
        private int parent_id;

        public Object getAccount_show() {
            return account_show;
        }

        public void setAccount_show(Object account_show) {
            this.account_show = account_show;
        }

        public Object getModule_show() {
            return module_show;
        }

        public void setModule_show(Object module_show) {
            this.module_show = module_show;
        }

        public String getOf_project_show() {
            return of_project_show;
        }

        public void setOf_project_show(String of_project_show) {
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

        public String getAdvices() {
            return advices;
        }

        public void setAdvices(String advices) {
            this.advices = advices;
        }

        public Object getSecurity_level() {
            return security_level;
        }

        public void setSecurity_level(Object security_level) {
            this.security_level = security_level;
        }

        public int getParent_id() {
            return parent_id;
        }

        public void setParent_id(int parent_id) {
            this.parent_id = parent_id;
        }
    }
}
