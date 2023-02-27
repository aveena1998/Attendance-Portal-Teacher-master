package com.example.auotmaticattendance.Student_section;

public class viewstudentobject {

    private String profilepic;
    private String name;
    private String rollno;
    private String email;
    private  String phone;

    public viewstudentobject(String profilepic, String name, String rollno,String email, String phone){

       this.rollno=rollno;
       this.email=email;
        this.name=name;
        this.profilepic=profilepic;
        this.phone=phone;

    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

