package com.ceq.app.main.bean;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class Bean_ProductionPeopleInfo {

    String Gradepreuids,grade,name,GradesonIds,url;
    boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getGradepreuids() {
        return Gradepreuids;
    }

    public void setGradepreuids(String gradepreuids) {
        Gradepreuids = gradepreuids;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGradesonIds() {
        return GradesonIds;
    }

    public void setGradesonIds(String gradesonIds) {
        GradesonIds = gradesonIds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
