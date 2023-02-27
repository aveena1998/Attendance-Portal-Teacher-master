package com.example.auotmaticattendance.Ac_section.View_Ac;

public class viewacobject {
    private String key;
    private String rollno;
    private String name;
    private String attended;
    private String total_classes;
    private String total_ac;
    private String branch;
    private String subject;

    public  viewacobject(String key,String rollno,String name,String attended,String total_classes,String total_ac,String subject,String branch){

        this.key=key;
        this.rollno=rollno;
        this.name=name;
        this.attended=attended;
        this.total_classes=total_classes;
        this.total_ac=total_ac;
        this.branch=branch;
        this.subject=subject;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setAttended(String attended) {
        this.attended = attended;
    }

    public String getAttended() {
        return attended;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getTotal_classes() {
        return total_classes;
    }

    public void setTotal_classes(String total_classes) {
        this.total_classes = total_classes;
    }

    public String getTotal_ac() {
        return total_ac;
    }

    public void setTotal_ac(String total_ac) {
        this.total_ac = total_ac;
    }

    public String getBranch() {
        return branch;
    }

    public String getSubject() {
        return subject;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
