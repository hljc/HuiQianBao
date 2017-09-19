package com.ceq.app.core.bean;

/**
 * Created by ceq on 2017/5/16.
 */

public class Bean_PushData {
    String realnameStatus;
    String marquee;
    String grade;
    String androidVersion;


    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRealnameStatus() {
        return realnameStatus;
    }

    public void setRealnameStatus(String realnameStatus) {
        this.realnameStatus = realnameStatus;
    }

    public String getMarquee() {
        return marquee;
    }

    public void setMarquee(String marquee) {
        this.marquee = marquee;
    }

    @Override
    public String toString() {
        return "Bean_PushData{" +
                "realnameStatus='" + realnameStatus + '\'' +
                ", marquee='" + marquee + '\'' +
                ", grade='" + grade + '\'' +
                ", androidVersion='" + androidVersion + '\'' +
                '}';
    }
}
